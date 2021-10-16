package org.misha.bankapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public final class CardRequest {
    private final String number;
    private final String month;
    private final String year;
    private final BigDecimal balance;
    private final int accountId;

    public CardRequest(
            @JsonProperty("number") String number,
            @JsonProperty("month") String month,
            @JsonProperty("year") String year,
            @JsonProperty("balance") BigDecimal balance,
            @JsonProperty("account_id") int accountId) {
        this.number = number;
        this.month = month;
        this.year = year;
        this.balance = balance;
        this.accountId = accountId;
    }

    public String getNumber() {
        return number;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public int getAccountId() {
        return accountId;
    }
}
