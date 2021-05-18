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
    private @JsonProperty("status") String status; // [ждет активации, активна, закрыта]
    private int accountId;


    public Card(@JsonProperty("number") String number,
                @JsonProperty("month") String month,
                @JsonProperty("year") String year,
                @JsonProperty("code") String code,
                @JsonProperty("balance") BigDecimal balance,
                @JsonProperty("accountId") int accountId) {
        this.number = number;
        this.month = month;
        this.year = year;
        this.code = code;
        this.balance = balance;
        this.status = "new";
        this.accountId = accountId;
        this.id = counter.incrementAndGet();
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", month='" + month + '\'' +
                ", year='" + year + '\'' +
                ", code='" + code + '\'' +
                ", balance=" + balance +
                ", status='" + status + '\'' +
                ", accountId=" + accountId +
                '}';
    }
}
