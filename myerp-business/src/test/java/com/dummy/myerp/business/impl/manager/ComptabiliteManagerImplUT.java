package com.dummy.myerp.business.impl.manager;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

import javax.validation.Configuration;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;


import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;

public class ComptabiliteManagerImplUT {
	
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
	
	
	private ComptabiliteManagerImpl manager = new ComptabiliteManagerImpl();
	
	/**
	 * Toutes les contraintes unitaires sont respectées sur les attributs de l'Ecriture Comptable pEcritureComptable
     * Aucune des contraintes unitaires n'est respectée sur les attributs de l'Ecriture Comptable vEcritureComptable
     * @throws ParseException
     */
    @Test
    //(expected=AssertionError.class)
    //(expected = FunctionalException.class)
    public void testCheckEcritureComptableUnitViolation() throws ParseException {
        EcritureComptable vEcritureComptable = new EcritureComptable();
        EcritureComptable pEcritureComptable = new EcritureComptable();
        //@NotNull
        vEcritureComptable.setJournal(new JournalComptable("", ""));
        pEcritureComptable.setJournal(new JournalComptable("AC", "Achats"));
        
        
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.FRANCE);
        String sdateTest = "2020/02/01";
        Date dateTest = simpleDateFormat.parse(sdateTest);
        //@NotNull
        vEcritureComptable.setDate(dateTest);
        pEcritureComptable.setDate(dateTest);
        
        //@Pattern Respect du regexp
        vEcritureComptable.setReference("AC******-2020/00001");
        pEcritureComptable.setReference("AC-2020/00001");
        
        //@Size min 1 max 200
        vEcritureComptable.setLibelle("Libelle");
        pEcritureComptable.setLibelle("Libelle");
        
        vEcritureComptable.getListLigneEcriture().add(vEcritureComptable.createLigne(1, 123456789000000000000.00, 0.00));	
        vEcritureComptable.getListLigneEcriture().add(vEcritureComptable.createLigne(1, 0.00, 123456789.0012345674));
        pEcritureComptable.getListLigneEcriture().add(vEcritureComptable.createLigne(1, 1234.00, 0.00));	
        pEcritureComptable.getListLigneEcriture().add(vEcritureComptable.createLigne(1, 0.00, 1234.00));
        
        Configuration<?> vConfiguration = Validation.byDefaultProvider().configure();
        ValidatorFactory vFactory = vConfiguration.buildValidatorFactory();
        Validator vValidator = vFactory.getValidator();
        Set<ConstraintViolation<EcritureComptable>> vViolations = vValidator.validate(vEcritureComptable);
        Set<ConstraintViolation<EcritureComptable>> pViolations = vValidator.validate(pEcritureComptable);
        
