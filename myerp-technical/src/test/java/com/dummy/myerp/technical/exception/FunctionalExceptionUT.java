package com.dummy.myerp.technical.exception;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;


public class FunctionalExceptionUT {
	
	@Test
	public void checkMessageFunctionalException() {
		
		//test sans erreur
		Assert.assertTrue("Le message de l'exception n'a pas été correctement envoyé", new FunctionalException("pMessage").getMessage().equals(new Exception("pMessage").getMessage()));
	
		//test avec erreur
		Assert.assertFalse("Le message de l'exception n'a pas été correctement envoyé", new FunctionalException("pMessage").getMessage().equals(new Exception("vMessage").getMessage()));
			
	}
	
	@Test
	public void checkCauseFunctionalException() {
		Exception exception = new Exception();
		Throwable pcause = new Throwable();
		exception.initCause(pcause);
		
		Exception functionalException = new FunctionalException(pcause);
		
		//test sans erreur
		Assert.assertTrue("Problème dans la reconnaissance de l'exception par FunctionalException", functionalException.getCause().toString().equals(exception.getCause().toString())); 
		
		Throwable vcause = null;
		Exception functionalExceptionF = new FunctionalException(vcause);
		
		//test avec erreur
		Assertions.assertThrows(NullPointerException.class, () -> {
			functionalExceptionF.getCause().toString().equals(exception.getCause().toString());
	         });
	}
	
	@Test
	public void checkCauseAndMessageFunctionalException() {
		Throwable pcause = new Throwable();
		Exception exception = new Exception("pMessage");
		exception.initCause(pcause);
		
		//test sans erreur
		FunctionalException functionalException = new FunctionalException("pMessage");
		Assert.assertTrue("Le message de l'exception n'a pas été correctement envoyé", functionalException.getMessage().equals(exception.getMessage()));
		
		
		//test avecerreur
		FunctionalException functionalExceptionF = new FunctionalException("vMessage");
				Assert.assertFalse("Le message de l'exception n'a pas été correctement envoyé", functionalExceptionF.getMessage().equals(exception.getMessage()));
				
		
	}

}
