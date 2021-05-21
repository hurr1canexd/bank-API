package model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

public class Card {
    private static final AtomicInteger counter = new AtomicInteger(0);
    public final @JsonProperty("id") int id;
    private final String number;
    private final String month;
    private final String year;
    private final String code;
    private BigDecimal balance;
    private @JsonProperty("status") int status; // [ждет активации, активна, закрыта] TODO: 19.05.2021 ENUM class
    private int accountId;


    public Card(@JsonProperty("number") String number,
                @JsonProperty("month") String month,
                @JsonProperty("year") String year,
                @JsonProperty("code") String code,
                @JsonProperty("balance") BigDecimal balance,
                @JsonProperty("accountId") int accountId) {
        this.number = number; // TODO: 19.05.2021 Generate randomly 
        this.month = month;
        this.year = year;
        this.code = code;
        this.balance = balance;
        this.status = 0;
        this.accountId = accountId;
        this.id = counter.incrementAndGet();
    }

    public int getId() {
        return id;
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

    public String getCode() {
        return code;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public int getStatus() {
        return status;
    }

    public int getAccountId() {
        return accountId;
    }
}
