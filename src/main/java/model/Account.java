package model;

import java.math.BigDecimal;

public class Account {
    private final int id;
    private BigDecimal balance;
    private int clientId;

    public Account(int id, BigDecimal balance, int clientId) {
        this.id = id;
        this.balance = balance;
        this.clientId = clientId;
    }
}
