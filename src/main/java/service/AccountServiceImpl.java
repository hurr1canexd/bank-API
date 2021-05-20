package service;

import db.DAO.AccountDAO;

import java.math.BigDecimal;

public class AccountServiceImpl implements AccountService {
    AccountDAO accountDAO;

    public AccountServiceImpl(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @Override
    public void topUpAccountBalance(String accountNumber, BigDecimal sum) {
        accountDAO.addMoney(accountNumber, sum);
    }

    @Override
    public BigDecimal getAccountBalance(String accountNumber) {
        return accountDAO.getBalance(accountNumber);
    }
}
