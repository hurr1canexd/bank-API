package model;

import java.math.BigDecimal;

public class Card {
    private final int id;
    private String number;
    private String month;
    private String year;
    private String code;
    private BigDecimal balance;
    private int accountId;

    public Card(int id, String number, String month, String year, String code, BigDecimal balance, int accountId) {
        this.id = id;
        this.number = number;
        this.month = month;
        this.year = year;
        this.code = code;
        this.balance = balance;
        this.accountId = accountId;
    }
}
