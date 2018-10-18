package Transcation.service;

/**
 * BankService的功能为：某个用户有两个账户，分别为银行账户和保险账户，并且有各自的账户号，
 * 两个DAO分别用于对两个账户表的存取操作。
 */
public interface BankService {
    /**
     * transfer方法从该用户的银行账户向保险账户转帐
     * @param fromId
     * @param toId
     * @param amount
     */
    public void tramsfer(int fromId, int toId, int amount);
}
