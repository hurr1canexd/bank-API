package org.misha.bankapi.service;

import org.misha.bankapi.exception.AccountNotFoundException;
import org.misha.bankapi.model.Deposit;

import java.math.BigDecimal;
import java.sql.SQLException;

public interface AccountService {
    void topUpAccountBalance(Deposit deposit) throws AccountNotFoundException;
    BigDecimal getAccountBalance(String accountNumber) throws AccountNotFoundException;
}
