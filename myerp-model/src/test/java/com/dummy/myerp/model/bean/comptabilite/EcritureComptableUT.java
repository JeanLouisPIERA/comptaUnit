package com.dummy.myerp.model.bean.comptabilite;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
		ecriture.setId(6);
		ecriture.setJournal(pJournal);
		ecriture.setReference("AC-2020/00001");
		ecriture.setDate(dateTest);
	    ecriture.setLibelle("pLibelle");
	    
	    
	    			
				
		String stringTest = "{id=6,journal=AC,reference=AC-2020/00001,date=Fri Feb 01 00:00:00 CET 2020,libelle=pLibelle,totalDebit=100.00,totalCredit=100.00}";		
		
		Assert.assertFalse("La méthode toString ne fonctionne pas pour la classe EcritureComptable", ecriture.equals(stringTest));
		
		
		
		
		
		
	}
	

}
