package Transcation.service.impl;

import Transcation.dao.FailureBankDao;
import Transcation.dao.FailureInsuranceDao;
import Transcation.service.BankService;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 在BankService中，我们可以先调用FailureBankDao的withdraw方法取出一定金额的存款，
 * 再调用FailureInsuranceDao的deposit方法将该笔存款存入保险账户表中，一切看似OK
 */
public class FailureBankServiceImpl implements BankService{
    private FailureBankDao failureBankDao;
    private FailureInsuranceDao failureInsuranceDao;
    private DataSource dataSource;

    public void setFailureBankDao(FailureBankDao failureBankDao) {
        this.failureBankDao = failureBankDao;
    }

    public void setFailureInsuranceDao(FailureInsuranceDao failureInsuranceDao) {
        this.failureInsuranceDao = failureInsuranceDao;
    }

    @Override
    public void tramsfer(int fromId, int toId, int amount) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            failureBankDao.withdraw(fromId, amount);
            failureInsuranceDao.deposit(toId, amount);

            connection.commit();
        } catch (Exception e) {
            try {
                assert  connection != null;
                connection.rollback();
            } catch (SQLException e2) {
                System.out.println(e2);
            }
        } finally {
            try {
                assert connection != null;
                connection.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }
}
