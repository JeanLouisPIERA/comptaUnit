package com.dummy.myerp.model.bean.comptabilite;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.dummy.myerp.technical.exception.FunctionalException;

public class SequenceEcritureComptableUT {
	
	/**
	 * TEST UNITAIRE
	 * Test de la qualité du retour de la méthode surchargée toString
	 * Erreurs détectées dans la construction du StringBuilder de la méthode 
	 * @throws FunctionalException
	 */
	@Test
	public void checkSequenceToString() {
		
		SequenceEcritureComptable sequence = new SequenceEcritureComptable(2020,1);
		
		//test sans erreur
		String test = "SequenceEcritureComptable{annee=2020, derniereValeur=1}";
		Assert.assertTrue("Problème sur la méthode toString de la classe CompteComptable"+"SEQUENCE="+sequence+"TEST="+test, sequence.toString().equals(test.toString()));
		
		//test avec erreur
		String testF = "SequenceEcritureComptable{annee=2021, derniereValeur=1}";
		Assert.assertFalse("Problème sur la méthode toString de la classe CompteComptable"+"SEQUENCE="+sequence+"TEST="+testF, sequence.toString().equals(testF.toString()));
		
		//test avec erreur
		String testFV = "SequenceEcritureComptable{annee=2020, derniereValeur=2}";
		Assert.assertFalse("Problème sur la méthode toString de la classe CompteComptable"+"SEQUENCE="+sequence+"TEST="+testFV, sequence.toString().equals(testFV.toString()));
	}
	

}
