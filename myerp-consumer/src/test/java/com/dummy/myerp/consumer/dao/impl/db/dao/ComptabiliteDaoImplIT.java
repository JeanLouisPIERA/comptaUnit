package com.dummy.myerp.consumer.dao.impl.db.dao;

import java.math.BigDecimal;
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

import com.dummy.myerp.consumer.dao.impl.DaoProxyImpl;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;


public class ComptabiliteDaoImplIT {
	
	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContext.xml", "sqlContext.xml"});
	DataSource MYERP = (DataSource) context.getBean("dataSourceMYERP"); 
	ComptabiliteDaoImpl comptabilite = (ComptabiliteDaoImpl) context.getBean("ComptabiliteDaoImpl");
	DaoProxyImpl daoProxyImpl = (DaoProxyImpl) context.getBean("DaoProxy");
	
	private EcritureComptable setEcritureComptable(EcritureComptable pEcriture, String journalCode, String dateTest, String libelle, String reference) throws ParseException {
		
		EcritureComptable  ecriture; 
		
		if(pEcriture == null) {
		ecriture = new EcritureComptable();}
		else {
			ecriture = pEcriture; 
		}; 
		
		JournalComptable vJournal = new JournalComptable();
		vJournal.setCode(journalCode);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);
		Date pDate = simpleDateFormat.parse(dateTest);
        
		ecriture.setDate(pDate);
		ecriture.setJournal(vJournal);
		ecriture.setLibelle(libelle);
		ecriture.setReference(reference);
		
