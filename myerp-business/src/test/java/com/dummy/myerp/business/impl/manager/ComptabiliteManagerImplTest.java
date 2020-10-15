package com.dummy.myerp.business.impl.manager;



import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.Locale;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito.Then;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;

import com.dummy.myerp.business.contrat.manager.ComptabiliteManager;
import com.dummy.myerp.business.impl.TransactionManager;
import com.dummy.myerp.business.impl.manager.ComptabiliteManagerImpl;
import com.dummy.myerp.consumer.dao.impl.DaoProxyImpl;
import com.dummy.myerp.consumer.dao.impl.db.dao.ComptabiliteDaoImpl;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;
import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;

import junit.framework.AssertionFailedError;

@Configuration("/applicationContext.xml")
//@ExtendWith(MockitoExtension.class)
public class ComptabiliteManagerImplTest {
	
	/* ------------------------------------MOCKITO EN SUSPENS 
	@Mock
	EcritureComptable ecriture;
	
	@Mock
	LigneEcritureComptable ligneEcriture;
	
	ComptabiliteManager comptaManager;
	
	@BeforeEach
	public void init() {
	comptaManager = new ComptabiliteManagerImpl();
	}
    ----------------------------------------------------------*/
	
	
	
	
	private ComptabiliteManagerImpl manager = new ComptabiliteManagerImpl();
	
	  
    
	
    /**
     * Ce bout de code est erroné dans toutes les méthodes de test fournies
     * il instantie les lignes avec new au lieu d'utiliser la méthode createLigne de la classe EcritureComptable
     *  vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                                                                                 "Libelle",
                                                                                 new BigDecimal(123), null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                                                                                 "Libelle", null,
                                                                                 new BigDecimal(123)));
     */
     
	
	

