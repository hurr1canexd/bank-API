package service;

import java.math.BigDecimal;
import java.sql.SQLException;

public interface AccountService {
    void topUpAccountBalance(String accountNumber, BigDecimal sum) throws SQLException;
    BigDecimal getAccountBalance(String accountNumber) throws SQLException;
}
