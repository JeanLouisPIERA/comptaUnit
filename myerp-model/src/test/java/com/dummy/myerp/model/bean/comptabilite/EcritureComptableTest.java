package com.dummy.myerp.model.bean.comptabilite;



import java.math.BigDecimal;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Configuration;

import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.TechnicalException;

@Configuration("/applicationContext.xml")
public class EcritureComptableTest {
	
	
	
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
        
        //Test sans erreur
        LigneEcritureComptable ligneT = ecriture.createLigne(1, 200.0, 0.0);
        Assert.assertTrue("La ligne d'écriture n'a pas été correctement créée", vtest.toString().equals(ligneT.toString()));
        
        //Test avec erreur
        LigneEcritureComptable ligneF = ecriture.createLigne(2, 300.0, 0.0);
        Assert.assertFalse("La ligne d'écriture n'a pas été correctement créée", vtest.toString().equals(ligneF.toString()));
        
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
        
        //Test sans erreur
        Double retourTV = 341.0;
        Double dvRetourDTV = vEcriture.getTotalDebit().doubleValue(); 
        Assert.assertTrue("La calcul du total des montants au débit de cette écriture est faux",dvRetourDTV==retourTV.doubleValue());
        
        //Test sans erreur
        Double retourTF = 342.0;
        Double dvRetourDTF = vEcriture.getTotalDebit().doubleValue(); 
        Assert.assertFalse("La calcul du total des montants au débit de cette écriture est faux",dvRetourDTF==retourTF.doubleValue());
        
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
         
        //Test sans erreur
        Double retourTV = 341.0;
        Double dvRetourCTV = vEcriture.getTotalCredit().doubleValue(); 
        Assert.assertTrue("La calcul du total des montants au crédit de cette écriture est faux",dvRetourCTV==retourTV.doubleValue());
        
        //Test sans erreur
        Double retourTF = 342.0;
        Double dvRetourCTF = vEcriture.getTotalCredit().doubleValue(); 
        Assert.assertFalse("La calcul du total des montants au crédit de cette écriture est faux",dvRetourCTF==retourTF.doubleValue());
        
        
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
        pEcriture.getTotalCredit();
        pEcriture.getTotalDebit();
        Assert.assertTrue("La première écriture Equilibrée n'est pas équilibrée", pEcriture.isEquilibree());
        pEcriture.getListLigneEcriture().clear();
    
        pEcriture.setLibelle("Non Equilibrée");
        pEcriture.getListLigneEcriture().add(pEcriture.createLigne(1, 10.00, 0.00));
        pEcriture.getListLigneEcriture().add(pEcriture.createLigne(1, 20.00, 1.00));
        pEcriture.getListLigneEcriture().add(pEcriture.createLigne(2, 0.00, 30.00));
        pEcriture.getListLigneEcriture().add(pEcriture.createLigne(2, 0.00, 2.00));
        pEcriture.getListLigneEcriture().add(pEcriture.createLigne(2, 1.00, 0.00));
        pEcriture.getTotalCredit();
        pEcriture.getTotalDebit();
        Assert.assertFalse("La deuxième écriture Non Equilibrée n'est pas équilibrée", pEcriture.isEquilibree());
    }
	
    
    
    
    
}
