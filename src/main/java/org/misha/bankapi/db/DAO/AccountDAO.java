package org.misha.bankapi.db.DAO;

import org.misha.bankapi.exception.AccountNotFoundException;

import java.math.BigDecimal;
import java.sql.SQLException;

public interface AccountDAO {
    void addMoney(String number, BigDecimal sum) throws AccountNotFoundException;
    BigDecimal getBalance(String accountNumber) throws AccountNotFoundException;
}
