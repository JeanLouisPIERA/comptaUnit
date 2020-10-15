package com.dummy.myerp.consumer.dao.impl.db.dao;

import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.sql.DataSource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.dummy.myerp.consumer.dao.impl.DaoProxyImpl;
import com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite.EcritureComptableRM;
import com.dummy.myerp.consumer.db.DataSourcesEnum;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.NotFoundException;







@RunWith(SpringRunner.class)
//@Configuration("/applicationContext.xml")
//@SpringIntegrationTest()
//@ContextConfiguration(locations={"/applicationContext.xml"})
/*@ExtendWith(SpringContextResourceAdapter.class)
@ContextConfiguration("/applicationContext.xml")
@ActiveProfiles({"inttests"}) 
@Transactional
*/
@Transactional
@Rollback(true)
public class ComptabiliteDaoImplIT /*extends AbstractTransactionalJUnit4SpringContextTests*/{
	
	//@Autowired
		//ApplicationContext applicationContext;
		
		//@Autowired
		//private DaoProxy daoProxy;
		
		//private ComptabiliteDaoImpl comptabiliteDao = (ComptabiliteDaoImpl) daoProxy.getComptabiliteDao();
		//@Autowired
		//private ComptabiliteDao comptabiliteDao;
	
	
	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContext.xml", "sqlContext.xml"});
	DataSource MYERP = (DataSource) context.getBean("dataSourceMYERP"); 
	ComptabiliteDaoImpl comptabilite = (ComptabiliteDaoImpl) context.getBean("ComptabiliteDaoImpl");
	DaoProxyImpl daoProxyImpl = (DaoProxyImpl) context.getBean("DaoProxy");
	
	
	
	
	/*
	

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
		System.out.println(comptabilite); 
		
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
			/*
			Assertions.assertEquals(1, idd-id, "Echec du test d'enregistrement d'une écriture comptable en base de données");

		
			txManager.rollback(status);
			//txManager.commit(status);
		
	
	
	
		
		
	}
	
	*/
	
	
	
	
    
   

	@Test
	@Rollback
	@Transactional
	public void testInsertEcritureComptable() throws ParseException {
		
		
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
				System.out.println(comptabilite); 
				System.out.println(txManager);	
				System.out.println(def);	
				System.out.println(status);	
		
		JournalComptable vJournal = new JournalComptable();
		vJournal.setCode("AC");
		
		
		EcritureComptable ecriture = new EcritureComptable();
		//ComptabiliteDaoImpl comptabiliteDao = new ComptabiliteDaoImpl();
		
		/*
		List<EcritureComptable> list = daoProxyImpl.getComptabiliteDao().getListEcritureComptable();
		Integer id=	list.size();
		*/
		List<EcritureComptable> listEcritures = daoProxyImpl.getComptabiliteDao().getListEcritureComptable();
				
				//.getComptabiliteDao().getListEcritureComptable();
		Integer id1=listEcritures.size();
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);
        String sdateTest = "2020-02-01";
		Date pDate = simpleDateFormat.parse(sdateTest);
        
		ecriture.setDate(pDate);
		//ecriture.setId(id-4);
		ecriture.setJournal(vJournal);
		ecriture.setLibelle("Achats");
		ecriture.setReference("AC-2020/00001");
		
		
	    
		
		
        // ===== Ecriture Comptable
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(MYERP);
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue("journal_code", ecriture.getJournal().getCode());
        vSqlParams.addValue("reference", ecriture.getReference());
        vSqlParams.addValue("date", ecriture.getDate(), Types.DATE);
        vSqlParams.addValue("libelle", ecriture.getLibelle());

        String SQLinsertEcritureComptable= "INSERT INTO myerp.ecriture_comptable (\r\n" + 
        		"                    id,\r\n" + 
        		"                    journal_code, reference, date, libelle\r\n" + 
        		"                ) VALUES (\r\n" + 
        		"                    nextval('myerp.ecriture_comptable_id_seq'),\r\n" + 
        		"                    :journal_code, :reference, :date, :libelle\r\n" + 
        		"                )";
        
        vJdbcTemplate.update(SQLinsertEcritureComptable, vSqlParams);

        // ----- Récupération de l'id
        Integer vId = comptabilite.
        		queryGetSequenceValuePostgreSQL(DataSourcesEnum.MYERP, "myerp.ecriture_comptable_id_seq",
                                                           Integer.class);
        ecriture.setId(vId);

        // ===== Liste des lignes d'écriture
        daoProxyImpl.getComptabiliteDao().insertListLigneEcritureComptable(ecriture);
        
        
        //List<EcritureComptable> listEcritures = daoProxyImpl.getComptabiliteDao().getListEcritureComptable();
		//Integer id1=listEcritures.size();
		Integer id2 = daoProxyImpl.getComptabiliteDao().getListEcritureComptable().size();
		System.out.println(id1);
		System.out.println(id2);
		
