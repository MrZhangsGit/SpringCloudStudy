package my.security.demo.po;

/**
 * @author zhangs
 * @Description
 * @createDate 2019/1/14
 */
public class RoleResource {
    private long id;

    private String rId;

    private String resId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getrId() {
        return rId;
    }

    public void setrId(String rId) {
        this.rId = rId;
    }

    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }

    public RoleResource() {
    }
}
