package com.dummy.myerp.model.bean.comptabilite;




import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;


import com.dummy.myerp.technical.exception.FunctionalException;




public class CompteComptableUT {
	
	
	/**
	 * TEST UNITAIRE
	 * Test de la qualité du retour de la méthode surchargée toString
	 * Erreurs détectées dans la construction du StringBuilder de la méthode 
	 * @throws FunctionalException
	 */
	@Test
	public void checkCompteToString() {
		
		CompteComptable compte = new CompteComptable(608,"Achats");
		
		//Test sans erreur
		String test = "CompteComptable{numero=608, libelle='Achats'}";
		Assert.assertTrue("Problème sur la méthode toString de la classe CompteComptable"+ "test=" + test + "compte=" + compte, compte.toString().equals(test));
		
		//Test avec erreur
		String testF = "CompteComptable{numero=707, libelle='Ventes'}";
		Assert.assertFalse("Problème sur la méthode toString de la classe CompteComptable"+ "test=" + test + "compte=" + compte, compte.toString().equals(testF));
		
	}
	
	
	
	/**
	 * TEST UNITAIRE
	 * Teste la methode getByNumero()  
	 */
	@Test
	public void checkGetByNumero() {
		
			CompteComptable vTest = new CompteComptable();
			
			List<CompteComptable> testList = new ArrayList<CompteComptable>();
			testList.add(vTest);
			
			//Test sans erreur
			vTest.setNumero(607);
			Assert.assertTrue("Problème sur la méthode GetByNumero",(vTest.equals(CompteComptable.getByNumero(testList, 607))));
			
			//Test avec erreur
			vTest.setNumero(608);
			Assert.assertFalse("Problème sur la méthode GetByNumero",(vTest.equals(CompteComptable.getByNumero(testList, 607))));
			
			}	
	
	}


