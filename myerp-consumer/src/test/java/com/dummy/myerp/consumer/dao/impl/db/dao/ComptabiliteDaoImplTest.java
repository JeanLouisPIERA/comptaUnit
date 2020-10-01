package com.dummy.myerp.consumer.dao.impl.db.dao;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;

import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.dummy.myerp.consumer.db.DataSourcesEnum;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.technical.exception.NotFoundException;

//@Testcontainers
public class ComptabiliteDaoImplTest {
	
	/*
	public ComptabiliteDaoImpl comptabiliteDao = ComptabiliteDaoImpl.getInstance();
	
	@Test
	public void checkInsertEcritureComptable() throws ParseException, NotFoundException {
		
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
		EcritureComptable exritureTest = comptabiliteDao.getEcritureComptable(1);
		
		Assert.assertTrue("", ecriture.getId().equals(exritureTest));
		
		
	}
    
	
    
	*/
	
	

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