		Assertions.assertEquals(1, id2-id1, "Echec du test d'enregistrement d'une écriture comptable en base de données");
        
    
		txManager.rollback(status);
		//txManager.commit(status);
	
	}
    
	@Test
	@Rollback
	@Transactional
	public void testGetEcritureComptable() throws NotFoundException, ParseException {
		
		
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(MYERP);
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue("id", -1);
        EcritureComptableRM vRM = new EcritureComptableRM();
        EcritureComptable vBean;
        String SQLgetEcritureComptable = "SELECT * FROM myerp.ecriture_comptable WHERE id = :id";
        
        vBean = vJdbcTemplate.queryForObject(SQLgetEcritureComptable, vSqlParams, vRM);
        
        Assertions.assertEquals("AC", vBean.getJournal().getCode(), "Echec du test de renvoi par son Id d'une écriture comptable persistée en base de données");
        Assertions.assertEquals("AC-2016/00001", vBean.getReference(), "Echec du test de renvoi par son Id d'une écriture comptable persistée en base de données");
       
	}
	
	
	@Test
	@Rollback
	@Transactional
	public void testUpdateEcritureComptable() throws NotFoundException, ParseException {
		
		DataSourceTransactionManager txManager = (DataSourceTransactionManager) context.getBean("txManager");
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	    TransactionStatus status = txManager.getTransaction(def);
	    
	    EcritureComptable pEcritureComptable = comptabilite.getEcritureComptable(-2); 
	    
	    JournalComptable vJournal = new JournalComptable();
		vJournal.setCode("AC");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);
        String sdateTest = "2020-02-01";
		Date pDate = simpleDateFormat.parse(sdateTest);
        
		pEcritureComptable.setDate(pDate);
		pEcritureComptable.setJournal(vJournal);
		pEcritureComptable.setLibelle("Achats");
		pEcritureComptable.setReference("AC-2020/00001");
	    
	    
	    
        // ===== Ecriture Comptable
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(MYERP);
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue("id", pEcritureComptable.getId());
        vSqlParams.addValue("journal_code", pEcritureComptable.getJournal().getCode());
        vSqlParams.addValue("reference", pEcritureComptable.getReference());
        vSqlParams.addValue("date", pEcritureComptable.getDate(), Types.DATE);
        vSqlParams.addValue("libelle", pEcritureComptable.getLibelle());

        String SQLinsertEcritureComptable= "INSERT INTO myerp.ecriture_comptable (\r\n" + 
        		"                    id,\r\n" + 
        		"                    journal_code, reference, date, libelle\r\n" + 
        		"                ) VALUES (\r\n" + 
        		"                    :id,\r\n" + 
        		"                    :journal_code, :reference, :date, :libelle\r\n" + 
        		"                )";
        comptabilite.deleteEcritureComptable(pEcritureComptable.getId());
        vJdbcTemplate.update(SQLinsertEcritureComptable, vSqlParams);

        // ===== Liste des lignes d'écriture
        comptabilite.deleteListLigneEcritureComptable(pEcritureComptable.getId());
        comptabilite.insertListLigneEcritureComptable(pEcritureComptable);
        EcritureComptable updated = comptabilite.getEcritureComptable(-2);
        
        
        Assertions.assertEquals("AC", updated.getJournal().getCode(), "Echec du test de renvoi par son Id d'une écriture comptable persistée en base de données");
        Assertions.assertEquals("AC-2020/00001", updated.getReference(), "Echec du test de renvoi par son Id d'une écriture comptable persistée en base de données");
        Assertions.assertEquals("Achats", updated.getLibelle(), "Echec du test de renvoi par son Id d'une écriture comptable persistée en base de données");
        
		txManager.rollback(status);
		//txManager.commit(status);
    }
	
	

	@Test
	@Rollback
	@Transactional
	public void getEcritureComptableByRef() throws NotFoundException {
		
		String pReference = "BQ-2016/00005";
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(MYERP);
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue("reference", pReference);
        EcritureComptableRM vRM = new EcritureComptableRM();
        EcritureComptable vBean;
        String SQLgetEcritureComptableByRef = "SELECT * FROM myerp.ecriture_comptable WHERE reference = :reference";
        
            vBean = vJdbcTemplate.queryForObject(SQLgetEcritureComptableByRef, vSqlParams, vRM);
        
            Assertions.assertEquals(-5, vBean.getId(), "Echec du test de renvoi par sa reference d'une écriture comptable persistée en base de données");
            
           
    }
		
		
		
 
	
	

}
