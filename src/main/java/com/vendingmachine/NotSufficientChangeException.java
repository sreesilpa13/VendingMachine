package com.vendingmachine;

public class NotSufficientChangeException extends RuntimeException {

	private static final long serialVersionUID = 8897052216472108296L;
	
	private String message;

    public NotSufficientChangeException(String string) {
        this.message = string;
    }

    @Override
    public String getMessage() {
        return message;
    }

}