    /**
     * Toutes les contraintes unitaires sont respectées sur les attributs de l'Ecriture Comptable
     * Test sans erreur
     * @throws ParseException
     * @throws FunctionalException 
     */
    @Test
    public void testCheckEcritureComptableUnit() throws ParseException, FunctionalException  {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.FRANCE);
        String sdateTest = "2021/02/01";
        Date dateTest = simpleDateFormat.parse(sdateTest);
        vEcritureComptable.setDate(dateTest);
        vEcritureComptable.setReference("BQ-2020/00001");
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(vEcritureComptable.createLigne(1, 123.00, 0.00));	
        vEcritureComptable.getListLigneEcriture().add(vEcritureComptable.createLigne(2, 0.00, 125.00));
        vEcritureComptable.setId(-1);
        
        
        
        
        Assertions.assertThrows(FunctionalException.class, () -> {
        	manager.checkEcritureComptableUnit(vEcritureComptable);
          });
      
        
    }
    
    
    /**
     * Aucune des contraintes unitaires n'est respectée sur les attributs de l'Ecriture Comptable
     * Pour compromettre l'exécution du test, supprimer (expected = FunctionalException.class)
     * @throws FunctionalException
     * @throws ParseException
     */
    @Test
    //(expected=AssertionError.class)
    //(expected = FunctionalException.class)
    public void testCheckEcritureComptableUnitViolation() throws ParseException {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        //@NotNull
        vEcritureComptable.setJournal(new JournalComptable("", ""));
        
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.FRANCE);
        String sdateTest = "2020/02/01";
        Date dateTest = simpleDateFormat.parse(sdateTest);
        //@NotNull
        //vEcritureComptable.setDate(dateTest);
        
        //@Pattern Respect du regexp
        vEcritureComptable.setReference("AC******-2020/00001");
        
        //@Size min 1 max 200
        vEcritureComptable.setLibelle("Libelle");
        
        /*
         * CompteComptable @NotNull
         * Libelle @Size 200
         * BigDecimal debit @MontantComptable @Digits(integer = 13, fraction = 2)
         * BigDecimal credit @MontantComptable @Digits(integer = 13, fraction = 2)
         */
        
        vEcritureComptable.getListLigneEcriture().add(vEcritureComptable.createLigne(1, 123456789000000000000.00, 0.00));	
        vEcritureComptable.getListLigneEcriture().add(vEcritureComptable.createLigne(1, 0.00, 123456789.0012345674));
        
        Assertions.assertThrows(FunctionalException.class, () -> {
        	manager.checkEcritureComptableUnit(vEcritureComptable);
          });
        
       
        
    }
    
    
    //====== VU
    /**
     * La régle RG2 n'est pas respectée 
     * Pour qu'une écriture comptable soit valide, elle doit être équilibrée
     * Pour compromettre l'exécution du test, supprimer (expected = FunctionalException.class)
     * @throws FunctionalException
     * @throws ParseException 
     */
    @Test
    //(expected=AssertionError.class)
    //(expected = FunctionalException.class)
    public void testCheckEcritureComptableUnitRG2() throws ParseException  {
    	
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.FRANCE);
        String sdateTest = "2020/02/01";
        Date dateTest = simpleDateFormat.parse(sdateTest);
        vEcritureComptable.setDate(dateTest);
        vEcritureComptable.setReference("AC-2020/00001");
        vEcritureComptable.setLibelle("Libelle");
        // 2 lignes la valeur au débit 1234 n'est pas égale à la valeur au crédit 1233
        vEcritureComptable.getListLigneEcriture().add(vEcritureComptable.createLigne(1, 1234.00, 0.00));	
        vEcritureComptable.getListLigneEcriture().add(vEcritureComptable.createLigne(2, 0.00, 1233.00));		
        
                
        Assertions.assertThrows(FunctionalException.class, () -> {
        	manager.checkEcritureComptableUnit(vEcritureComptable);
          });	
		    
			
        
        //test pour commit sur webhook Jenkins
   	
    	//GIVEN
    	/*
    	LigneEcritureComptable ligne1 = ecriture.createLigne(1, 1234.00, 0.00);
    	LigneEcritureComptable ligne2 = ecriture.createLigne(2, 0.00, 1233.00);
    	ecriture.getListLigneEcriture().add(ligne1);
    	ecriture.getListLigneEcriture().add(ligne2);
    	when(ecriture.isEquilibree()).thenThrow(new FunctionalException("L'écriture n'est pas équilibrée"));
    	*/
    	/**
    	doThrow(new FunctionalException("L'écriture n'est pas équilibrée")).when(ecriture.getListLigneEcriture()).add(ecriture.createLigne(1, 1234.00, 0.00)); 
    	ecriture.getListLigneEcriture().add(ecriture.createLigne(2, 0.00, 1233.00));
    	
    	
    	//WHEN
    	comptaManager.checkEcritureComptableUnit(ecriture);
    	LigneEcritureComptable ligne1 = ecriture.createLigne(1, 1234.00, 0.00);
    	LigneEcritureComptable ligne2 = ecriture.createLigne(2, 0.00, 1233.00);
    	ecriture.getListLigneEcriture().add(ligne1);
    	ecriture.getListLigneEcriture().add(ligne2);
    	
    	
    	//THEN
    	verify(ecriture).checkIsEquilibree(ecriture);
    	doThrow(new FunctionalException("L'écriture n'est pas équilibrée"));
    	**/
                
    }
    
    //====== VU
    /**
     * RG_Compta_3 non respectée 
     * une écriture comptable doit avoir au moins 2 lignes d'écriture (1 au débit, 1 au crédit)
     * Pour compromettre l'exécution du test, supprimer (expected = FunctionalException.class)
     * @throws FunctionalException
     * @throws ParseException 
     */
    @Test
    //(expected=AssertionError.class)
    //(expected = FunctionalException.class)
    public void testCheckEcritureComptableUnitRG3() throws FunctionalException, ParseException  {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.FRANCE);
        String sdateTest = "2020/02/01";
        Date dateTest = simpleDateFormat.parse(sdateTest);
        vEcritureComptable.setDate(dateTest);
        vEcritureComptable.setReference("AC-2020/00001");
        vEcritureComptable.setLibelle("Libelle");
        // 2 lignes mais les 2 sont au débit
        vEcritureComptable.getListLigneEcriture().add(vEcritureComptable.createLigne(1, 1234.00, 0.00));	
        vEcritureComptable.getListLigneEcriture().add(vEcritureComptable.createLigne(2, 1234.00,0.00));
        
       
        
        Assertions.assertThrows(FunctionalException.class, () -> {
        	manager.checkEcritureComptableUnit(vEcritureComptable);
          });
        
       
    }
    
    //======== DONE 2
    /**
     * RG_Compta_5 non respectée 
     * Format et contenu de la référence :l'année dans la référence ne correspond pas à la date de l'écriture, 
     * idem pour le code journal
     * L'enregistrement de la séquence n'est pas testé ici. Il est testé dans le test de la méthode addReference
     * Pour compromettre l'exécution du test, supprimer (expected = FunctionalException.class)
     * @throws FunctionalException
     * @throws ParseException
     */
    @Test
    //(expected = FunctionalException.class)
    public void testCheckEcritureComptableUnitRG5() throws FunctionalException, ParseException  {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.FRANCE);
        String sdateTest = "2020/02/01";
        Date dateTest = simpleDateFormat.parse(sdateTest);
        vEcritureComptable.setDate(dateTest);
        
    	Integer sequence = 1;
    	DecimalFormat df = new DecimalFormat("00000");
    	String enregistrement = df.format(sequence) ;
    	
    	//Erreur : le code journal dans la référence doit être CA et pas BQ
    	String journalCode = "BQ";
    	//Erreur : l'année dans la référence doit être 2020 et pas 2019
    	String annee = "2019";
    	
    	/**
    	//Pour exécution du test sans erreur masquer les 2 //Erreur ci-dessus
    	String journalCode = "AC";
        Calendar calendar = new GregorianCalendar();
    	calendar.setTime(dateTest);
    	Integer annee = calendar.get(Calendar.YEAR);
        
    	*/
    	
    	StringBuilder vStB = new StringBuilder();
		String vSEP1 = "-"; 
		String vSEP2 = "/";
		vStB.append(journalCode)
			.append(vSEP1)
		    .append(annee)
		    .append(vSEP2).append(enregistrement);
		String reference = vStB.toString();
        
        vEcritureComptable.setReference(reference);
        
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(vEcritureComptable.createLigne(1, 1234.00, 0.00));	
        vEcritureComptable.getListLigneEcriture().add(vEcritureComptable.createLigne(2, 0.00, 1234.00));
        
        Assertions.assertThrows(FunctionalException.class, () -> {
        	manager.checkEcritureComptableUnit(vEcritureComptable);
          });
        
        
    }
    
    
    //======= DONE 3
    /**
     * RG_Compta_1 non respectée 
     * Le solde d'un compte comptable est égal à la somme des montants au débit des lignes d'écriture 
     * diminuées de la somme des montants au crédit. 
     * Si le résultat est positif, le solde est dit "débiteur", si le résultat est négatif le solde est dit "créditeur".
     * Ici le solde est passé en paramètre pour une valeur de 20 alors que solde du compte cc1 passé en paramètre est 10
     * Pour compromettre l'exécution du test, supprimer (expected = FunctionalException.class)
     * @throws FunctionalException
     */
    @Test
    //(expected = FunctionalException.class)
    public void testCheckSoldeCompteComptableRG1() throws FunctionalException  {
    	EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        CompteComptable cc1 = new CompteComptable();
        CompteComptable cc2 = new CompteComptable();
        vEcritureComptable.getListLigneEcriture().add(vEcritureComptable.createLigne(1, 1234.00, 0.00));	
        vEcritureComptable.getListLigneEcriture().add(vEcritureComptable.createLigne(2, 0.00, 1234.00));
        vEcritureComptable.getListLigneEcriture().add(vEcritureComptable.createLigne(2, 1234.00, 0.00));	
        vEcritureComptable.getListLigneEcriture().add(vEcritureComptable.createLigne(1, 0.00, 1234.00));
       
        
    	
    	Assertions.assertThrows(FunctionalException.class, () -> {
    		manager.checkSoldeCompteComptableRG1(vEcritureComptable, cc1, 20);
          });
    }
    
    
    
    //====== DONE 1
    /**
     * Le test construit une référence sans erreur 
     * Les éléments donnés sont : code journal et année
     * En l'absence d'enregistrement, la méthode construit une valeur de séquence au format 00001 : correct
     * @throws FunctionalException
     * @throws ParseException
     */
    @Test
    public void testCreateAndCheckReferenceEcritureComptableRG5() throws FunctionalException, ParseException {
    	EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.FRANCE);
        String sdateTest = "2020/02/01";
        Date dateTest = simpleDateFormat.parse(sdateTest);
        vEcritureComptable.setDate(dateTest);
        manager.createAndCheckReferenceEcritureComptableRG5(vEcritureComptable);
        
        if (!vEcritureComptable.getReference().equals("AC-2020/00001")) throw new FunctionalException("La référence" + vEcritureComptable.getReference() + "n'est pas correcte");
        
    }
    
    	
    
    
    
    
    
    //------------------------------------------RESTE A TESTER
    
   
    //=======================================GETTERS ET SETTERS
    /*
    @Test
    public void getListCompteComptable() {
    	
    	
    }

    @Test
    public  void getListJournalComptable() {
    	List<JournalComptable> list;
    }

    @Test
    public void getListEcritureComptable() {
    	List<EcritureComptable> list;
    }
	
	*/
	
	
	
	//======================================Tests d'intégration liés au module consumer
	//======================================Problèmes de connexion à la base de données
	
	/*
	@Test
	public void testCheckEcritureComptableContext() {
	EcritureComptable vEcritureComptable = new EcritureComptable();
	
	vEcritureComptable.setReference("vReference");
	vEcritureComptable.setId(20);
	
	try {
		manager.checkEcritureComptableContext(vEcritureComptable);
	} catch (FunctionalException e) {
		Assert.assertTrue(e.getCause().getMessage(), e.getCause().getClass().equals(FunctionalException.class));
	}
	}
	
	
	
	@Test
	public void testCheckEcritureComptable() throws ParseException {
		
	
		EcritureComptable vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.FRANCE);
        String sdateTest = "2020/02/01";
        Date dateTest = simpleDateFormat.parse(sdateTest);
        vEcritureComptable.setDate(dateTest);
        vEcritureComptable.setReference("AC-2020/00001");
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(vEcritureComptable.createLigne(1, 123.00, 0.00));	
        vEcritureComptable.getListLigneEcriture().add(vEcritureComptable.createLigne(2, 0.00, 123.00));
    	vEcritureComptable.setReference("vReference");
    	vEcritureComptable.setId(20);
    	
    	
        
    	try {
    		manager.checkEcritureComptable(vEcritureComptable);
    	} catch (FunctionalException e) {
    		Assert.assertTrue(e.getCause().getMessage(), e.getCause().getClass().equals(FunctionalException.class));
    	}
    	
  
	}
	
	@Test
	public void addReference() {
		EcritureComptable pEcritureComptable; 
	}
	

    @Test
    public void insertEcritureComptable() throws FunctionalException {
    	EcritureComptable pEcritureComptable;
    }
    

    @Test
    public void updateEcritureComptable() throws FunctionalException {
    	EcritureComptable pEcritureComptable;
    }

    @Test
    public void deleteEcritureComptable() {
    	Integer pId; 
    }
    
*/
}
