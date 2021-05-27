package org.misha.bankapi.service;

import org.misha.bankapi.db.DAO.AccountDAO;
import org.misha.bankapi.exception.AccountNotFoundException;
import org.misha.bankapi.model.Deposit;

import java.math.BigDecimal;
import java.sql.SQLException;

public class AccountServiceImpl implements AccountService {
    AccountDAO accountDAO;

    public AccountServiceImpl(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @Override
    public void topUpAccountBalance(Deposit deposit) throws AccountNotFoundException {
        accountDAO.addMoney(deposit.getAccountNumber(), deposit.getSum());
    }

    @Override
    public BigDecimal getAccountBalance(String accountNumber) throws SQLException {
        return accountDAO.getBalance(accountNumber);
    }
}
