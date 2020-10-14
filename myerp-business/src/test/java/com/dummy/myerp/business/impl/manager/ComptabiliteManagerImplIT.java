package com.dummy.myerp.business.impl.manager;

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
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.dummy.myerp.consumer.dao.impl.DaoProxyImpl;
import com.dummy.myerp.consumer.dao.impl.db.dao.ComptabiliteDaoImpl;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
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
    	
        Integer max = comptabilite.getListEcritureComptable().size();
        Random random = new Random();
        Integer nb = random.nextInt(max)-6;
        
        EcritureComptable pEcritureComptable = daoProxyImpl.getComptabiliteDao().getEcritureComptable(nb);
  	
        // ===== RG_Compta_6 : La référence d'une écriture comptable doit être unique
        if (StringUtils.isNoneEmpty(pEcritureComptable.getReference())) {
            
                // Recherche d'une écriture ayant la même référence
                EcritureComptable vECRef = daoProxyImpl.getComptabiliteDao().getEcritureComptableByRef(
                    pEcritureComptable.getReference());

                // Si l'écriture à vérifier est une nouvelle écriture (id == null)
                // ou si elle ne correspond pas à l'écriture trouvée (id != idECRef),
                // c'est qu'il y a déjà une autre écriture avec la même référence
                
                Assertions.assertEquals(pEcritureComptable.getId(), vECRef.getId(), "Echec du test du RG Compta 6 : La référence de l'écriture comptable Id=" + max + "n'est pas unique");
            
        }
        
        
    }

}
