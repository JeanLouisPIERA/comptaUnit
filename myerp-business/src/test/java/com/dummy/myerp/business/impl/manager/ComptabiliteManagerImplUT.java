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
     * Aucune des contraintes unitaires n'est respectée sur les attributs de l'Ecriture Comptable
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
        vEcritureComptable.setDate(dateTest);
        
        //@Pattern Respect du regexp
        vEcritureComptable.setReference("AC******-2020/00001");
        
        //@Size min 1 max 200
        vEcritureComptable.setLibelle("Libelle");
        
        vEcritureComptable.getListLigneEcriture().add(vEcritureComptable.createLigne(1, 123456789000000000000.00, 0.00));	
        vEcritureComptable.getListLigneEcriture().add(vEcritureComptable.createLigne(1, 0.00, 123456789.0012345674));
        
        Configuration<?> vConfiguration = Validation.byDefaultProvider().configure();
        ValidatorFactory vFactory = vConfiguration.buildValidatorFactory();
        Validator vValidator = vFactory.getValidator();
        Set<ConstraintViolation<EcritureComptable>> vViolations = vValidator.validate(vEcritureComptable);
        
        Assert.assertTrue("L'écriture comptable ne respecte pas les contraintes de validation",!vViolations.isEmpty());
        
    }
    
    
    //====== VU
    /**
     * La régle RG2 n'est pas respectée 
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
        // 2 lignes la valeur au débit 1234 n'est pas égale à la valeur au crédit 1233
        vEcritureComptable.getListLigneEcriture().add(vEcritureComptable.createLigne(1, 1234.00, 0.00));	
        vEcritureComptable.getListLigneEcriture().add(vEcritureComptable.createLigne(2, 0.00, 1233.00));		
        
        Assert.assertTrue("La régle RG2 n'es pas respectée : l'écriture n'est pas équilibrée", vEcritureComptable.isEquilibree()==false);;        
       
    }
    
    //====== VU
    /**
     * RG_Compta_3 non respectée 
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
        // 2 lignes mais les 2 sont au débit
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
        
        if (vEcritureComptable.getListLigneEcriture().size() < 2
                || vNbrCredit < 1
                || vNbrDebit < 1) {
                
            Assertions.assertTrue(vNbrCredit+vNbrDebit<2, "L'écriture comptable doit avoir au moins deux lignes");
            Assertions.assertTrue(vNbrCredit<1, "L'écriture comptable doit avoir au moins une ligne au crédit");
            Assertions.assertTrue(vNbrDebit<1, "L'écriture comptable doit avoir au moins une ligne au débit");
        }
        
        
       
    }
    
    //======== DONE 2
    /**
     * RG_Compta_5 non respectée 
     * Format et contenu de la référence :l'année dans la référence ne correspond pas à la date de l'écriture, 
     * idem pour le code journal
     * L'enregistrement de la séquence n'est pas testé ici. Il est testé dans le test de la méthode addReference
     * @throws ParseException
     */
    @Test
    //(expected = FunctionalException.class)
    public void testCheckEcritureComptableUnitRG5() throws ParseException  {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.FRANCE);
        String sdateTest = "2020/02/01";
        Date dateTest = simpleDateFormat.parse(sdateTest);
        vEcritureComptable.setDate(dateTest); 
        String year = "2020";
        
    	Integer sequence = 1;
    	DecimalFormat df = new DecimalFormat("00000");
    	String enregistrement = df.format(sequence) ;
    	
    	//Erreur : le code journal dans la référence doit être CA et pas BQ
    	String journalCode = "AC";
    	//Erreur : l'année dans la référence doit être 2020 et pas 2019
    	String annee = "2020";
    	
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
        vEcritureComptable.getListLigneEcriture().add(vEcritureComptable.createLigne(2, 1233.00, 0.00));
        
       
            Assertions.assertTrue(vEcritureComptable.getReference().contains(vEcritureComptable.getJournal().getCode()), 
            		"Le code journal dans la référence ne correspond pas au journal où se trouve l'écriture");
            
            Assertions.assertTrue(vEcritureComptable.getReference().contains(year), 
            		"Le code journal dans la référence ne correspond pas au journal où se trouve l'écriture");
            
    }

}
