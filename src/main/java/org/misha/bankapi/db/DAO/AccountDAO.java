package org.misha.bankapi.db.DAO;

import org.misha.bankapi.exception.AccountNotFoundException;
import org.misha.bankapi.model.Account;

import java.math.BigDecimal;

public interface AccountDAO {
    void addMoney(String number, BigDecimal sum) throws AccountNotFoundException;
    BigDecimal getBalance(String accountNumber) throws AccountNotFoundException;
    Account getAccount(String accountNumber);
}
