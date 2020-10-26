package com.dummy.myerp.technical.exception;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Configuration;

@Configuration("/applicationContext.xml")
public class FunctionalExceptionTest {
	
	@Test
	private void methodTest() {
		
	}
	
	@Test
	public void checkMessageFunctionalException() {
		//test sans erreur
		Assert.assertTrue("Le message de l'exception n'a pas été correctement envoyé", new FunctionalException("pMessage").getMessage().equals(new Exception("pMessage").getMessage()));
	
		//test avec erreur
				Assert.assertFalse("Le message de l'exception n'a pas été correctement envoyé", new FunctionalException("pMessage").getMessage().equals(new Exception("vMessage").getMessage()));
	}
	

}
