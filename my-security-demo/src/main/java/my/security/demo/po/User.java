package my.security.demo.po;

/**
 * @author zhangs
 * @Description
 * @createDate 2019/1/14
 */

public class User {
    private String userName;

    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User() {
    }
}
