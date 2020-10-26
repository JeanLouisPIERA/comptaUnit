package com.dummy.myerp.model.bean.comptabilite;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import com.dummy.myerp.technical.exception.FunctionalException;

public class EcritureComptableUT {
	
	/**
	 * TEST UNITAIRE
	 * Test de la qualité du retour de la méthode surchargée toString
	 * Erreurs détectées dans la construction du StringBuilder de la méthode 
	 * @throws ParseException 
	 * @throws FunctionalException
	 */
	@Test
	public void checkEcritureToString() throws ParseException, FunctionalException {
		
		EcritureComptable ecriture = new EcritureComptable(); 
		JournalComptable pJournal = new JournalComptable();
		
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.FRANCE);
        String sdateTest = "2020/02/01";
        Date dateTest = simpleDateFormat.parse(sdateTest);
        BigDecimal totalCredit = new BigDecimal(100.00);
        String totalCreditString = totalCredit.toPlainString();
        BigDecimal totalDebit = new BigDecimal(100.00);
        String totalDebitString = totalDebit.toPlainString();
		
		pJournal.setCode("AC");
		ecriture.setId(20);
		ecriture.setJournal(pJournal);
		ecriture.setReference("AC-2020/00001");
		ecriture.setDate(dateTest);
	    ecriture.setLibelle("pLibelle");
	    
	    List<LigneEcritureComptable> listLigneEcriture = ecriture.getListLigneEcriture(); 
	    listLigneEcriture.add(ecriture.createLigne(1, 1234.00, 0.00));	
        listLigneEcriture.add(ecriture.createLigne(2, 0.00, 1234.00));
	    			
		//Test sans erreur		
		String stringTest = "EcritureComptable{id=20, journal=JournalComptable{code='AC', libelle='null'}, reference='AC-2020/00001', date=Sat Feb 01 00:00:00 CET 2020, libelle='pLibelle', totalDebit=1234, totalCredit=1234, listLigneEcriture=[\n"
			+"LigneEcritureComptable{compteComptable=CompteComptable{numero=1, libelle='null'}, libelle='1234', debit=1234, credit=0}"+"\n"
			+"LigneEcritureComptable{compteComptable=CompteComptable{numero=2, libelle='null'}, libelle='-1234', debit=0, credit=1234}"
			+"\n]"+"}";
			
		Assert.assertTrue("La méthode toString ne fonctionne pas pour la classe EcritureComptable" + "ECRITURE=" + ecriture + "STRINGTEST=" + stringTest, stringTest.equals(ecriture.toString()));
		
		//Test avec erreur sur la date	
		String stringTestFD = "EcritureComptable{id=20, journal=JournalComptable{code='AC', libelle='null'}, reference='AC-2020/00001', date=Sat Feb 01, libelle='pLibelle', totalDebit=1234, totalCredit=1234, listLigneEcriture=[\n"
			+"LigneEcritureComptable{compteComptable=CompteComptable{numero=1, libelle='null'}, libelle='1234', debit=1234, credit=0}"+"\n"
			+"LigneEcritureComptable{compteComptable=CompteComptable{numero=2, libelle='null'}, libelle='-1234', debit=0, credit=1234}"
			+"\n]"+"}";
			
		Assert.assertFalse("Probleme DATE : La méthode toString ne fonctionne pas pour la classe EcritureComptable" + "ECRITURE=" + ecriture + "STRINGTEST=" + stringTestFD, stringTestFD.equals(ecriture.toString()));
		
		//Test avec erreur surID		
				String stringTestFID = "EcritureComptable{id=18, journal=JournalComptable{code='AC', libelle='null'}, reference='AC-2020/00001', date=Sat Feb 01 00:00:00 CET 2020, libelle='pLibelle', totalDebit=1234, totalCredit=1234, listLigneEcriture=[\n"
					+"LigneEcritureComptable{compteComptable=CompteComptable{numero=1, libelle='null'}, libelle='1234', debit=1234, credit=0}"+"\n"
					+"LigneEcritureComptable{compteComptable=CompteComptable{numero=2, libelle='null'}, libelle='-1234', debit=0, credit=1234}"
					+"\n]"+"}";
					
				Assert.assertFalse("Problème IDLa méthode toString ne fonctionne pas pour la classe EcritureComptable" + "ECRITURE=" + ecriture + "STRINGTEST=" + stringTestFID, stringTestFID.equals(ecriture.toString()));		
		
		//Test avec erreur à itérer autant de fois que d'éléments dans les appends du StringBuilder
	
	}
	

}
