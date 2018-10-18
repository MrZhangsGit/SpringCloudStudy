package Subscribe.simple;

public interface Subject {

    /**
     * 添加观察者
     * @param observer
     */
    void addObserver(Observer observer);

    /**
     * 移除观察者
     * @param observer
     */
    void deleteObserver(Observer observer);

    /**
     * 当主题方法改变时，调用此方法通知所有观察者
     */
    void notifyObserver();
}