		return ecriture;
		
	}
	
	private EcritureComptable createListLigneEcritureComptable(EcritureComptable ecriture) {
		
		   List<LigneEcritureComptable> listLigneEcriture = new ArrayList<LigneEcritureComptable>();
		   
		   CompteComptable vCompte = new CompteComptable();
		   vCompte.setNumero(706);
		   LigneEcritureComptable ligneEcriture1 = new LigneEcritureComptable(vCompte, "libelle",new BigDecimal(1243.00), new BigDecimal(0.00) );
		
		   CompteComptable pCompte = new CompteComptable();
		   pCompte.setNumero(411);
		   LigneEcritureComptable ligneEcriture2 = new LigneEcritureComptable(pCompte, "libelle", new BigDecimal(0.00),new BigDecimal(1243.00) );
		   
		   listLigneEcriture.add(0, ligneEcriture1); 
		   listLigneEcriture.add(1, ligneEcriture2);
		   
	       ecriture.setListLigneEcriture(listLigneEcriture);
		
		return ecriture;
		
	}
	
	
	@Test
	public void testInsertEcritureComptable() throws ParseException, NotFoundException {
		
		
		DataSourceTransactionManager txManager = (DataSourceTransactionManager) context.getBean("txManagerMYERP");
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	    TransactionStatus status = txManager.getTransaction(def);
		
	    List<EcritureComptable> listEcritures = daoProxyImpl.getComptabiliteDao().getListEcritureComptable();		
		Integer id1=listEcritures.size();
	    
		EcritureComptable ecriture= this.setEcritureComptable(null, "AC", "2020-02-01", "Libelle", "AC-2020/00100");
		comptabilite.insertEcritureComptable(ecriture);
        
		Integer id2 = daoProxyImpl.getComptabiliteDao().getListEcritureComptable().size();
		
		//test sans erreur
		Assertions.assertEquals(1, id2-id1, "Echec du test d'enregistrement d'une écriture comptable en base de données");
		Assertions.assertTrue(daoProxyImpl.getComptabiliteDao().getEcritureComptableByRef("AC-2020/00100").getReference().toString().equals("AC-2020/00100"), "La création de l'écriture comptable n'a pas été correctement persistée");
		
		
		//test avec erreur - échec d'insertion d'une écriture nulle
		EcritureComptable dEcriture = null;
		Assertions.assertThrows(NullPointerException.class, () -> {
			comptabilite.insertEcritureComptable(dEcriture);
	         });
		
		txManager.rollback(status);
		
	}

	@Test
	public void testUpdateEcritureComptable() throws ParseException{
		
		DataSourceTransactionManager txManager = (DataSourceTransactionManager) context.getBean("txManagerMYERP");
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	    TransactionStatus status = txManager.getTransaction(def);
				
		EcritureComptable ecriture= this.setEcritureComptable(null, "AC","2020-02-01" , "Libelle", "AC-2020/01000");
		comptabilite.insertEcritureComptable(ecriture);
		
		EcritureComptable ecritureUpdate = this.setEcritureComptable(ecriture, "BQ", "2021-02-01", "LibelleUpdate", "AC-2021/01001");  
		comptabilite.updateEcritureComptable(ecritureUpdate);
		
		//test sans erreur
		Assertions.assertDoesNotThrow(() -> {
			daoProxyImpl.getComptabiliteDao().getEcritureComptableByRef(ecriture.getReference());
			Assertions.assertEquals("AC-2021/01001", daoProxyImpl.getComptabiliteDao().getEcritureComptableByRef(ecriture.getReference()).getReference());
	         }, "La mise à jour de l'écriture comptable n'a pas été correctement persistée");
		
		//test avec erreur
		Assertions.assertThrows(NotFoundException.class, () -> {
			comptabilite.getEcritureComptableByRef("AC-2020/01000");
		     });
		
		txManager.rollback(status);
		
	}
	
	@Test
	public void testInsertListLignesEcritureComptable() throws ParseException, NotFoundException{
	
	
		DataSourceTransactionManager txManager = (DataSourceTransactionManager) context.getBean("txManagerMYERP");
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	    TransactionStatus status = txManager.getTransaction(def);
				
		EcritureComptable ecriture= this.setEcritureComptable(null, "AC","2020-02-01" , "Libelle", "AC-2024/00031");
		comptabilite.insertEcritureComptable(ecriture);
		EcritureComptable ecritureTest = daoProxyImpl.getComptabiliteDao().getEcritureComptableByRef(ecriture.getReference());
		this.createListLigneEcritureComptable(ecritureTest);
		comptabilite.insertListLigneEcritureComptable(ecritureTest);
		
		//test sans erreur
		Assertions.assertDoesNotThrow(() -> {
			Assert.assertTrue(daoProxyImpl.getComptabiliteDao().getEcritureComptableByRef(ecriture.getReference()).getListLigneEcriture().size()==2);
			}, "La mise à jour des lignes de l'écriture comptable n'a pas été correctement persistée");
		
		//test avec erreur - échec d'insertion de liste des lignes d'écriture d'une écriture nulle
				EcritureComptable dEcriture = null;
				Assertions.assertThrows(NullPointerException.class, () -> {
					comptabilite.insertListLigneEcritureComptable(dEcriture);
			         });
		
		txManager.rollback(status);
		
	}
	
	@Test 
	public void testDeleteEcritureComptable() throws ParseException{
		
		DataSourceTransactionManager txManager = (DataSourceTransactionManager) context.getBean("txManagerMYERP");
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	    TransactionStatus status = txManager.getTransaction(def);
				
		EcritureComptable ecriture= this.setEcritureComptable(null, "AC","2020-02-01" , "Libelle", "AC-2020/00005");
		comptabilite.insertEcritureComptable(ecriture);
		
		//test sans erreur
		Assertions.assertThrows(NotFoundException.class, () -> {
			comptabilite.deleteEcritureComptable(ecriture.getId()); 
			daoProxyImpl.getComptabiliteDao().getEcritureComptable(ecriture.getId());
		     });
		
		//test avec erreur
		List<EcritureComptable> listEcritures = daoProxyImpl.getComptabiliteDao().getListEcritureComptable();		
		Integer id1=listEcritures.size();
		comptabilite.deleteEcritureComptable(-10); 
		Integer id2 = daoProxyImpl.getComptabiliteDao().getListEcritureComptable().size();
		Assertions.assertEquals(0, id2-id1, "Problème : une Ecriture Comptable inexistante ne peut pas être supprimée");
	
		txManager.rollback(status);
	}

	@Test 
	public void testDeleteListLignesEcritureComptable() throws ParseException{
		
		DataSourceTransactionManager txManager = (DataSourceTransactionManager) context.getBean("txManagerMYERP");
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	    TransactionStatus status = txManager.getTransaction(def);
				
		EcritureComptable ecriture= this.setEcritureComptable(null, "AC","2020-02-01" , "Libelle", "AC-2020/00006");
		comptabilite.insertEcritureComptable(ecriture);
		comptabilite.deleteListLigneEcritureComptable(ecriture.getId()); 
		
		//test sans erreur
		Assertions.assertDoesNotThrow(() -> {
			 Assert.assertTrue(daoProxyImpl.getComptabiliteDao().getEcritureComptable(ecriture.getId()).getListLigneEcriture().isEmpty());
	         }, "La liste des lignes d'écriture n'a pas été correctement supprimée");
		
		
		//test avec erreur - échec de suppresion d'une liste de lignes d'une écriture nulle
				EcritureComptable dEcriture = null;
				Assertions.assertThrows(NullPointerException.class, () -> {
					comptabilite.deleteListLigneEcritureComptable(dEcriture.getId()); 
			         });
		
	
		txManager.rollback(status);
	}
	
		
		
 
	
	

}
