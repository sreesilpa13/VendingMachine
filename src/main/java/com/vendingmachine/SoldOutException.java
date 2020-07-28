package com.vendingmachine;

public class SoldOutException extends RuntimeException {

	private static final long serialVersionUID = -3782408667684402759L;
	
	private String message;

    public SoldOutException(String string) {
        this.message = string;
    }

    @Override
    public String getMessage() {
        return message;
    }

}