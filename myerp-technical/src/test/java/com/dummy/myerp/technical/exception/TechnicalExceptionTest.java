package com.dummy.myerp.technical.exception;

import org.junit.Assert;
import org.junit.Test;

public class TechnicalExceptionTest {
	
	@Test
	public void checkMessageTechnicalException() {
		Assert.assertTrue("Le message de la Technical Exception n'a pas été correctement envoyé", new TechnicalException("pMessage").getMessage().equals(new Exception("pMessage").getMessage()));
	}
	
	
	@Test
	public void checkCauseTechnicalException() {
		Exception exception = new Exception();
		Throwable pcause = new Throwable();
		exception.initCause(pcause);
		
		Exception technicalException = new TechnicalException(pcause);
		
		
		Assert.assertTrue("Problème dans la reconnaissance de l'exception par TechnicalException", technicalException.getCause().toString().equals(exception.getCause().toString())); 
		
	}
	
	@Test
	public void checkCauseAndMessageFunctionalException() {
		Throwable pcause = new Throwable();
		Exception exception = new Exception();
		TechnicalException technicalException = new TechnicalException("pMessage", pcause);
		exception.initCause(pcause);
		
		Assert.assertTrue("Le message de l'exception n'a pas été correctement envoyé", new TechnicalException("pMessage").getMessage().equals(new Exception("pMessage").getMessage()));
		Assert.assertTrue("Problème dans la reconnaissance de l'exception par FunctionalException", technicalException.getCause().toString().equals(exception.getCause().toString())); 
		
		
	}

	
	

}
