package com.sijan.ticketbooking.exceptions;

public class TicketBookedException extends RuntimeException{

    public TicketBookedException(String message) {
        super(message);
    }

    public TicketBookedException(String message, Throwable cause) {
        super(message, cause);
    }

    public TicketBookedException(Throwable cause) {
        super(cause);
    }
}
