package my.security.demo.po;

/**
 * @author zhangs
 * @Description
 * @createDate 2019/1/14
 */
public class Resource {
    private long id;

    private String url;

    private String resName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public Resource() {
    }
}
