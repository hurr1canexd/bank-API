package db.DAO;

import java.math.BigDecimal;

public interface AccountDAO {
    public void addMoney(String number, BigDecimal sum);
    public BigDecimal getBalance(String accountNumber);
}
