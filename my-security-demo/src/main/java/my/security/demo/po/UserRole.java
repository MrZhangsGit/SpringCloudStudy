package my.security.demo.po;

/**
 * @author zhangs
 * @Description
 * @createDate 2019/1/14
 */
public class UserRole {
    private long id;

    private String userName;

    private String rId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getrId() {
        return rId;
    }

    public void setrId(String rId) {
        this.rId = rId;
    }

    public UserRole() {
    }
}
