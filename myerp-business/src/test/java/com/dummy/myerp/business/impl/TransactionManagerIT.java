package com.dummy.myerp.business.impl;


import javax.sql.DataSource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.dummy.myerp.consumer.ConsumerHelper;
import com.dummy.myerp.consumer.dao.contrat.DaoProxy;
import com.dummy.myerp.consumer.dao.impl.DaoProxyImpl;



public class TransactionManagerIT {
	
	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"classpath*:applicationContext.xml", "transactionContext.xml", "transactionContext.xml"} );
	DataSource MYERP = (DataSource) context.getBean("dataSourceMYERP");
	DataSourceTransactionManager ptmMyERP = (DataSourceTransactionManager) context.getBean("txManagerMYERP");
	TransactionManager pTransactionManager = (TransactionManager) context.getBean("TransactionManager");
	DaoProxyImpl DaoProxyImpl = (DaoProxyImpl) context.getBean("DaoProxy");
	ConsumerHelper consumerHelper = (ConsumerHelper) context.getBean("ConsumerHelper");
	DaoProxy daoProxy = ConsumerHelper.getDaoProxy();

	private TransactionStatus createStatus() {
	DefaultTransactionDefinition vTDef = new DefaultTransactionDefinition();
    vTDef.setName("Transaction_txManagerMyERP");
    vTDef.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

    TransactionStatus status = ptmMyERP.getTransaction(vTDef);
	return status;
	}
	
	@Test
	public  void testBeginTransactionMyERP() {
		
		TransactionStatus status = this.createStatus();
		pTransactionManager.beginTransactionMyERP();
        
		//test sans erreur
        Assertions.assertTrue(status.isNewTransaction()==true, "Echec du test la méthode beginTransactionMyERP : la transaction n'a pas été créée");
    
        //test avec erreur
        Assertions.assertFalse(status.isNewTransaction()==false, "Echec du test la méthode beginTransactionMyERP : la transaction n'a pas été créée");
	}
	
	@Test
	public void commitMyERP() {
		
		TransactionStatus status = this.createStatus();
		pTransactionManager.commitMyERP(status);
		
		//test sans erreur
        Assertions.assertTrue(status.isCompleted()==true, "Echec du test sur la méthode commitMyERP : la transaction n'a pas été committée");
        
        //test avec erreur
        Assertions.assertFalse(status.isCompleted()==false, "Echec du test sur la méthode commitMyERP : la transaction n'a pas été committée");
    }
	
	@Test
	public void rollbackMyERP() {
		
		TransactionStatus status = this.createStatus();
		pTransactionManager.rollbackMyERP(status);
		
		//test avec erreur
        Assertions.assertTrue(status.isCompleted()==true, "Echec du test sur la méthode rollbackMyERP : la transaction n'a pas été rolled-back");
        
        //test sans erreur
        Assertions.assertFalse(status.isCompleted()==false, "Echec du test sur la méthode rollbackMyERP : la transaction n'a pas été rolled-back");
    }
	
}