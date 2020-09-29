package com.dummy.myerp.technical.exception;

import org.junit.Assert;
import org.junit.Test;






public class FunctionalExceptionTest {
	
	
	@Test
	public void checkFunctionalException() {
		Exception exception = new Exception();
		Throwable pcause = new Throwable();
		exception.initCause(pcause);
		
		Exception functionalException = new FunctionalException(pcause);
		
		
		Assert.assertTrue("Problème dans la reconnaissance de l'exception par FunctionalException", functionalException.getCause().toString().equals(exception.getCause().toString())); 
		
	}

}
