package SqlUtil;

import java.util.Date;

/**
 * <desc>
 *      实体基类
 * </desc>
 *
 * @createDate 2017/09/18
 */
@ItlTables("base")
public class BasePO {

    /**
     * 主键id
     */
    @ItlField("id")
    private Integer id;

    /**
     * 状态：0删除 1正常 2禁用
     */
    @ItlField("status")
    private Integer status;

    /**
     * 创建时间
     */
    @ItlField("create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @ItlField("update_time")
    private Date updateTime;

    public BasePO() {
    }

    public BasePO(Integer id, Integer status, Date createTime, Date updateTime) {
        this.id = id;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
