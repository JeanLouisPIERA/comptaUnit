package com.dummy.myerp.technical.exception;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class NotFoundExceptionUT {

		@Test
		public void checkNotFoundException() {
			
			Assert.assertTrue("L'exception Not Found n'a pas été correctement créée ", new NotFoundException().getClass().asSubclass(Exception.class) != null);
		
			
		
		
		}
			
		@Test
		public void checkMessageNotFoundException() {
			//test sans erreur
			Assert.assertTrue("Le message de l'exception NotFound n'a pas été correctement envoyé", new NotFoundException("pMessage").getMessage().equals(new Exception("pMessage").getMessage()));
		
			//test avec erreur
			Assert.assertFalse("Le message de l'exception NotFound n'a pas été correctement envoyé", new NotFoundException("vMessage").getMessage().equals(new Exception("pMessage").getMessage()));
		}
		
		
		@Test
		public void checkCauseFunctionalException() {
			Exception exception = new Exception();
			Throwable pcause = new Throwable();
			exception.initCause(pcause);
			
			Exception notfoundException = new NotFoundException(pcause);
			
			//test sans erreur
			Assert.assertTrue("Problème dans la reconnaissance de l'exception par FunctionalException", notfoundException.getCause().toString().equals(exception.getCause().toString())); 
			
			Throwable vcause = null;
			Exception notFoundExceptionF = new NotFoundException(vcause);
			
			//test avec erreur
			Assertions.assertThrows(NullPointerException.class, () -> {
				notFoundExceptionF.getCause().toString().equals(exception.getCause().toString());
		         });
			
		}
		
		@Test
		public void checkCauseAndMessageNotFoundException() {
			Throwable pcause = new Throwable();
			Exception exception = new Exception();
			NotFoundException notfoundException = new NotFoundException("pMessage", pcause);
			exception.initCause(pcause);
			
			Assert.assertTrue("Le message de l'exception n'a pas été correctement envoyé", new NotFoundException("pMessage").getMessage().equals(new Exception("pMessage").getMessage()));
			Assert.assertTrue("Problème dans la reconnaissance de l'exception par FunctionalException", notfoundException.getCause().toString().equals(exception.getCause().toString())); 
						
		}
			
	

}
