package Subscribe.simple;

import java.util.ArrayList;
import java.util.List;

public class TeacherSubject implements Subject {
    /**
     * 用于存放和记录观察者
     */
    private List<Observer> observers = new ArrayList<>();
    /**
     * 记录状态的字符串
     */
    private String info;

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void deleteObserver(Observer observer) {
        int i = observers.indexOf(observer);
        if (i >= 0) {
            observers.remove(observer);
        }
    }

    @Override
    public void notifyObserver() {
        for (int i = 0;i < observers.size(); i++) {
            Observer observer = observers.get(i);
            observer.update(info);
        }
    }

    /**
     * 布置作业
     * 调用notifyObserver()方法通知所有观察者
     */
    public void setHomework(String info) {
        this.info = info;
        System.out.println("今天的作业是:" + info);
        this.notifyObserver();
    }
}
