package com.dummy.myerp.consumer.dao.impl.db.dao;

import org.junit.Assert;

import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;

import static org.junit.Assert.assertTrue;

import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jca.context.SpringContextResourceAdapter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;


import com.dummy.myerp.consumer.dao.impl.DaoProxyImpl;
import com.dummy.myerp.consumer.db.DataSourcesEnum;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.technical.exception.NotFoundException;



//@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration("/applicationContext.xml")
@ActiveProfiles({"inttests"}) 
@Transactional
public class ComptabiliteDaoImplIT {
	
	@Autowired
	DaoProxyImpl daoProxy;
	
	@Autowired
	ComptabiliteDaoImpl comptabiliteDao;
	
	@Autowired
	DataSource dataSource; 
	
	@Autowired
	EcritureComptable ecriture; 
	
	@Autowired
	JournalComptable pJournal;
	

	@Test
	public void checkInsertEcritureComptable() throws ParseException, NotFoundException{
		
		pJournal.setCode("AC");
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.FRANCE);
        String sdateTest = "2020/02/01";
        Date pDate = simpleDateFormat.parse(sdateTest);
		ecriture.setDate(pDate);
		ecriture.setId(6);
		ecriture.setJournal(pJournal);
		ecriture.setLibelle("Achats");
		ecriture.setReference("AC-2020/00001");
	
		comptabiliteDao.insertEcritureComptable(ecriture);
		EcritureComptable ecritureTest = comptabiliteDao.getEcritureComptable(1);
		
		Assert.assertTrue("Erreur dans l'insertion des données dans Postgres"+"TEST="+comptabiliteDao.getEcritureComptableByRef("6").toString(), ecriture.getId().equals(ecritureTest.getId()));
		
	
	
	
	
		
		
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
