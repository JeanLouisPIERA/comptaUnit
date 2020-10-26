package com.dummy.myerp.business.impl.manager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Configuration;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;

@Configuration("/applicationContext.xml")
public class ComptabiliteManagerImplTest {
	
	private ComptabiliteManagerImpl manager = new ComptabiliteManagerImpl();
	
	private EcritureComptable createEcritureComptable() throws ParseException {
			
		   	EcritureComptable vEcritureComptable = new EcritureComptable();
	    	vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
	        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.FRANCE);
	        String sdateTest = "2020/02/01";
	        Date dateTest = simpleDateFormat.parse(sdateTest);
	        vEcritureComptable.setDate(dateTest);
	        vEcritureComptable.setReference("AC-2020/00005");
	        vEcritureComptable.setLibelle("Libelle");
	        
	        CompteComptable compte = new CompteComptable();
	        compte.setNumero(606);
	        compte.setNumero(401);
	        List<LigneEcritureComptable> listLigne = new ArrayList<>();
	        LigneEcritureComptable ligne1 = vEcritureComptable.createLigne(606, 1243.00, 0.00);
	        listLigne.add(ligne1);
	        LigneEcritureComptable ligne2 = vEcritureComptable.createLigne(401, 0.00, 1243.00);
	        listLigne.add(ligne2);
	        vEcritureComptable.setListLigneEcriture(listLigne);
			
			
			return vEcritureComptable;
		}
	
    /**
     * Test intégration 
     * Vérifie que l'Ecriture comptable respecte les règles de gestion unitaires,
     * c'est à dire indépendemment du contexte (unicité de la référence, exercie comptable non cloturé...)
     * @throws ParseException
     * @throws FunctionalException 
     */
    @Test
    public void testCheckEcritureComptableUnit() throws ParseException {
        
    	//Test sans erreur
    	EcritureComptable vEcritureComptable = this.createEcritureComptable(); 
    	
    	 Assertions.assertDoesNotThrow(() -> {
         	manager.checkEcritureComptableUnit(vEcritureComptable);
             }, "L'écriture comptable ne respecte pas les règles de gestion unitaires");
        
    	 //Test avec erreur
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.FRANCE);
        String sdateTest = "2021/02/01";
        Date dateTest = simpleDateFormat.parse(sdateTest);
        vEcritureComptable.setDate(dateTest);
        vEcritureComptable.setReference("BQ-2020/00001");
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().clear();
        vEcritureComptable.getListLigneEcriture().add(vEcritureComptable.createLigne(1, 123.00, 0.00));	
        vEcritureComptable.getListLigneEcriture().add(vEcritureComptable.createLigne(2, 0.00, 125.00));
        vEcritureComptable.setId(-1);
     
        Assertions.assertThrows(FunctionalException.class, () -> {
        	manager.checkEcritureComptableUnit(vEcritureComptable);
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
    	
    	//Test sans erreur
    	EcritureComptable vEcritureComptable = this.createEcritureComptable();
    	Assertions.assertDoesNotThrow(() -> {
    		manager.createAndCheckReferenceEcritureComptableRG5(vEcritureComptable);
             }, "La référence de l'écriture comptable n'a pas été construite selon la RG5");
        
    	//Test avec erreur
    	vEcritureComptable.setReference("BQ-*****2020/00000000125");
        Assertions.assertThrows(FunctionalException.class, () -> {
        	manager.createAndCheckReferenceEcritureComptableRG5(vEcritureComptable);
          });
        
        
    }
    
  
}
