package model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.concurrent.atomic.AtomicInteger;

public class Client {
    private static final AtomicInteger counter = new AtomicInteger(0);
    private final @JsonProperty("id") int id;
    private String firstName;
    private String lastName;

    public Client(@JsonProperty("firstName") String firstName,
                  @JsonProperty("lastName") String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = counter.incrementAndGet();
    }
}
