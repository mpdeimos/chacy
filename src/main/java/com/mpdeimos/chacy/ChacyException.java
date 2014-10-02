package com.mpdeimos.chacy;

/** Generic exception for handling all kinds of parsing and conversion errors. */
public class ChacyException extends Exception
{
	/** Constructor. */
	public ChacyException(String message)
	{
		super(message);
	}
}
