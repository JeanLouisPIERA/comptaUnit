package com.dummy.myerp.business.impl.manager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.dummy.myerp.business.impl.BusinessProxyImpl;
import com.dummy.myerp.business.impl.TransactionManager;
import com.dummy.myerp.consumer.ConsumerHelper;
import com.dummy.myerp.consumer.dao.contrat.DaoProxy;
import com.dummy.myerp.consumer.dao.impl.DaoProxyImpl;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;

public class ComptabiliteManagerImplIT {
	
	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"classpath*:applicationContext.xml", "transactionContext.xml", "transactionContext.xml"} );
	DataSource MYERP = (DataSource) context.getBean("dataSourceMYERP");
	DataSourceTransactionManager txManagerMYERP = (DataSourceTransactionManager) context.getBean("txManagerMYERP");
	TransactionManager pTransactionManager = (TransactionManager) context.getBean("TransactionManager");
	DaoProxyImpl DaoProxyImpl = (DaoProxyImpl) context.getBean("DaoProxy");
	ConsumerHelper consumerHelper = (ConsumerHelper) context.getBean("ConsumerHelper");
	DaoProxy daoProxy = ConsumerHelper.getDaoProxy();
	
	private EcritureComptable createEcritureComptable() throws ParseException {
		
	   	EcritureComptable vEcritureComptable = new EcritureComptable();
    	vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.FRANCE);
        String sdateTest = "2020/02/01";
        Date dateTest = simpleDateFormat.parse(sdateTest);
        vEcritureComptable.setDate(dateTest);
        vEcritureComptable.setReference("AC-2020/00005");
        vEcritureComptable.setLibelle("Libelle");
        
        CompteComptable compte = new CompteComptable();
        compte.setNumero(606);
        compte.setNumero(401);
        List<LigneEcritureComptable> listLigne = new ArrayList<>();
        LigneEcritureComptable ligne1 = vEcritureComptable.createLigne(606, 1243.00, 0.00);
        listLigne.add(ligne1);
        LigneEcritureComptable ligne2 = vEcritureComptable.createLigne(401, 0.00, 1243.00);
        listLigne.add(ligne2);
        vEcritureComptable.setListLigneEcriture(listLigne);
		
		
		return vEcritureComptable;
	}
	
	
	@Test
	public void testGettters() {
		
		DaoProxy daoProxy = ConsumerHelper.getDaoProxy();
	   	
	   	BusinessProxyImpl businessProxyImpl = BusinessProxyImpl.getInstance(daoProxy, pTransactionManager);
	   	ComptabiliteManagerImpl comptaManager = (ComptabiliteManagerImpl) businessProxyImpl.getComptabiliteManager();
	   	
	   	Assert.assertTrue(comptaManager.getListCompteComptable().get(0).getNumero()==401);
	    Assert.assertFalse(comptaManager.getListJournalComptable().get(0).getLibelle().toString().equals("AC"));	
	    Assert.assertFalse(comptaManager.getListEcritureComptable().get(0).getReference().toString().equals("BQ-2016/00005"));
		
	   	Assert.assertFalse(comptaManager.getListCompteComptable().isEmpty());
	    Assert.assertFalse(comptaManager.getListJournalComptable().isEmpty());	
	    Assert.assertFalse(comptaManager.getListEcritureComptable().isEmpty());
	    
	}
	    
   @Test
    public void testCheckEcritureComptableContext() throws ParseException {
	   
	    DataSourceTransactionManager txManager = (DataSourceTransactionManager) context.getBean("txManagerMYERP");
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	    TransactionStatus status = txManager.getTransaction(def);
	   
	    DaoProxy daoProxy = ConsumerHelper.getDaoProxy();
	   	BusinessProxyImpl businessProxyImpl = BusinessProxyImpl.getInstance(daoProxy, pTransactionManager);
	   	ComptabiliteManagerImpl comptaManager = (ComptabiliteManagerImpl) businessProxyImpl.getComptabiliteManager();
	   
	   	//Test sans erreur
	   	EcritureComptable vEcritureComptable = this.createEcritureComptable();
        
        Assertions.assertDoesNotThrow(() -> {
        	comptaManager.insertEcritureComptable(vEcritureComptable);
        	comptaManager.checkEcritureComptableContext(vEcritureComptable);
            }, "L'écriture comptable ne respecte pas les contraintes de validation");
        
        //Test avec erreur
        vEcritureComptable.setReference("AC-2021/00012");
       
        Assertions.assertThrows(FunctionalException.class, () -> {
        	 comptaManager.insertEcritureComptable(vEcritureComptable);
        	 comptaManager.checkEcritureComptableContext(vEcritureComptable);
	         });
        
        txManager.rollback(status);
   }
   
   @Test
   public void testCheckEcritureComptable() throws ParseException {
	   
	    DataSourceTransactionManager txManager = (DataSourceTransactionManager) context.getBean("txManagerMYERP");
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	    TransactionStatus status = txManager.getTransaction(def);
   	
   		DaoProxy daoProxy = ConsumerHelper.getDaoProxy();
	   	BusinessProxyImpl businessProxyImpl = BusinessProxyImpl.getInstance(daoProxy, pTransactionManager);
	   	ComptabiliteManagerImpl comptaManager = (ComptabiliteManagerImpl) businessProxyImpl.getComptabiliteManager();
	   	
	   	//Test sans erreur
	    EcritureComptable vEcritureComptable = this.createEcritureComptable();
        
        Assertions.assertDoesNotThrow(() -> {
        	comptaManager.insertEcritureComptable(vEcritureComptable);
           	comptaManager.checkEcritureComptable(vEcritureComptable);;
            }, "L'écriture comptable ne respecte pas les règles de gestion.");
        
        //Test avec erreur
        vEcritureComptable.getListLigneEcriture().clear();
        
        Assertions.assertThrows(FunctionalException.class, () -> {
       	 comptaManager.insertEcritureComptable(vEcritureComptable);
       	 comptaManager.checkEcritureComptableContext(vEcritureComptable);
	         });
        
        txManager.rollback(status);
   	
   }
   
   @Test
	public void testInsertAndUpdateAndDeleteEcritureComptable() throws ParseException, NotFoundException, FunctionalException {
	   
	   	
	   	DaoProxy daoProxy = ConsumerHelper.getDaoProxy();
	   	
	   	BusinessProxyImpl businessProxyImpl = BusinessProxyImpl.getInstance(daoProxy, pTransactionManager);
	   	ComptabiliteManagerImpl comptaManager = (ComptabiliteManagerImpl) businessProxyImpl.getComptabiliteManager();
 	
        //Test INSERT sans erreur
		EcritureComptable vEcritureComptable = this.createEcritureComptable();
        Assertions.assertDoesNotThrow(() -> {
       	 comptaManager.insertEcritureComptable(vEcritureComptable);
       	 vEcritureComptable.equals(daoProxy.getComptabiliteDao().getEcritureComptableByRef(vEcritureComptable.getReference()));
         }, "L'écriture comptable n'a pas été correctement insérée");
        
        //Test INSERT avec erreur
         vEcritureComptable.getListLigneEcriture().clear();
         Assertions.assertFalse(vEcritureComptable.equals(daoProxy.getComptabiliteDao().getEcritureComptableByRef(vEcritureComptable.getReference())), 
    		   "L'écriture n'a pas été correctement insérée");

         
	       JournalComptable journalUpdate = new JournalComptable("BQ", "Banque");
	   	   vEcritureComptable.setJournal(journalUpdate);
	       String sdateUpdate = "2021/02/01";
	       SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.FRANCE);
	       Date dateUpdate = simpleDateFormat.parse(sdateUpdate);
	       vEcritureComptable.setDate(dateUpdate);
	       vEcritureComptable.setReference("AC-2021/000038");
	       vEcritureComptable.setLibelle("Libelle1");
	       
	       //Test UPDATE sans erreur 
	       Assertions.assertDoesNotThrow(() -> {
	       	 comptaManager.updateEcritureComptable(vEcritureComptable);
	       	 EcritureComptable ecritureRef = daoProxy.getComptabiliteDao().getEcritureComptable(vEcritureComptable.getId());
	       	 Assert.assertTrue(ecritureRef.getJournal().getCode().equals(vEcritureComptable.getJournal().getCode()));
	       	 Assert.assertTrue(ecritureRef.getReference().equals(vEcritureComptable.getReference()));
	       	 Assert.assertTrue(ecritureRef.getDate().equals(vEcritureComptable.getDate()));
	       	 Assert.assertTrue(ecritureRef.getLibelle().equals(vEcritureComptable.getLibelle()));
	         }, "L'écriture comptable n'a pas été correctement mise à jour");
	       
	       //Test UPDATE avec erreur 
	       EcritureComptable ecritureComptableRef = this.createEcritureComptable();
	       Assertions.assertFalse(ecritureComptableRef.equals(daoProxy.getComptabiliteDao().getEcritureComptableByRef(vEcritureComptable.getReference())), 
	    		   "L'écriture n'a pas été correctement insérée");  
	       
	       //Test DELETE sans erreur - on recherche une écriture qui vient bien d'être supprimée -> delete a bien fonctionné
	       Assertions.assertThrows(NotFoundException.class, () -> {
		       	 comptaManager.deleteEcritureComptable(vEcritureComptable.getId());
		         EcritureComptable ecritureRef = daoProxy.getComptabiliteDao().getEcritureComptableByRef(vEcritureComptable.getReference());
		         });
	       
	      //Test DELETE avec erreur  - on veut supprimer une écriture qui n'existe pas -> delete n'a pas fonctionné
	       Assertions.assertThrows(NotFoundException.class, () -> {
		       	 comptaManager.deleteEcritureComptable(-10);
		         });
		      
	       
	   }
   
}