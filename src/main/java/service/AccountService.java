package service;

import java.math.BigDecimal;

public interface AccountService {
    public void topUpAccountBalance(String accountNumber, BigDecimal sum);
    public BigDecimal getAccountBalance(String accountNumber);
}