        //Test du cas sans Exception
        Assert.assertTrue("L'écriture comptable ne respecte pas les contraintes de validation",pViolations.isEmpty());
        //Test du cas avec Exception
        Assert.assertFalse("L'écriture comptable ne respecte pas les contraintes de validation",vViolations.isEmpty());
        
    }
    
    
    //====== VU
    /**
     * La régle RG2 
     * Pour qu'une écriture comptable soit valide, elle doit être équilibrée
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
         
        //Test du cas sans Exception
        vEcritureComptable.getListLigneEcriture().add(vEcritureComptable.createLigne(1, 1234.00, 0.00));	
        vEcritureComptable.getListLigneEcriture().add(vEcritureComptable.createLigne(2, 0.00, 1234.00));	
        Assert.assertTrue("La régle RG2 n'es pas respectée : l'écriture n'est pas équilibrée", vEcritureComptable.isEquilibree());;        
        
        //Test du cas avec Exception
        // 2 lignes la valeur au débit 1234 n'est pas égale à la valeur au crédit 1233
        vEcritureComptable.getListLigneEcriture().clear();
        vEcritureComptable.getListLigneEcriture().add(vEcritureComptable.createLigne(1, 1234.00, 0.00));	
        vEcritureComptable.getListLigneEcriture().add(vEcritureComptable.createLigne(2, 0.00, 1233.00));		
       
        Assert.assertFalse("La régle RG2 n'es pas respectée : l'écriture n'est pas équilibrée", vEcritureComptable.isEquilibree());;       
        
    }
    
    //====== VU
    /**
     * RG_Compta_3 
     * une écriture comptable doit avoir au moins 2 lignes d'écriture (1 au débit, 1 au crédit)
     * @throws ParseException 
     */
    @Test
    //(expected=AssertionError.class)
    //(expected = FunctionalException.class)
    public void testCheckEcritureComptableUnitRG3() throws ParseException  {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.FRANCE);
        String sdateTest = "2020/02/01";
        Date dateTest = simpleDateFormat.parse(sdateTest);
        vEcritureComptable.setDate(dateTest);
        vEcritureComptable.setReference("AC-2020/00001");
        vEcritureComptable.setLibelle("Libelle");
        
        //Test du cas sans Exception
        // CAS 1 : 2 lignes, 1 au débit, 1 au crédit
        vEcritureComptable.getListLigneEcriture().add(vEcritureComptable.createLigne(1, 1234.00, 0.00));	
        vEcritureComptable.getListLigneEcriture().add(vEcritureComptable.createLigne(2, 0.00,1234.00));
        int vNbrCredit = 0;
        int vNbrDebit = 0;
        for (LigneEcritureComptable vLigneEcritureComptable : vEcritureComptable.getListLigneEcriture()) {
            if (BigDecimal.ZERO.compareTo(ObjectUtils.defaultIfNull(vLigneEcritureComptable.getCredit(),
                                                                    BigDecimal.ZERO)) != 0) {
                vNbrCredit++;
            }
            if (BigDecimal.ZERO.compareTo(ObjectUtils.defaultIfNull(vLigneEcritureComptable.getDebit(),
                                                                    BigDecimal.ZERO)) != 0) {
                vNbrDebit++;
            }}
        
            Assertions.assertTrue(vNbrCredit+vNbrDebit==2, "L'écriture comptable doit avoir au moins deux lignes");
            Assertions.assertTrue(vNbrCredit==1, "L'écriture comptable doit avoir au moins une ligne au crédit");
            Assertions.assertTrue(vNbrDebit==1, "L'écriture comptable doit avoir au moins une ligne au débit");
            
         //Test du cas avec Exception   
         // CAS 2 : 2 lignes mais les 2 sont au débit
            vEcritureComptable.getListLigneEcriture().clear();
            vEcritureComptable.getListLigneEcriture().add(vEcritureComptable.createLigne(1, 1234.00, 0.00));	
            vEcritureComptable.getListLigneEcriture().add(vEcritureComptable.createLigne(2, 1234.00, 0.00));
            int pNbrCredit = 0;
            int pNbrDebit = 0;
            for (LigneEcritureComptable vLigneEcritureComptable : vEcritureComptable.getListLigneEcriture()) {
                if (BigDecimal.ZERO.compareTo(ObjectUtils.defaultIfNull(vLigneEcritureComptable.getCredit(),
                                                                        BigDecimal.ZERO)) != 0) {
                    pNbrCredit++;
                }
                if (BigDecimal.ZERO.compareTo(ObjectUtils.defaultIfNull(vLigneEcritureComptable.getDebit(),
                                                                        BigDecimal.ZERO)) != 0) {
                    pNbrDebit++;
                }}
            
                Assertions.assertFalse(pNbrCredit+vNbrDebit==2, "L'écriture comptable doit avoir au moins deux lignes");
                Assertions.assertFalse(pNbrCredit==1, "L'écriture comptable doit avoir au moins une ligne au crédit");
                Assertions.assertFalse(pNbrDebit==1, "L'écriture comptable doit avoir au moins une ligne au débit");   
             
             //Test du cas avec Exception     
             // CAS 3 : 1 seul ligne d'écriture
                vEcritureComptable.getListLigneEcriture().clear();
                vEcritureComptable.getListLigneEcriture().add(vEcritureComptable.createLigne(1, 1234.00, 0.00));	
                int rNbrCredit = 0;
                int rNbrDebit = 0;
                for (LigneEcritureComptable vLigneEcritureComptable : vEcritureComptable.getListLigneEcriture()) {
                    if (BigDecimal.ZERO.compareTo(ObjectUtils.defaultIfNull(vLigneEcritureComptable.getCredit(),
                                                                            BigDecimal.ZERO)) != 0) {
                        rNbrCredit++;
                    }
                    if (BigDecimal.ZERO.compareTo(ObjectUtils.defaultIfNull(vLigneEcritureComptable.getDebit(),
                                                                            BigDecimal.ZERO)) != 0) {
                        rNbrDebit++;
                    }}
                
                    Assertions.assertFalse(rNbrCredit+rNbrDebit==2, "L'écriture comptable doit avoir au moins deux lignes");
                    
    }
    
    //======== DONE 2
    /**
     * RG_Compta_5 
     * Format et contenu de la référence :l'année dans la référence ne correspond pas à la date de l'écriture, 
     * idem pour le code journal
     * L'enregistrement de la séquence n'est pas testé ici. Il est testé dans le test de la méthode addReference
     * @throws ParseException
     */
    @Test
    //(expected = FunctionalException.class)
    public void testCheckEcritureComptableUnitRG5() throws ParseException  {
        EcritureComptable vEcritureComptable = new EcritureComptable();
        
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.FRANCE);
        String sdateTest = "2020/02/01";
        Date dateTest = simpleDateFormat.parse(sdateTest);
        vEcritureComptable.setDate(dateTest); 
        String year = "2020";
        
    	Integer sequence = 1;
    	DecimalFormat df = new DecimalFormat("00000");
    	String enregistrement = df.format(sequence) ;
    	
    	//Test du cas sans Exception
    	String vJournalCode = "AC";
    	//Test du cas sans Exception
    	String vAnnee = "2020";
    	
    	StringBuilder vStB = new StringBuilder();
		String SEP1 = "-"; 
		String SEP2 = "/";
		vStB.append(vJournalCode)
			.append(SEP1)
		    .append(vAnnee)
		    .append(SEP2).append(enregistrement);
		String vReference = vStB.toString();
        
        vEcritureComptable.setReference(vReference);
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(vEcritureComptable.createLigne(1, 1234.00, 0.00));	
        vEcritureComptable.getListLigneEcriture().add(vEcritureComptable.createLigne(2, 0.00, 1234.00));
        
      
            Assertions.assertTrue(vEcritureComptable.getReference().contains(vEcritureComptable.getJournal().getCode()), 
            		"Le code journal dans la référence ne correspond pas au journal où se trouve l'écriture");
            
            Assertions.assertTrue(vEcritureComptable.getReference().contains(year), 
            		"Le code journal dans la référence ne correspond pas au journal où se trouve l'écriture");
            
            EcritureComptable pEcritureComptable = new EcritureComptable();

            pEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));  
            pEcritureComptable.setDate(dateTest); 
            
            //Erreur : le code journal dans la référence doit être CA et pas BQ
        	String pJournalCode = "BQ";
        	//Erreur : l'année dans la référence doit être 2020 et pas 2019
        	String pAnnee = "2019";
        	
        	StringBuilder pStB = new StringBuilder();
    		pStB.append(pJournalCode)
    			.append(SEP1)
    		    .append(pAnnee)
    		    .append(SEP2).append(enregistrement);
    		String pReference = pStB.toString();
            
            pEcritureComptable.setReference(pReference);
            pEcritureComptable.setLibelle("Libelle");
            pEcritureComptable.getListLigneEcriture().add(pEcritureComptable.createLigne(1, 1234.00, 0.00));	
            pEcritureComptable.getListLigneEcriture().add(pEcritureComptable.createLigne(2, 0.00, 1234.00)); 
            
            Assertions.assertFalse(pEcritureComptable.getReference().contains(pEcritureComptable.getJournal().getCode()), 
            		"Le code journal dans la référence ne correspond pas au journal où se trouve l'écriture");
            
            Assertions.assertFalse(pEcritureComptable.getReference().contains(year), 
            		"Le code journal dans la référence ne correspond pas au journal où se trouve l'écriture");
            
    }

}
