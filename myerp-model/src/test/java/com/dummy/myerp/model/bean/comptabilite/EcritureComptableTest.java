package com.dummy.myerp.model.bean.comptabilite;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.TechnicalException;

@ExtendWith(MockitoExtension.class)
public class EcritureComptableTest {
	
	@Mock
	LigneEcritureComptable ligne ;
	
	/**
	 * Methode inutile - Il ne s'agit pas d'un test - Elle est la cause d'une erreur dans les autres tests this.createLigne
	 * @param pCompteComptableNumero
	 * @param pDebit
	 * @param pCredit
	 * @return
	 
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
	 * @throws FunctionalException 
    */
	
	
	
	
	
	/**
	 * Teste la méthode qui crée une ligne d'écriture comptable pour permettre la création d'un compte comptable
     * WARNING : cette méthode aurait davantage sa place dans la classe LigneEcritureComptable
     * On compare le résultat de la méthode qui crée une ligne sur le compte comptable 2 avec la création par constructeur sur 
     * le compte comptable 1 
     * Le résultat de ce test est renvoyer une exception
	 * @throws FunctionalException
	 */
	@Test
	public void checkCreateLigne() throws FunctionalException  {
        Integer pCompteComptableNumero = 1;
        Double pDebit = 200.0;
        Double pCredit = 0.0;
		BigDecimal vDebit = pDebit == null ? null : new BigDecimal(pDebit);
        BigDecimal vCredit = pCredit == null ? null : new BigDecimal(pCredit);
        String vLibelle = ObjectUtils.defaultIfNull(vDebit, BigDecimal.ZERO)
                                     .subtract(ObjectUtils.defaultIfNull(vCredit, BigDecimal.ZERO)).toPlainString();
        LigneEcritureComptable vtest = new LigneEcritureComptable(new CompteComptable(pCompteComptableNumero),
                                                                    vLibelle,
                                                                    vDebit, vCredit);
        EcritureComptable ecriture = new EcritureComptable();
        LigneEcritureComptable ligne = ecriture.createLigne(1, 200.0, 0.0);
        Assert.assertTrue("La ligne d'écriture n'a pas été correctement créée", vtest.toString().equals(ligne.toString()));
    }
	
	
	 
    /**
     * Calcule et teste le total des montants au débit des lignes d'écriture
     *
     * @return {@link BigDecimal}, {@link BigDecimal#ZERO} si aucun montant au débit
     * @throws FunctionalException 
     * @throws TechnicalException 
     */
    @Test
    public void checkGetTotalDebit() {
        
        EcritureComptable vEcriture;
        vEcriture = new EcritureComptable();

        vEcriture.setLibelle("Equilibrée");
        vEcriture.getListLigneEcriture().add(vEcriture.createLigne(1, 200.50, 0.00));
        vEcriture.getListLigneEcriture().add(vEcriture.createLigne(1, 100.50, 33.00));
        vEcriture.getListLigneEcriture().add(vEcriture.createLigne(2, 0.00, 301.00));
        vEcriture.getListLigneEcriture().add(vEcriture.createLigne(2, 40.00, 7.00));
        
        Double retour = 341.0;
        Double dvRetour = vEcriture.getTotalCredit().doubleValue();
        Assert.assertTrue("La calcul du total des montants au débit de cette écriture est faux",dvRetour==retour.doubleValue());
    }
    
    /**
     * Calcul et teste le total des montants au crédit des lignes d'écriture
     *
     * @return {@link BigDecimal}, {@link BigDecimal#ZERO} si aucun montant au débit
     * @throws FunctionalException 
     * @throws TechnicalException 
     */
    @Test
    public void checkGetTotalCredit() {
        
        EcritureComptable vEcriture;
        vEcriture = new EcritureComptable();

        vEcriture.setLibelle("Equilibrée");
        vEcriture.getListLigneEcriture().add(vEcriture.createLigne(1, 200.50, 0.00));
        vEcriture.getListLigneEcriture().add(vEcriture.createLigne(1, 100.50, 33.00));
        vEcriture.getListLigneEcriture().add(vEcriture.createLigne(2, 0.00, 301.00));
        vEcriture.getListLigneEcriture().add(vEcriture.createLigne(2, 40.00, 7.00));
        
        Double retour = 341.0;
        Double dvRetour = vEcriture.getTotalCredit().doubleValue();
        Assert.assertTrue("La calcul du total des montants au crédit de cette écriture est faux",dvRetour==retour.doubleValue());
        
    }

    @Test
    public void checkIsEquilibreeTrue() {
    	
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
        //pEcriture.checkIsEquilibree(pEcriture);
        Assert.assertTrue("La première écriture Equilibrée n'est pas équilibrée", pEcriture.isEquilibree());
        pEcriture.getListLigneEcriture().clear();
    
        pEcriture.setLibelle("Non Equilibrée");
        pEcriture.getListLigneEcriture().add(pEcriture.createLigne(1, 10.00, 0.00));
        pEcriture.getListLigneEcriture().add(pEcriture.createLigne(1, 20.00, 1.00));
        pEcriture.getListLigneEcriture().add(pEcriture.createLigne(2, 0.00, 30.00));
        pEcriture.getListLigneEcriture().add(pEcriture.createLigne(2, 0.00, 2.00));
        pEcriture.getListLigneEcriture().add(pEcriture.createLigne(2, 1.00, 0.00));
        //Assert.assertTrue(pEcriture.toString(), vEcriture.isEquilibree());
        pEcriture.getTotalCredit();
        pEcriture.getTotalDebit();
        //pEcriture.checkIsEquilibree(pEcriture);
        Assert.assertFalse("La deuxième écriture Non Equilibrée n'est pas équilibrée", pEcriture.isEquilibree());
    }
	
    
    
    
    
}
