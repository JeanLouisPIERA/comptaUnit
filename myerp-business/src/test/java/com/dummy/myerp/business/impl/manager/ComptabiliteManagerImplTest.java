package com.dummy.myerp.business.impl.manager;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.junit.Test;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;


public class ComptabiliteManagerImplTest {

    private ComptabiliteManagerImpl manager = new ComptabiliteManagerImpl();

    @Test
    public void checkEcritureComptableUnit() throws FunctionalException, ParseException  {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.FRANCE);
        String sdateTest = "2020/02/01";
        Date dateTest = simpleDateFormat.parse(sdateTest);
        vEcritureComptable.setDate(dateTest);
        vEcritureComptable.setReference("AC-2020/00001");
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                                                                                 "Libelle",
                                                                                 new BigDecimal(123), null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                                                                                 "Libelle", null,
                                                                                 new BigDecimal(123)));
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }
    

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitViolation() throws FunctionalException {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }
    
    
    //====== VU
    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG2() throws FunctionalException  {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                                                                                 null, null, new BigDecimal(1234)
                                                                                 ));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                                                                                 null, null,
                                                                                 new BigDecimal(1234)));
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }
    
    //====== VU
    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG3() throws FunctionalException  {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                                                                                 null, new BigDecimal(123),
                                                                                 null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                                                                                 null, new BigDecimal(123),
                                                                                 null));
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }
    
    //======== DONE 2
    @Test
    public void checkEcritureComptableUnitRG5() throws FunctionalException, ParseException  {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        String journalCode = "AC";
        
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.FRANCE);
        String sdateTest = "2020/02/01";
        Date dateTest = simpleDateFormat.parse(sdateTest);
        vEcritureComptable.setDate(dateTest);
        
        Calendar calendar = new GregorianCalendar();
    	calendar.setTime(dateTest);
    	Integer annee = calendar.get(Calendar.YEAR);
    	
    	Integer sequence = 1;
    	DecimalFormat df = new DecimalFormat("00000");
    	String enregistrement = df.format(sequence) ;
    	
    	StringBuilder vStB = new StringBuilder();
		String vSEP1 = "-"; 
		String vSEP2 = "/";
		vStB.append(journalCode)
			.append(vSEP1)
		    .append(annee)
		    .append(vSEP2).append(enregistrement);
		String reference = vStB.toString();
        
        vEcritureComptable.setReference(reference);
        
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                                                                                 null, new BigDecimal(123),
                                                                                 null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                                                                                 null, null,
                                                                                 new BigDecimal(123)));
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }
    
    
    //======= DONE 3
    @Test
    public void checkSoldeCompteComptableRG1() throws FunctionalException  {
    	EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        CompteComptable cc1 = new CompteComptable();
        CompteComptable cc2 = new CompteComptable();
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(cc1,"Libelle",
                new BigDecimal(123), null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(cc2,"Libelle", null,
                new BigDecimal(123)));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(cc2,"Libelle",
                new BigDecimal(133), null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(cc1,"Libelle", null,
                new BigDecimal(133)));
    	manager.checkSoldeCompteComptableRG1(vEcritureComptable, cc1, 10);
        
    }
    
    //======= EN CHANTIER
    @Test
    public void checkLigneEcritureRG4() throws FunctionalException  {
    	
    }
    
    //====== DONE 1
    @Test
    public void createAndCheckReferenceEcritureComptableRG5() throws FunctionalException, ParseException {
    	EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.FRANCE);
        String sdateTest = "2020/02/01";
        Date dateTest = simpleDateFormat.parse(sdateTest);
        vEcritureComptable.setDate(dateTest);
        manager.createAndCheckReferenceEcritureComptableRG5(vEcritureComptable);
        
        if (!vEcritureComptable.getReference().equals("AC-2020/00001")) throw new FunctionalException("La référence" + vEcritureComptable.getReference() + "n'est pas correcte");
        
    }
    

}
