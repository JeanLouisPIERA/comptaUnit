package com.dummy.myerp.model.bean.comptabilite;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import com.dummy.myerp.technical.exception.FunctionalException;

public class LigneEcritureComptableUT {
	
	/**
	 * TEST UNITAIRE
	 * Test de la qualité du retour de la méthode surchargée toString
	 * Erreurs détectées dans la construction du StringBuilder de la méthode 
	 */
	@Test
	public void checkLigneEcritureToString() {
		
		LigneEcritureComptable ligne = new LigneEcritureComptable() ;
		CompteComptable compte = new CompteComptable();
		
		compte.setNumero(607);
		ligne.setCompteComptable(compte);
		ligne.setLibelle("pLibelle");
		ligne.setDebit(new BigDecimal(100.0));
		ligne.setCredit(new BigDecimal (0.0));
		
		//Test sans erreur
		String test = "LigneEcritureComptable{compteComptable=CompteComptable{numero=607, libelle='null'}, libelle='pLibelle', debit=100, credit=0}";
		Assert.assertTrue("Problème sur la méthode toString de la classe LigneEcritureComptable" + "LIGNE=" + ligne + "TEST=" + test, ligne.toString().equals(test));
		
		//Test avec erreur
				String testFN = "LigneEcritureComptable{compteComptable=CompteComptable{numero=606, libelle='null'}, libelle='pLibelle', debit=100, credit=0}";
				Assert.assertFalse("Problème sur la méthode toString de la classe LigneEcritureComptable" + "LIGNE=" + ligne + "TEST=" + test, ligne.toString().equals(testFN));
		
				//Test avec erreur
				String testFL = "LigneEcritureComptable{compteComptable=CompteComptable{numero=607, libelle='false'}, libelle='pLibelle', debit=100, credit=0}";
				Assert.assertFalse("Problème sur la méthode toString de la classe LigneEcritureComptable" + "LIGNE=" + ligne + "TEST=" + test, ligne.toString().equals(testFL));		
		
		
				//Test avec erreur à itérer autant de fois que d'éléments dans les appends du StringBuilder		
				
	}
	
	 
}
