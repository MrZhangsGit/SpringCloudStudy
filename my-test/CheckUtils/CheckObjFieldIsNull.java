package CheckUtils;

import lombok.Data;

import java.lang.reflect.Field;

/**
 * @author zhangs
 * @Description 判断类中对象是否为空
 * @createDate 2018/12/24
 */
public class CheckObjFieldIsNull {

    public static boolean checkObjFieldIsNull(Object obj){
        try {
            for (Field field:obj.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (field.get(obj) != null || !"".equals(field.get(obj))) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public static void main(String[] args) {
        User user = new User();
        System.out.println(checkObjFieldIsNull(user));
    }
}

@Data
class User{
    private String id;
    private String name;
}
