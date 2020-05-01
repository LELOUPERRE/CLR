package fr.christophelouer.commons.preconds;

import org.junit.Assert;
import org.junit.Test;

import fr.christophelouer.commons.preconds.Precondition;
import fr.christophelouer.commons.preconds.Precondition.PreconditionException;

public class PreconditionsTest
{

	public static class BooleanUtils
	{
		public static boolean returnFalse()
		{
			return false;
		}

		public static boolean returnTrue()
		{
			return true;
		}

	}

	@SuppressWarnings("serial")
	public static class MyException extends RuntimeException
	{
		public MyException()
		{
			super("My Exception");
		}
	}

	@Test
	public void testMessage()
	{	
		try
		{
			Precondition.check(BooleanUtils::returnFalse, "user message");
		}
		catch (PreconditionException preconditionException)
		{
			Assert.assertEquals("Precondition failure : user message", preconditionException.getMessage());
		}
	}
	
	@Test
	public void testMessageFormat()
	{
		try
		{
			Precondition.check(BooleanUtils::returnFalse, "user message %d, %s",10,"OK");
		}
		catch (PreconditionException preconditionException)
		{
			Assert.assertEquals("Precondition failure : user message 10, OK", preconditionException.getMessage());
		}
	}

	@Test
	public void testException()
	{
		try
		{
			Precondition.check(BooleanUtils::returnFalse, MyException::new);
		}
		catch (MyException ex)
		{
			Assert.assertEquals("My Exception", ex.getMessage());
		}
	}

}
