package com.dummy.myerp.model.bean.comptabilite;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.dummy.myerp.technical.exception.FunctionalException;

public class JournalComptableUT {
	/**
	 * TEST UNITAIRE
	 * Test de la qualité du retour de la méthode surchargée toString
	 * Erreurs détectées dans la construction du StringBuilder de la méthode 
	 */
	@Test
	public void checkCompteToString() {
		
		JournalComptable journal = new JournalComptable("AC","Achats");
		String test = "JournalComptable{code=AC,libelle=Achats}";
		Assert.assertTrue("Problème sur la méthode toString de la classe JournalComptable", journal.toString().equals(test));
		
	}
	
	/**
	 * TEST UNITAIRE
	 * Teste la methode getByNumero()  
	 */
	@Test
	public void checkGetByCode() {
		
			JournalComptable vTest = new JournalComptable();
			vTest.setCode("AC");
			
			List<JournalComptable> testList = new ArrayList<JournalComptable>();
			testList.add(vTest);
			
			Assert.assertTrue("Problème sur la méthode GetByNumero de JournalComptable",(vTest.equals(JournalComptable.getByCode(testList, "AC"))));
			
			}	
	
	

}
