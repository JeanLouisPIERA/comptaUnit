package com.dummy.myerp.business.impl.manager;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.validation.constraints.Digits;

import org.junit.Test;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;


public class ComptabiliteManagerImplTest {

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
     * Toutes les contraintes unitaires n'est respectée sur les attributs de l'Ecriture Comptable
     * Test sans erreur
     * @throws FunctionalException
     * @throws ParseException
     */
    @Test
    public void checkEcritureComptableUnit() throws FunctionalException, ParseException  {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.FRANCE);
        String sdateTest = "2020/02/01";
        Date dateTest = simpleDateFormat.parse(sdateTest);
        vEcritureComptable.setDate(dateTest);
        vEcritureComptable.setReference("AC-2020/00001");
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(vEcritureComptable.createLigne(1, 123.00, 0.00));	
        vEcritureComptable.getListLigneEcriture().add(vEcritureComptable.createLigne(2, 0.00, 123.00));
        
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }
    
    
    /**
     * Aucune des contraintes unitaires n'est respectée sur les attributs de l'Ecriture Comptable
     * Pour compromettre l'exécution du test, supprimer (expected = FunctionalException.class)
     * @throws FunctionalException
     * @throws ParseException
     */
    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitViolation() throws FunctionalException, ParseException {
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
        vEcritureComptable.setReference("AC*****2020/00001");
        
        //@Size min 1 max 200
        vEcritureComptable.setLibelle("");
        
        /*
         * CompteComptable @NotNull
         * Libelle @Size 200
         * BigDecimal debit @MontantComptable @Digits(integer = 13, fraction = 2)
         * BigDecimal credit @MontantComptable @Digits(integer = 13, fraction = 2)
         */
        
        vEcritureComptable.getListLigneEcriture().add(vEcritureComptable.createLigne(1, 1234567891000000.00256989, 0.00));	
        vEcritureComptable.getListLigneEcriture().add(vEcritureComptable.createLigne(1, 0.00, 1234567891000000.00256989));
        
        manager.checkEcritureComptableUnit(vEcritureComptable);
        
    }
    
    
    //====== VU
    /**
     * La régle RG2 n'est pas respectée 
     * Pour qu'une écriture comptable soit valide, elle doit être équilibrée
     * Pour compromettre l'exécution du test, supprimer (expected = FunctionalException.class)
     * @throws FunctionalException
     * @throws ParseException 
     */
    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG2() throws FunctionalException, ParseException  {
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
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }
    
    //====== VU
    /**
     * RG_Compta_3 non respectée 
     * une écriture comptable doit avoir au moins 2 lignes d'écriture (1 au débit, 1 au crédit)
     * Pour compromettre l'exécution du test, supprimer (expected = FunctionalException.class)
     * @throws FunctionalException
     * @throws ParseException 
     */
    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG3() throws FunctionalException, ParseException  {
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
        vEcritureComptable.getListLigneEcriture().add(vEcritureComptable.createLigne(2, 1233.00,0.00));
        manager.checkEcritureComptableUnit(vEcritureComptable);
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
    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG5() throws FunctionalException, ParseException  {
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
        manager.checkEcritureComptableUnit(vEcritureComptable);
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
    @Test(expected = FunctionalException.class)
    public void checkSoldeCompteComptableRG1() throws FunctionalException  {
    	EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        CompteComptable cc1 = new CompteComptable();
        CompteComptable cc2 = new CompteComptable();
        vEcritureComptable.getListLigneEcriture().add(vEcritureComptable.createLigne(1, 1234.00, 0.00));	
        vEcritureComptable.getListLigneEcriture().add(vEcritureComptable.createLigne(2, 0.00, 1234.00));
        vEcritureComptable.getListLigneEcriture().add(vEcritureComptable.createLigne(2, 1234.00, 0.00));	
        vEcritureComptable.getListLigneEcriture().add(vEcritureComptable.createLigne(1, 0.00, 1234.00));
        
    	manager.checkSoldeCompteComptableRG1(vEcritureComptable, cc1, 20);
        
    }
    
    //======= EN CHANTIER
    @Test
    public void checkLigneEcritureRG4() throws FunctionalException  {
    	
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
    public void createAndCheckReferenceEcritureComptableRG5() throws FunctionalException, ParseException {
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
    

}
