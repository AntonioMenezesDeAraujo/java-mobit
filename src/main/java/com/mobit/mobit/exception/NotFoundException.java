package com.mobit.mobit.exception;

public class NotFoundException extends RuntimeException{

	private static final long serialVersionUID = -321485740194030779L;

	public NotFoundException() {
		super();
	}
	
	public NotFoundException(String message) {
		super(message, null);
	}
}
