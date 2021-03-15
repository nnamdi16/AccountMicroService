package com.nnamdi.account.exceptions;

public class AccountNotFoundException  extends RuntimeException{
    public AccountNotFoundException(Long id) {
        super("Could not find account " +id);
    }
}
