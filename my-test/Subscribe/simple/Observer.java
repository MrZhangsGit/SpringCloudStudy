package Subscribe.simple;

public interface Observer {

    /**
     * 当主题状态改变时会将一个String类型字符传入该方法
     * 每个观察者都需要实现该方法
     */
    public void update(String info);
}
