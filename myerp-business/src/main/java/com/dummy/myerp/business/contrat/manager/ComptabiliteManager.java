package com.dummy.myerp.business.contrat.manager;

import java.util.List;

import javax.inject.Named;

import org.springframework.stereotype.Component;

import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;


/**
 * Interface du manager du package comptabilite.
 */
@Named
@Component
public interface ComptabiliteManager {

    /**
     * Renvoie la liste des comptes comptables.
     *
     * @return {@link List}
     */
    List<CompteComptable> getListCompteComptable();


    /**
     * Renvoie la liste des journaux comptables.
     *
     * @return {@link List}
     */
    List<JournalComptable> getListJournalComptable();


    /**
     * Renvoie la liste des écritures comptables.
     *
     * @return {@link List}
     */
    List<EcritureComptable> getListEcritureComptable();

    /**
     * Ajoute une référence à l'écriture comptable.
     *
     * <strong>RG_Compta_5 : </strong>
     * La référence d'une écriture comptable est composée du code du journal dans lequel figure l'écriture
     * suivi de l'année et d'un numéro de séquence (propre à chaque journal) sur 5 chiffres incrémenté automatiquement
     * à chaque écriture. Le formatage de la référence est : XX-AAAA/#####.
     * <br>
     * Ex : Journal de banque (BQ), écriture au 31/12/2016
     * <pre>BQ-2016/00001</pre>
     *
     * <p><strong>Attention :</strong> l'écriture n'est pas enregistrée en persistance</p>
     * @param pEcritureComptable L'écriture comptable concernée
     * @throws FunctionalException 
     */
    void addReference(EcritureComptable pEcritureComptable) throws FunctionalException;
    
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
    void createAndCheckReferenceEcritureComptableRG5(EcritureComptable pEcritureComptable) throws FunctionalException;
    
    
    /**
     * Vérifie que l'Ecriture comptable respecte les règles de gestion.
     * Les tests n'ont pas été réalisés pour cette méthode qui appelle 2 sous-méthodes qui ont été testées
     * Pour cela chacune des sous-méthodes a été passée en public
     * @param pEcritureComptable -
     * @throws FunctionalException Si l'Ecriture comptable ne respecte pas les règles de gestion
     */
    void checkEcritureComptable(EcritureComptable pEcritureComptable) throws FunctionalException;
    
    /**
     * Vérifie que l'Ecriture comptable respecte les règles de gestion unitaires,
     * c'est à dire indépendemment du contexte (unicité de la référence, exercie comptable non cloturé...)
     *
     * @param pEcritureComptable -
     * @throws FunctionalException Si l'Ecriture comptable ne respecte pas les règles de gestion
     */
    // DONE tests à compléter
    void checkEcritureComptableUnit(EcritureComptable pEcritureComptable) throws FunctionalException;

     
      
      /**
       * Vérifie que l'Ecriture comptable respecte les règles de gestion liées au contexte
       * (unicité de la référence, année comptable non cloturé...)
       *
       * @param pEcritureComptable -
       * @throws FunctionalException Si l'Ecriture comptable ne respecte pas les règles de gestion
       */
      void checkEcritureComptableContext(EcritureComptable pEcritureComptable) throws FunctionalException; 
      

    /**
     * Insert une nouvelle écriture comptable.
     *
     * @param pEcritureComptable -
     * @throws FunctionalException Si l'Ecriture comptable ne respecte pas les règles de gestion
     */
    void insertEcritureComptable(EcritureComptable pEcritureComptable) throws FunctionalException;

    /**
     * Met à jour l'écriture comptable.
     *
     * @param pEcritureComptable -
     * @throws FunctionalException Si l'Ecriture comptable ne respecte pas les règles de gestion
     */
    void updateEcritureComptable(EcritureComptable pEcritureComptable) throws FunctionalException;

    /**
     * Supprime l'écriture comptable d'id {@code pId}.
     *
     * @param pId l'id de l'écriture
     * @throws FunctionalException 
     * @throws NotFoundException 
     */
    void deleteEcritureComptable(Integer pId) throws FunctionalException, NotFoundException;
}
