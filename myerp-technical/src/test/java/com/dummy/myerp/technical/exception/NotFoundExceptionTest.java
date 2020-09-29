package com.dummy.myerp.technical.exception;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.params.aggregator.ArgumentAccessException;

public class NotFoundExceptionTest {
	
	@Test
	public void checkMessageNotFoundException() {
		Throwable pMessage = new Throwable();
		ArgumentAccessException extendedException = new ArgumentAccessException("pMessage");
		NotFoundException nfException = new NotFoundException("pMessage");
		
		Assert.assertTrue("Le message de l'exception n'a pas été correctement envoyé", nfException.getMessage().toString().equals(extendedException.getMessage().toString())); 
		
	}
	
	
	
	@Test
	public void checkCauseAndMessageNotFoundException() {
		Throwable pcause = new Throwable();
		ArgumentAccessException extendedException = new ArgumentAccessException("pMessage", pcause);
		NotFoundException nfException = new NotFoundException("pMessage", pcause);
		
		Assert.assertTrue("Le message de l'exception n'a pas été correctement envoyé", nfException.getMessage().toString().equals(extendedException.getMessage().toString())); 
		Assert.assertTrue("Problème dans la reconnaissance de l'exception par NotFoundException", nfException.getCause().toString().equals(extendedException.getCause().toString())); 
		
	}

}
