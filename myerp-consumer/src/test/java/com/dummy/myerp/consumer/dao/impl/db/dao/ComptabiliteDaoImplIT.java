package com.dummy.myerp.consumer.dao.impl.db.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;

import java.sql.Connection;
import java.sql.DriverManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.integration.test.context.MockIntegrationContext;
import org.springframework.integration.test.context.SpringIntegrationTest;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.dummy.myerp.consumer.dao.contrat.DaoProxy;
import com.dummy.myerp.consumer.dao.impl.DaoProxyImpl;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;

import com.dummy.myerp.technical.exception.NotFoundException;


@RunWith(SpringRunner.class)
@SpringIntegrationTest()
@ContextConfiguration(locations={"classpath:/applicationContext.xml","classpath:/sqlContext.xml"})
/*@ExtendWith(SpringContextResourceAdapter.class)
@ContextConfiguration("/applicationContext.xml")
@ActiveProfiles({"inttests"}) 
@Transactional
*/
@Transactional
@Rollback(true)
public class ComptabiliteDaoImplIT extends AbstractTransactionalJUnit4SpringContextTests{
	
	@Autowired
	ApplicationContext applicationContext;
	
	//@Autowired
	//private DaoProxy daoProxy;
	
	//private ComptabiliteDaoImpl comptabiliteDao = (ComptabiliteDaoImpl) daoProxy.getComptabiliteDao();
	//@Autowired
	//private ComptabiliteDao comptabiliteDao;
	
	ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
	DataSource MYERP = (DataSource) context.getBean("dataSourceMYERP"); 
	//ComptabiliteDaoImpl comptabilite = (ComptabiliteDaoImpl) context.getBean("DaoProxy");
	DaoProxyImpl daoProxyImpl = (DaoProxyImpl) context.getBean("DaoProxy");
		
	
	

	@Test
	@Rollback
	@Transactional
	public void checkInsertEcritureComptable() throws ParseException, NotFoundException{
		
		
		DataSourceTransactionManager txManager = (DataSourceTransactionManager) context.getBean("txManager");
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	    TransactionStatus status = txManager.getTransaction(def);
        
		
		//System.out.println(applicationContext);
		//System.out.println(daoProxy);
		//System.out.println(comptabiliteDao);
		System.out.println(context);
		//System.out.println(comptabilite);
		System.out.println(daoProxyImpl);
		System.out.println(MYERP); 
		
		JournalComptable vJournal = new JournalComptable();
		vJournal.setCode("AC");
		
		
		EcritureComptable ecriture = new EcritureComptable();
		//ComptabiliteDaoImpl comptabiliteDao = new ComptabiliteDaoImpl();
		
		List<EcritureComptable> list = daoProxyImpl.getComptabiliteDao().getListEcritureComptable();
		Integer id=	list.size();
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);
        String sdateTest = "2020-02-01";
		Date pDate = simpleDateFormat.parse(sdateTest);
        
		ecriture.setDate(pDate);
		//ecriture.setId(id-4);
		ecriture.setJournal(vJournal);
		ecriture.setLibelle("Achats");
		ecriture.setReference("AC-2020/00001");

		
			daoProxyImpl.getComptabiliteDao().insertEcritureComptable(ecriture);
			
			
			
			Integer idd = daoProxyImpl.getComptabiliteDao().getListEcritureComptable().size();
			System.out.println(id);
			System.out.println(idd);
			
			/*
			EcritureComptable ecritureTest = daoProxyImpl.getComptabiliteDao().getEcritureComptable(id-4);
			
			
			//Assertions.assertEquals(ecritureTest.getId(), ecriture.getId(), "Erreur d'enregistrement de l'ID de l'éciture comptable insérée");
			Assertions.assertEquals(ecritureTest.getJournal().getCode(), ecriture.getJournal().getCode(), "Erreur d'enregistrement du code Journal de l'éciture comptable insérée");
			Assertions.assertEquals(ecritureTest.getLibelle(), ecriture.getLibelle(), "Erreur d'enregistrement du libellé de l'éciture comptable insérée");
			Assertions.assertEquals(ecritureTest.getReference(), ecriture.getReference(), "Erreur d'enregistrement de la référence de l'éciture comptable insérée");
			Assertions.assertEquals(ecritureTest.getTotalCredit(), ecriture.getTotalCredit(), "Erreur d'enregistrement du total Crédit de l'éciture comptable insérée");
			Assertions.assertEquals(ecritureTest.getTotalDebit(), ecriture.getTotalDebit(), "Erreur d'enregistrement du total Débit de l'éciture comptable insérée");
			Assertions.assertEquals(ecritureTest.getListLigneEcriture().size(), ecriture.getListLigneEcriture().size(), "Erreur d'enregistrement du nombre de lignes de l'éciture comptable insérée");
			Assertions.assertEquals(ecritureTest.getDate().toString(), simpleDateFormat.format(ecriture.getDate()), "Erreur d'enregistrement de la Date de l'éciture comptable insérée");
		*/
			
			Assertions.assertEquals(1, idd-id, "Erreur d'enregistrement du code Journal de l'éciture comptable insérée");

		
			txManager.rollback(status);
			//txManager.commit(status);
		
	
	
	
		
		
	}
	
	
    
	
    
	
	
	

	/*
	@Container
    private PostgreSQLContainer postgresqlContainer = new PostgreSQLContainer()
        .withDatabaseName("foo")
        .withUsername("foo")
        .withPassword("secret");

    @Test
    void test() {
        
        assertTrue(postgresqlContainer.isRunning());
    }
    
    private ComptabiliteDaoImpl comptabiliteDao;
    
    @Test
    void testInteractionWithDatabase() throws ParseException {
        //ScriptUtils.runInitScript(new JdbcDatabaseDelegate(postgresqlContainer, ""),"01_create_schema.sql");
        
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(comptabiliteDao.getDataSource(DataSourcesEnum.MYERP));
        
        EcritureComptable ecriture = new EcritureComptable();
		JournalComptable pJournal = new JournalComptable();
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.FRANCE);
        String sdateTest = "2020/02/01";
        Date pDate = simpleDateFormat.parse(sdateTest);
		ecriture.setDate(pDate);
		ecriture.setId(1);
		ecriture.setJournal(pJournal);
		ecriture.setLibelle("Achats");
		ecriture.setReference("AC-2020/00001");
        
        
        
        comptabiliteDao.insertEcritureComptable(ecriture);
        
        
        Collection<EcritureComptable> ecritures = vJdbcTemplate.query("select * From ecriture",
                    (resultSet, i) -> new EcritureComptable (
                    							resultSet.getDate("date"),
                                                resultSet.getInt("Id"),
                                                resultSet.getString("journal"),
                                                result.getString("libelle"),
                                                result.getString("reference")
                                                ))));
 
        Assert.assertTrue("L'écriture n'a pas été correctement persistée",(ecritures).hasSize(1));
    }
    */

}
