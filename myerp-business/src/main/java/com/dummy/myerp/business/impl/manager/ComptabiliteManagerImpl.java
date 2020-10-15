package com.dummy.myerp.business.impl.manager;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.TransactionStatus;

import com.dummy.myerp.business.contrat.manager.ComptabiliteManager;
import com.dummy.myerp.business.impl.AbstractBusinessManager;
import com.dummy.myerp.business.impl.TransactionManager;
import com.dummy.myerp.consumer.db.DataSourcesEnum;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;


/**
 * Comptabilite manager implementation.
 */
public class ComptabiliteManagerImpl extends AbstractBusinessManager implements ComptabiliteManager {

    // ==================== Attributs ====================


    // ==================== Constructeurs ====================
    /**
     * Instantiates a new Comptabilite manager.
     */
    public ComptabiliteManagerImpl() {
    }


    // ==================== Getters/Setters ====================
    @Override
    public List<CompteComptable> getListCompteComptable() {
        return getDaoProxy().getComptabiliteDao().getListCompteComptable();
    }


    @Override
    public List<JournalComptable> getListJournalComptable() {
        return getDaoProxy().getComptabiliteDao().getListJournalComptable();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<EcritureComptable> getListEcritureComptable() {
        return getDaoProxy().getComptabiliteDao().getListEcritureComptable();
    }
    
    //=================================================================================================

    /**
     * {@inheritDoc}
     */
    // DONE 1 à tester
    @Override
    public synchronized void addReference(EcritureComptable pEcritureComptable) {
        // DONE 1 à implémenter
        // Bien se réferer à la JavaDoc de cette méthode !
        /* Le principe :
                1.  Remonter depuis la persitance la dernière valeur de la séquence du journal pour l'année de l'écriture
                    (table sequence_ecriture_comptable)
                2.  * S'il n'y a aucun enregistrement pour le journal pour l'année concernée :
                        1. Utiliser le numéro 1.
                    * Sinon :
                        1. Utiliser la dernière valeur + 1
                3.  Mettre à jour la référence de l'écriture avec la référence calculée (RG_Compta_5)
                4.  Enregistrer (insert/update) la valeur de la séquence en persitance
                    (table sequence_ecriture_comptable)
         */
    	
    	
    	//Créer et checker la référence 
    	this.createAndCheckReferenceEcritureComptableRG5(pEcritureComptable);
    	
        //Enregistrer (insert/update) la valeur de la séquence en persitance
        getDaoProxy().getComptabiliteDao().insertEcritureComptable(pEcritureComptable);
    	
    }
    
    //==============================================================================================================
    
    /*
	 * Ajoute une référence à l'écriture comptable. 
	 * RG_Compta_5 : La référence d'une écriture comptable est composée 
	 * du code du journal dans lequel figure l'écriture suivi de l'année 
	 * et d'un numéro de séquence (propre à chaque journal) sur 5 chiffres incrémenté automatiquement à chaque écriture. 
	 * Le formatage de la référence est : XX-AAAA/#####.
	 * Ex : Journal de banque (BQ), écriture au 31/12/2016
	 * BQ-2016/00001
	 * Attention : l'écriture n'est pas enregistrée en persistance
	 */
    //======== DONE 1
    @Override
    public void createAndCheckReferenceEcritureComptableRG5(EcritureComptable pEcritureComptable) {
    	//Récupère la partie XX de la référrence
    	String journalCode = pEcritureComptable.getJournal().getCode(); 
    	
    	//Recupère la partie AAAA de la référence
    	Calendar calendar = new GregorianCalendar();
    	calendar.setTime(pEcritureComptable.getDate());
    	Integer annee = calendar.get(Calendar.YEAR);
    	
    	//Récupère la dernière valeur utilisée d'une séquence dans le journal comptable 
    	JournalComptable journal = pEcritureComptable.getJournal();
    	String sequence = ""; 
    	Integer vId;
		try {
			vId = getDaoProxy().getComptabiliteDao().queryGetSequenceValueJournalPostgreSQL(DataSourcesEnum.MYERP, "myerp.ecriture_comptable", journal,
			        Integer.class);
		} catch (Exception e) {
			vId = null;
		}
    	
    	//Calcul et formatage de la part ##### de la référence
    	DecimalFormat df = new DecimalFormat("00000");
    			if (vId == null) {sequence = df.format(1) ;} 
    			else {sequence = df.format(vId + 1);}
    	
    	//Constuction du String reference au format xx-AAAA/#####
        StringBuilder vStB = new StringBuilder();
        String vSEP1 = "-"; 
        String vSEP2 = "/";
        vStB.append(journalCode)
        	.append(vSEP1)
            .append(annee)
            .append(vSEP2).append(sequence);
        String reference = vStB.toString();
            
		//Mise à jour de la référence de l'écriture avec la référence calculée (RG_Compta_5)
        pEcritureComptable.setReference(reference);
    	
    }
    
    //=====================================================================================================

    /**
     * {@inheritDoc}
     * cette méthode qui appelle 2 sous-méthodes qui ont été testées
     * Pour cela chacune des sous-méthodes a été passée en public
     * @throws FunctionalException 
     */
    // TODO à tester
    @Override
    public void checkEcritureComptable(EcritureComptable pEcritureComptable) throws FunctionalException  {
    	this.checkEcritureComptableUnit(pEcritureComptable);
        this.checkEcritureComptableContext(pEcritureComptable);
    }

    //=========================================================================================================	

    /**
     * Vérifie que l'Ecriture comptable respecte les règles de gestion unitaires,
     * c'est à dire indépendemment du contexte (unicité de la référence, exercie comptable non cloturé...)
     *
     * @param pEcritureComptable -
     * @throws FunctionalException Si l'Ecriture comptable ne respecte pas les règles de gestion
     */
    // DONE tests à compléter
    @Override
    public void checkEcritureComptableUnit(EcritureComptable pEcritureComptable) throws FunctionalException {
        // ===== Vérification des contraintes unitaires sur les attributs de l'écriture
        Set<ConstraintViolation<EcritureComptable>> vViolations = getConstraintValidator().validate(pEcritureComptable);
        if (!vViolations.isEmpty()) {
            throw new FunctionalException("L'écriture comptable ne respecte pas les règles de gestion." + vViolations.toString(),
                                          new ConstraintViolationException(
                                              "L'écriture comptable ne respecte pas les contraintes de validation",
                                              vViolations));
        }
  

        //===== RG_Compta_2 : Pour qu'une écriture comptable soit valide, elle doit être équilibrée
        if (!pEcritureComptable.isEquilibree()) 
            throw new FunctionalException("L'écriture comptable n'est pas équilibrée.");
        

        // ===== RG_Compta_3 : une écriture comptable doit avoir au moins 2 lignes d'écriture (1 au débit, 1 au crédit)
        int vNbrCredit = 0;
        int vNbrDebit = 0;
        for (LigneEcritureComptable vLigneEcritureComptable : pEcritureComptable.getListLigneEcriture()) {
            if (BigDecimal.ZERO.compareTo(ObjectUtils.defaultIfNull(vLigneEcritureComptable.getCredit(),
                                                                    BigDecimal.ZERO)) != 0) {
                vNbrCredit++;
            }
            if (BigDecimal.ZERO.compareTo(ObjectUtils.defaultIfNull(vLigneEcritureComptable.getDebit(),
                                                                    BigDecimal.ZERO)) != 0) {
                vNbrDebit++;
            }
        }
        // On test le nombre de lignes car si l'écriture à une seule ligne
        //      avec un montant au débit et un montant au crédit ce n'est pas valable
        if (pEcritureComptable.getListLigneEcriture().size() < 2
            || vNbrCredit < 1
            || vNbrDebit < 1) {
            throw new FunctionalException(
                "L'écriture comptable doit avoir au moins deux lignes : une ligne au débit et une ligne au crédit.");
        }
        
        
        // DONE 2 ===== RG_Compta_5 : Format et contenu de la référence
        // vérifier que l'année dans la référence correspond bien à la date de l'écriture, idem pour le code journal...
        
        String journalCode = pEcritureComptable.getJournal().getCode(); 
        
        Calendar calendar = new GregorianCalendar();
    	calendar.setTime(pEcritureComptable.getDate());
    	Integer year = calendar.get(Calendar.YEAR);
    	StringBuilder vStB = new StringBuilder();
    	vStB.append(year);
    	String annee = vStB.toString();
  	
        if(pEcritureComptable.getReference().contains(journalCode)==false)
        	throw new FunctionalException("Le code journal dans la référence ne correspond pas au journal où se trouve l'écriture");
        if(pEcritureComptable.getReference().contains(annee)==false)
        	throw new FunctionalException("L'année dans la référence ne correspond pas à la date de l'écriture" + annee);
        
    }
    
    //====================================================================================================================
    
    /**
     * DONE 3 ====== RG_Compta_1 : Le solde d'un compte comptable est égal à la somme des montants au débit des lignes d'écriture 
  	 * diminuées de la somme des montants au crédit. 
  	 * Si le résultat est positif, le solde est dit "débiteur", si le résultat est négatif le solde est dit "créditeur".
     * @param pEcritureComptable
     * @param pCompteComptable
     * @param solde
     * @throws FunctionalException
     */
    public void checkSoldeCompteComptableRG1(EcritureComptable pEcritureComptable, CompteComptable pCompteComptable, Integer solde) throws FunctionalException  {
    	Integer soldeCredit = 0;
    	Integer soldeDebit = 0;
        for (LigneEcritureComptable vLigneEcritureComptable : pEcritureComptable.getListLigneEcriture()) {
            if(vLigneEcritureComptable.getCompteComptable().equals(pCompteComptable)) {
            	if(vLigneEcritureComptable.getCredit()!=null) soldeCredit += vLigneEcritureComptable.getCredit().intValue();
            	if(vLigneEcritureComptable.getDebit()!=null) soldeDebit += vLigneEcritureComptable.getDebit().intValue();
            }
        }
        Integer result = soldeCredit - soldeDebit;
        if(solde!=result) 
        	throw new FunctionalException("La RG Compta 1 n'est pas respectée : le solde d'un compte comptable n'est pas égal \n"
        			+ " à la somme des montants au débit des lignes d'écriture diminuées de la somme des montants au crédit. "); 
        
    }
    
    //========================================================================================================================

    /**
     * Vérifie que l'Ecriture comptable respecte les règles de gestion liées au contexte
     * (unicité de la référence, année comptable non cloturé...)
     *
     * @param pEcritureComptable -
     * @throws FunctionalException Si l'Ecriture comptable ne respecte pas les règles de gestion
     */
    @Override
    public void checkEcritureComptableContext(EcritureComptable pEcritureComptable) throws FunctionalException {
        // ===== RG_Compta_6 : La référence d'une écriture comptable doit être unique
        if (StringUtils.isNoneEmpty(pEcritureComptable.getReference())) {
            try {
                // Recherche d'une écriture ayant la même référence
                EcritureComptable vECRef = getDaoProxy().getComptabiliteDao().getEcritureComptableByRef(
                    pEcritureComptable.getReference());

                // Si l'écriture à vérifier est une nouvelle écriture (id == null)
                // ou si elle ne correspond pas à l'écriture trouvée (id != idECRef),
                // c'est qu'il y a déjà une autre écriture avec la même référence
                if (pEcritureComptable.getId() == null
                    ||!pEcritureComptable.getId().equals(vECRef.getId())) {
                    throw new FunctionalException("Une autre écriture comptable existe déjà avec la même référence.");
                }
            } catch (NotFoundException vEx) {
                // Dans ce cas, c'est bon, ça veut dire qu'on n'a aucune autre écriture avec la même référence.
            }
        }
    }
    
    //===============================================================================================================
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void insertEcritureComptable(EcritureComptable pEcritureComptable) throws FunctionalException {
        this.checkEcritureComptable(pEcritureComptable);
        //TransactionStatus vTS = getTransactionManager().beginTransactionMyERP();------> Correction
        TransactionManager transactionManager = TransactionManager.getInstance();
        TransactionStatus vTS = transactionManager.beginTransactionMyERP();
        try {
            getDaoProxy().getComptabiliteDao().insertEcritureComptable(pEcritureComptable);
            getTransactionManager().commitMyERP(vTS);
            vTS = null;
        } finally {
            getTransactionManager().rollbackMyERP(vTS);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateEcritureComptable(EcritureComptable pEcritureComptable) throws FunctionalException {
        TransactionStatus vTS = getTransactionManager().beginTransactionMyERP();
        try {
            getDaoProxy().getComptabiliteDao().updateEcritureComptable(pEcritureComptable);
            getTransactionManager().commitMyERP(vTS);
            vTS = null;
        } finally {
            getTransactionManager().rollbackMyERP(vTS);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteEcritureComptable(Integer pId) {
        TransactionStatus vTS = getTransactionManager().beginTransactionMyERP();
        try {
            getDaoProxy().getComptabiliteDao().deleteEcritureComptable(pId);
            getTransactionManager().commitMyERP(vTS);
            vTS = null;
        } finally {
            getTransactionManager().rollbackMyERP(vTS);
        }
    }
}