package org.misha.bankapi.service;

import org.misha.bankapi.db.DAO.AccountDAO;

import java.math.BigDecimal;
import java.sql.SQLException;

public class AccountServiceImpl implements AccountService {
    AccountDAO accountDAO;

    public AccountServiceImpl(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @Override
    public void topUpAccountBalance(String accountNumber, BigDecimal sum) throws SQLException {
        accountDAO.addMoney(accountNumber, sum);
    }

    @Override
    public BigDecimal getAccountBalance(String accountNumber) throws SQLException {
        return accountDAO.getBalance(accountNumber);
    }
}
