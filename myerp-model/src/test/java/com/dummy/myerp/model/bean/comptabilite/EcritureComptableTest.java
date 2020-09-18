package com.dummy.myerp.model.bean.comptabilite;

import java.math.BigDecimal;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.Assert;
import org.junit.Test;

import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.TechnicalException;


public class EcritureComptableTest {
	
	/**
	 * Methode inutile - Il ne s'agit pas d'un test - Elle est la cause d'une erreur dans les autres tests this.createLigne
	 * @param pCompteComptableNumero
	 * @param pDebit
	 * @param pCredit
	 * @return
	 */
    public LigneEcritureComptable createLigne(Integer pCompteComptableNumero, Double pDebit, Double pCredit) {
		
        BigDecimal vDebit = pDebit == null ? null : new BigDecimal(pDebit);
        BigDecimal vCredit = pCredit == null ? null : new BigDecimal(pCredit);
        String vLibelle = ObjectUtils.defaultIfNull(vDebit, BigDecimal.ZERO)
                                     .subtract(ObjectUtils.defaultIfNull(vCredit, BigDecimal.ZERO)).toPlainString();
        LigneEcritureComptable vRetour = new LigneEcritureComptable(new CompteComptable(pCompteComptableNumero),
                                                                    vLibelle,
                                                                    vDebit, vCredit);
        return vRetour;
    }
    
    
    /**
     * Calcul et renvoie le total des montants au débit des lignes d'écriture
     *
     * @return {@link BigDecimal}, {@link BigDecimal#ZERO} si aucun montant au débit
     * @throws FunctionalException 
     * @throws TechnicalException 
     */
    @Test
    public void checkGetTotalDebit() throws FunctionalException {
        
        EcritureComptable vEcriture;
        vEcriture = new EcritureComptable();

        vEcriture.setLibelle("Equilibrée");
        vEcriture.getListLigneEcriture().add(vEcriture.createLigne(1, 200.50, 0.00));
        vEcriture.getListLigneEcriture().add(vEcriture.createLigne(1, 100.50, 33.00));
        vEcriture.getListLigneEcriture().add(vEcriture.createLigne(2, 0.00, 301.00));
        vEcriture.getListLigneEcriture().add(vEcriture.createLigne(2, 40.00, 7.00));
        
        vEcriture.checkGetTotalDebit(vEcriture, 341.0);
    }
    
    /**
     * Calcul et renvoie le total des montants au crédit des lignes d'écriture
     *
     * @return {@link BigDecimal}, {@link BigDecimal#ZERO} si aucun montant au débit
     * @throws FunctionalException 
     * @throws TechnicalException 
     */
    @Test
    public void checkGetTotalCredit() throws FunctionalException {
        
        EcritureComptable vEcriture;
        vEcriture = new EcritureComptable();

        vEcriture.setLibelle("Equilibrée");
        vEcriture.getListLigneEcriture().add(vEcriture.createLigne(1, 200.50, 0.00));
        vEcriture.getListLigneEcriture().add(vEcriture.createLigne(1, 100.50, 33.00));
        vEcriture.getListLigneEcriture().add(vEcriture.createLigne(2, 0.00, 301.00));
        vEcriture.getListLigneEcriture().add(vEcriture.createLigne(2, 40.00, 7.00));
        
        vEcriture.checkGetTotalCredit(vEcriture, 341.0);
    }

    @Test
    public void checkIsEquilibreeTrue() throws FunctionalException{
    	
    	EcritureComptable pEcriture = new EcritureComptable();
        
        pEcriture.setLibelle("Equilibrée");
        pEcriture.getListLigneEcriture().add(pEcriture.createLigne(1, 200.50, 0.00));
        pEcriture.getListLigneEcriture().add(pEcriture.createLigne(1, 100.50, 33.00));
        pEcriture.getListLigneEcriture().add(pEcriture.createLigne(2, 0.00, 301.00));
        pEcriture.getListLigneEcriture().add(pEcriture.createLigne(2, 40.00, 0.00));
        pEcriture.getListLigneEcriture().add(pEcriture.createLigne(2, 0.00, 7.00));
        //Assert.assertTrue(vEcriture.toString(), vEcriture.isEquilibree());
        pEcriture.getTotalCredit();
        pEcriture.getTotalDebit();
        pEcriture.checkIsEquilibree(pEcriture);
    }
    /**
     * 2ème partie du test précédent
     * le clear ne sert à rien
     * Test inutile Doublon du test checkIsEquilibreeTrue()
     * @throws FunctionalException
     */
    @Test(expected = FunctionalException.class)
    public void checkIsEquilibreeFalse() throws FunctionalException{    
    	EcritureComptable pEcriture = new EcritureComptable();
        pEcriture.setLibelle("Non Equilibrée");
        pEcriture.getListLigneEcriture().add(pEcriture.createLigne(1, 10.00, 0.00));
        pEcriture.getListLigneEcriture().add(pEcriture.createLigne(1, 20.00, 1.00));
        pEcriture.getListLigneEcriture().add(pEcriture.createLigne(2, 0.00, 30.00));
        pEcriture.getListLigneEcriture().add(pEcriture.createLigne(2, 0.00, 2.00));
        pEcriture.getListLigneEcriture().add(pEcriture.createLigne(2, 1.00, 0.00));
        //Assert.assertTrue(pEcriture.toString(), vEcriture.isEquilibree());
        pEcriture.getTotalCredit();
        pEcriture.getTotalDebit();
        pEcriture.checkIsEquilibree(pEcriture);
    }

}
