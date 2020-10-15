package com.dummy.myerp.business.impl.manager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.dummy.myerp.business.contrat.manager.ComptabiliteManager;
import com.dummy.myerp.business.impl.BusinessProxyImpl;
import com.dummy.myerp.business.impl.TransactionManager;
import com.dummy.myerp.consumer.dao.impl.DaoProxyImpl;
import com.dummy.myerp.consumer.dao.impl.db.dao.ComptabiliteDaoImpl;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;



@RunWith(SpringRunner.class)
@Transactional
@Rollback(true)
public class ComptabiliteManagerImplIT {
	
	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContext.xml", "sqlContext.xml"});
	DataSource MYERP = (DataSource) context.getBean("dataSourceMYERP"); 
	ComptabiliteDaoImpl comptabilite = (ComptabiliteDaoImpl) context.getBean("ComptabiliteDaoImpl");
	DaoProxyImpl daoProxyImpl = (DaoProxyImpl) context.getBean("DaoProxy");
    
    
    @Test
	@Rollback
	@Transactional
    public void checkEcritureComptableContext() throws NotFoundException  {
    	  
        EcritureComptable pEcritureComptable = daoProxyImpl.getComptabiliteDao().getEcritureComptable(-2);
  	
        // ===== RG_Compta_6 : La référence d'une écriture comptable doit être unique
        if (StringUtils.isNoneEmpty(pEcritureComptable.getReference())) {
            
                // Recherche d'une écriture ayant la même référence
                EcritureComptable vECRef = daoProxyImpl.getComptabiliteDao().getEcritureComptableByRef(
                    pEcritureComptable.getReference());

                // Si l'écriture à vérifier est une nouvelle écriture (id == null)
                // ou si elle ne correspond pas à l'écriture trouvée (id != idECRef),
                // c'est qu'il y a déjà une autre écriture avec la même référence
                if (pEcritureComptable.getId() != null
                        &&pEcritureComptable.getId().equals(vECRef.getId())) {
                	pEcritureComptable.equals(vECRef);
                }
                Assertions.assertTrue(pEcritureComptable.getId().equals(vECRef.getId()), "Echec du test du RG Compta 6 : La référence de l'écriture comptable Id=" + max + "n'est pas unique");
                
                System.out.println(pEcritureComptable.getId());	
        		System.out.println(vECRef.getId());	
        }
        
        System.out.println(max);	
		System.out.println(nb);	
    }
    
    /*
    @Test
	public void testInsertEcritureComptable() throws FunctionalException, ParseException, NotFoundException {
    	
    	
    	System.out.println(context);
		//System.out.println(comptabilite);
		System.out.println(daoProxyImpl);
		System.out.println(MYERP); 
		System.out.println(comptabilite); 
		
	    	
    		DataSourceTransactionManager ptmMyERP = (DataSourceTransactionManager) context.getBean("txManager");
	        DefaultTransactionDefinition vTDef = new DefaultTransactionDefinition();
	        vTDef.setName("Transaction_txManagerMyERP");
	        vTDef.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
	        TransactionStatus vTS = ptmMyERP.getTransaction(vTDef);
	    	
	    	EcritureComptable ecritureTest = comptabilite.getEcritureComptable(-2);
	    	System.out.println(ecritureTest);
	    	//Integer newId = comptabilite.getListEcritureComptable().size()-4;
	    	ecritureTest.setId(null);
	    		
	    	ComptabiliteManager comptabiliteManager =BusinessProxyImpl.getInstance(daoProxyImpl, TransactionManager.getInstance()).getComptabiliteManager();
	    	//ComptabiliteManagerImpl comptabiliteManager = new ComptabiliteManagerImpl();
	        comptabiliteManager.checkEcritureComptable(ecritureTest);
	        
	        

	        Assertions.assertTrue(vTS.isNewTransaction(), "Echec du test de la méthode insertEcritureComptable : la transaction n'a pas été créée");
	
	        try {
	            comptabilite.insertEcritureComptable(ecritureTest);
	            ptmMyERP.commit(vTS);
	            Assertions.assertTrue(vTS.isCompleted(), "Echec du test sur la méthode insertEcritureComptable : la transaction n'a pas été committée");
	            vTS = null;
	        } finally {
	            ptmMyERP.rollback(vTS);
	        }
	        
	        Assertions.assertTrue(vTS.isCompleted(), "Echec du test sur la méthode insertEcritureComptable : la transaction n'a pas été rolled-back");
	    }

    */
    

}
