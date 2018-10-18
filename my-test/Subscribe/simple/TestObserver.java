package Subscribe.simple;

public class TestObserver {
    public static void main(String[] args) {
        TeacherSubject teacher = new TeacherSubject();
        StudentObserver zhang1 = new StudentObserver(teacher, "zhang1");
        StudentObserver zhang2 = new StudentObserver(teacher, "zhang2");
        StudentObserver zhang3 = new StudentObserver(teacher, "zhang3");
        StudentObserver zhang4 = new StudentObserver(teacher, "zhang4");

        teacher.setHomework("第二页第三题");
        teacher.setHomework("第一页第五题");
        teacher.setHomework("第七页第三题");
    }
}
