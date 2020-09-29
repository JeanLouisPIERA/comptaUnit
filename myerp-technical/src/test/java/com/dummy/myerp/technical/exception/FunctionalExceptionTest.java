package com.dummy.myerp.technical.exception;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;







public class FunctionalExceptionTest {
	
	
	
	
	
	@Test
	public void checkMessageFunctionalException() {
		Assert.assertTrue("Le message de l'exception a été correctement envoyé", new FunctionalException("pMessage").getMessage().equals(new Exception("pMessage").getMessage()));
	}
	
	
	@Test
	public void checkCauseFunctionalException() {
		Exception exception = new Exception();
		Throwable pcause = new Throwable();
		exception.initCause(pcause);
		
		Exception functionalException = new FunctionalException(pcause);
		
		
		Assert.assertTrue("Problème dans la reconnaissance de l'exception par FunctionalException", functionalException.getCause().toString().equals(exception.getCause().toString())); 
		
	}
	
	@Test
	public void checkCauseAndMessageFunctionalException() {
		Throwable pcause = new Throwable();
		Exception exception = new Exception();
		FunctionalException functionalException = new FunctionalException("pMessage", pcause);
		exception.initCause(pcause);
		
		Assert.assertTrue("Le message de l'exception a été correctement envoyé", new FunctionalException("pMessage").getMessage().equals(new Exception("pMessage").getMessage()));
		Assert.assertTrue("Problème dans la reconnaissance de l'exception par FunctionalException", functionalException.getCause().toString().equals(exception.getCause().toString())); 
		
		
	}

}
