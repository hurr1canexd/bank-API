package org.misha.bankapi.service;

import org.misha.bankapi.exception.AccountNotFoundException;

import java.math.BigDecimal;
import java.sql.SQLException;

public interface AccountService {
    void topUpAccountBalance(String accountNumber, BigDecimal sum) throws AccountNotFoundException;
    BigDecimal getAccountBalance(String accountNumber) throws SQLException;
}
