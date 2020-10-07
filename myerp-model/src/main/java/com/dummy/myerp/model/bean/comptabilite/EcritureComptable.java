package com.dummy.myerp.model.bean.comptabilite;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.TechnicalException;


/**
 * Bean représentant une Écriture Comptable
 */
@Component
public class EcritureComptable {

    // ==================== Attributs ====================
    /** The Id. */
    private Integer id;
    /** Journal comptable */
    @NotNull private JournalComptable journal;
    /** The Reference. */
    @Pattern(regexp = "\\D{1,5}-\\d{4}/\\d{5}")
    private String reference;
    /** The Date. */
    @NotNull private Date date;

    /** The Libelle. */
    @NotNull
    @Size(min = 1, max = 200)
    private String libelle;

    /** La liste des lignes d'écriture comptable. */
    @Valid
    @Size(min = 2)
    private final List<LigneEcritureComptable> listLigneEcriture = new ArrayList<>();
    
    
    // ==================== Constructeur ====================
    
    public EcritureComptable() {
    	
    }

    // ==================== Getters/Setters ====================
    public Integer getId() {
        return id;
    }
    public void setId(Integer pId) {
        id = pId;
    }
    public JournalComptable getJournal() {
        return journal;
    }
    public void setJournal(JournalComptable pJournal) {
        journal = pJournal;
    }
    public String getReference() {
        return reference;
    }
    public void setReference(String pReference) {
        reference = pReference;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date pDate) {
        date = pDate;
    }
    public String getLibelle() {
        return libelle;
    }
    public void setLibelle(String pLibelle) {
        libelle = pLibelle;
    }
    public List<LigneEcritureComptable> getListLigneEcriture() {
        return listLigneEcriture;
    }

    /**
     * Calcul et renvoie le total des montants au débit des lignes d'écriture
     *
     * @return {@link BigDecimal}, {@link BigDecimal#ZERO} si aucun montant au débit
     */
    // DONE TODO à tester
    public BigDecimal getTotalDebit() {
        BigDecimal vRetour = BigDecimal.ZERO;
        for (LigneEcritureComptable vLigneEcritureComptable : this.getListLigneEcriture())  {
            if (vLigneEcritureComptable.getDebit() != null) {
                vRetour = vRetour.add(vLigneEcritureComptable.getDebit());
            }
        }
        return vRetour;
    }
    
    
    /**
     * Calcul et renvoie le total des montants au crédit des lignes d'écriture
     *
     * @return {@link BigDecimal}, {@link BigDecimal#ZERO} si aucun montant au crédit
     */
    public BigDecimal getTotalCredit() {
        BigDecimal vRetour = BigDecimal.ZERO;
        for (LigneEcritureComptable vLigneEcritureComptable : this.getListLigneEcriture()) {
            if (vLigneEcritureComptable.getDebit() != null) {
                vRetour = vRetour.add(vLigneEcritureComptable.getCredit());// Erreur corrigée il était indiqué getDebit()
                // cela entrainait une erreur dans isEquilibree
                //le TO DO de test a été posé sur la seule la méthode getTotalDebit() 
            }
        }
        return vRetour;
    }
    
  
    /**
     * Renvoie si l'écriture est équilibrée (TotalDebit = TotalCrédit)
     * @return boolean
     */
    public boolean isEquilibree() {
        boolean vRetour = this.getTotalDebit().doubleValue()==this.getTotalCredit().doubleValue();
        return vRetour;
    }

    // ==================== Méthodes ====================
    /**
     * Nombreuses corrections
     * public String toString() {
        final StringBuilder vStB = new StringBuilder(this.getClass().getSimpleName());
        final String vSEP = ", ";
        final String vSSP = "/ ";
        vStB.append("{")
            .append("id=").append(id)
            //.append(vSEP).append("journal=").append(journal) ---------------------jounnal est un objet sans signification
              .append(vSEP).append("journal=").append(journal.getCode())
            //.append(vSEP).append("reference='").append(reference)--------- remove "'" after =
              .append(vSEP).append("reference=").append(reference)
            //.append('\'')------------------------------------------------ remove signe escape
            .append(vSEP).append("date=").append(date.toString())
            //.append(vSEP).append("libelle='").append(libelle)--------- remove "'" after =
            .append(vSEP).append("libelle=").append(libelle)
            //.append('\'')------------------------------------------------ remove signe escape
            .append(vSEP).append("totalDebit=").append(this.getTotalDebit().toPlainString())
            .append(vSEP).append("totalCredit=").append(this.getTotalCredit().toPlainString())
            .append("}");
        	/*
        	 * Plusieurs erreurs de syntaxe et inutiles car retour d'objets LigneEcriture issus de la liste 
            //.append(vSEP).append("listLigneEcriture=[\n")---------------------\n retour à la ligne, [ est inutile
              .append(vSEP).append("listLigneEcriture=")
            //.append(StringUtils.join(listLigneEcriture, "\n")).append("\n]") ---------"\n" n'est pas un séparateur, séparateur obligatoire
            .append(StringUtils.join(listLigneEcriture, ','))
    */
    @Override
    public String toString() {
        final StringBuilder vStB = new StringBuilder(this.getClass().getSimpleName());
        final String vSEP = ", ";
        final String vSSP = "/ ";
        vStB.append("{")
            .append("id=").append(id)
            .append(vSEP).append("journal=").append(journal.getCode())
            .append(vSEP).append("reference=").append(reference)
            .append(vSEP).append("date=").append(date.toString())
            .append(vSEP).append("libelle=").append(libelle)
            .append(vSEP).append("totalDebit=").append(this.getTotalDebit().toPlainString())
            .append(vSEP).append("totalCredit=").append(this.getTotalCredit().toPlainString())
            .append("}");
        return vStB.toString();
    }
    
    /**
     * Cette méthode crée une ligne d'écriture comptable pour permettre la création d'un compte comptable
     * WARNING : cette méthode aurait davantage sa place dans la classe LigneEcritureComptable
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
    
    
}
