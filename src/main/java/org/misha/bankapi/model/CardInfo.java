package org.misha.bankapi.model;

public class CardInfo {
    private final int id;
    private final String number;

    public CardInfo(int id, String number) {
        this.id = id;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }
}
