package com.dummy.myerp.business.impl.manager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Configuration;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.technical.exception.FunctionalException;

@Configuration("/applicationContext.xml")
public class ComptabiliteManagerImplTest {
	
	private ComptabiliteManagerImpl manager = new ComptabiliteManagerImpl();
	
    /**
     * Test intégration 
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
    public void testCheckSoldeCompteComptableRG1()  {
    	EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        CompteComptable cc1 = new CompteComptable();
        cc1.setNumero(1);
        CompteComptable cc2 = new CompteComptable();
        cc2.setNumero(2);
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
    public void testCreateAndCheckReferenceEcritureComptableRG5() throws ParseException {
    	EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("BQ", "Achat"));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.FRANCE);
        String sdateTest = "2021/02/01";
        Date dateTest = simpleDateFormat.parse(sdateTest);
        vEcritureComptable.setDate(dateTest);
        vEcritureComptable.setReference("AC-2020/00001");
        //manager.createAndCheckReferenceEcritureComptableRG5(vEcritureComptable);
        
        //if (!vEcritureComptable.getReference().equals("AC-2020/00001")) throw new FunctionalException("La référence" + vEcritureComptable.getReference() + "n'est pas correcte");
        
        Assertions.assertThrows(FunctionalException.class, () -> {
        	manager.createAndCheckReferenceEcritureComptableRG5(vEcritureComptable);
          });
    }
    
  
}
