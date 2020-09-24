package com.dummy.myerp.model.bean.comptabilite;



import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.dummy.myerp.technical.exception.FunctionalException;




public class CompteComptableTest {
	
	CompteComptable compte = new CompteComptable(607, "Achats");
	
	
	/**
	 * TEST INTEGRATION + TESTS UNITAIRES
	 * Test du fonctionnement correct des getters, setters et du constructeur avec les 2 paramètres	
	 * @throws FunctionalException
	 */
	@Test
	public void testGettersAndSettersAndConstructeurWith2Params() throws FunctionalException {
		
		final StringBuilder message = new StringBuilder();
		if(!compte.getLibelle().equals("Achats")) message.append("Problème sur le getter Libelle"); 
		if(compte.getNumero()!=607) message.append("Problème sur le getter Numero");
		compte.setLibelle("Ventes");
		compte.setNumero(707);
		if(!compte.getLibelle().equals("Ventes")) message.append("Problème sur le setter Libelle"); 
		if(compte.getNumero()!=707) message.append("Problème sur le getter Numero");
		
		if((message.toString().isEmpty())==false) throw new FunctionalException("message" + message);
			
	}
	
	/**
	 * TEST UNITAIRE
	 * Test de la qualité du retour de la méthode surchargée toString
	 * Erreurs détectées dans la construction du StringBuilder de la méthode 
	 * @throws FunctionalException
	 */
	@Test
	public void testCompteComptableToString() throws FunctionalException {
		
		String test = "CompteComptable{numero=607,libelle=Achats}";
		if ((compte.toString().equals(test.toString()))==false) throw new FunctionalException("Problème sur la méthode toString de la classe CompteComptable" + test + compte.toString());
		
	}
	
	
	/**
	 * TEST UNITAIRE
	 * Teste la methode getByNumero()  
	 * @throws FunctionalException
	 */
	@Test
	public void testGetByNumero() throws FunctionalException {
	        CompteComptable c607 = new CompteComptable(607);
			List<CompteComptable> testList = new ArrayList<CompteComptable>();
			testList.add(c607);
			
			CompteComptable vTest = new CompteComptable();
			vTest.setNumero(607);
			
	        for (CompteComptable vCompteFromList : testList) {
	            if (vCompteFromList != null && Objects.equals(vCompteFromList.getNumero(), 607)) {
	                vCompteFromList=c607;
	                break;
	            }      
	            if((vTest.equals(vCompteFromList))==false) throw new FunctionalException("");
		        }
		    }
	}
