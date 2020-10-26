package com.dummy.myerp.technical.exception;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class TechnicalExceptionUT {
	
	@Test
	public void checkMessageTechnicalException() {
		Assert.assertTrue("Le message de la Technical Exception n'a pas été correctement envoyé", new TechnicalException("pMessage").getMessage().equals(new Exception("pMessage").getMessage()));
	}
	
	
	@Test
	public void checkCauseTechnicalException() {
		Exception exception = new Exception();
		Throwable pcause = new Throwable();
		exception.initCause(pcause);
		
		TechnicalException technicalException = new TechnicalException(pcause);
		
		//test sans erreur
		Assert.assertTrue("Problème dans la reconnaissance de l'exception par FunctionalException", technicalException.getCause().toString().equals(exception.getCause().toString())); 
		
		Throwable vcause = null;
		TechnicalException technicalExceptionF = new TechnicalException(vcause);
		
		//test avec erreur
		Assertions.assertThrows(NullPointerException.class, () -> {
			technicalExceptionF.getCause().toString().equals(exception.getCause().toString());
	         });
	}
	
	@Test
	public void checkCauseAndMessageTechnicalException() {
		Throwable pcause = new Throwable();
		Exception exception = new Exception();
		TechnicalException technicalException = new TechnicalException("pMessage", pcause);
		exception.initCause(pcause);
		
		Assert.assertTrue("Le message de l'exception n'a pas été correctement envoyé", new TechnicalException("pMessage").getMessage().equals(new Exception("pMessage").getMessage()));
		Assert.assertTrue("Problème dans la reconnaissance de l'exception par FunctionalException", technicalException.getCause().toString().equals(exception.getCause().toString())); 
		
	}


}
