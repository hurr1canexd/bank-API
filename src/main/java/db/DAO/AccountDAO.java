package db.DAO;

import java.math.BigDecimal;
import java.sql.SQLException;

public interface AccountDAO {
    void addMoney(String number, BigDecimal sum) throws SQLException;
    BigDecimal getBalance(String accountNumber) throws SQLException;
}
