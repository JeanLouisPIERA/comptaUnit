package com.dummy.myerp.business.impl;


import javax.sql.DataSource;

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

import com.dummy.myerp.business.impl.manager.ComptabiliteManagerImpl;


@RunWith(SpringRunner.class)
@Transactional
@Rollback(true)
public class TransactionManagerIT {
	


	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContext.xml", "sqlContext.xml"});
	DataSource MYERP = (DataSource) context.getBean("dataSourceMYERP");
	DataSourceTransactionManager ptmMyERP = (DataSourceTransactionManager) context.getBean("txManager");
	
	
	
	@Test
	public  void testBeginTransactionMyERP() {
		
        DefaultTransactionDefinition vTDef = new DefaultTransactionDefinition();
        vTDef.setName("Transaction_txManagerMyERP");
        vTDef.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        TransactionStatus status = ptmMyERP.getTransaction(vTDef);
        
        
        Assertions.assertTrue(status.isNewTransaction()==true, "Echec du test la méthode beginTransactionMyERP : la transaction n'a pas été créée");
    }
	
	@Test
	public void commitMyERP() {
		
		DefaultTransactionDefinition vTDef = new DefaultTransactionDefinition();
        vTDef.setName("Transaction_txManagerMyERP");
        vTDef.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        TransactionStatus status = ptmMyERP.getTransaction(vTDef);
        if (status != null) {
            ptmMyERP.commit(status);
        }
        
        
        Assertions.assertTrue(status.isCompleted()==true, "Echec du test sur la méthode commitMyERP : la transaction n'a pas été committée");
    }
	
	@Test
	public void rollbackMyERP() {
		
		DefaultTransactionDefinition vTDef = new DefaultTransactionDefinition();
        vTDef.setName("Transaction_txManagerMyERP");
        vTDef.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        TransactionStatus status = ptmMyERP.getTransaction(vTDef);
        if (status != null) {
           
            ptmMyERP.rollback(status);
        }
        
        
        Assertions.assertTrue(status.isCompleted()==true, "Echec du test sur la méthode rollbackMyERP : la transaction n'a pas été rolled-back");
    }
	
}