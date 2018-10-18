package Subscribe.simple;

public class StudentObserver implements Observer {
    /**
     * 保存一个Subject的引用
     * @param info
     */
    private TeacherSubject teacherSubject;

    private String studentName;

    public StudentObserver(TeacherSubject teacherSubject, String studentName) {
        this.teacherSubject = teacherSubject;
        this.studentName = studentName;
        teacherSubject.addObserver(this);
    }

    @Override
    public void update(String info) {
        System.out.println(studentName + "得到作业:" + info);
    }
}
