package org.misha.bankapi.service.impl;

import org.misha.bankapi.db.DAO.AccountDAO;
import org.misha.bankapi.exception.AccountNotFoundException;
import org.misha.bankapi.model.Deposit;
import org.misha.bankapi.service.AccountService;

import java.math.BigDecimal;

public class AccountServiceImpl implements AccountService {
    private final AccountDAO accountDAO;

    public AccountServiceImpl(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @Override
    public void topUpAccountBalance(Deposit deposit) throws AccountNotFoundException {
        if (!accountExists(deposit.getAccountNumber())) {
            throw new AccountNotFoundException();
        }
        accountDAO.addMoney(deposit.getAccountNumber(), deposit.getSum());
    }

    @Override
    public BigDecimal getAccountBalance(String accountNumber) throws AccountNotFoundException {
        if (!accountExists(accountNumber)) {
            throw new AccountNotFoundException();
        }
        return accountDAO.getBalance(accountNumber);
    }

    @Override
    public boolean accountExists(String accountNumber) {
        return accountDAO.getAccount(accountNumber) != null;
    }
}
