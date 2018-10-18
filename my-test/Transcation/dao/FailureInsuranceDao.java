package Transcation.dao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 定义保险账户的DAO
 */
public class FailureInsuranceDao {
    private DataSource dataSource;

    public FailureInsuranceDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 向保险账户表（INSURANCE_ACCOUNT）存入amount数量的金额
     * @param insuranceId
     * @param amount
     * @throws SQLException
     */
    public void deposit(int insuranceId, int amount) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement selectStatement = connection.prepareStatement("SELECT INSURANCE_AMOUNT FROM INSURANCE_ACCOUNT WHERE INSURANCE_ID = ?");
        selectStatement.setInt(1, insuranceId);
        ResultSet resultSet = selectStatement.executeQuery();
        resultSet.next();
        int previousAmount = resultSet.getInt(1);
        resultSet.close();
        selectStatement.close();

        int newAmount = previousAmount + amount;
        PreparedStatement updateStatement = connection.prepareStatement("UPDATE INSURANCE_ACCOUNT SET INSURANCE_AMOUNT = ? WHERE INSURANCE_ID = ?");
        updateStatement.setInt(1, newAmount);
        updateStatement.setInt(2, insuranceId);
        updateStatement.execute();

        updateStatement.close();
        connection.close();
    }
}
