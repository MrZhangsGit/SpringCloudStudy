package SqlUtil;

import DateUtil.DateUtil;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class SqlUtil {
    /**
     * <desc>
     *      获取插入或更新的参数集
     * </desc>
     *
     * @param obj 待转换的bean
     * @return map
     * @throws Exception
     * @author zhangs
     * @createDate 2018/04/11
     */
    public static <T> Map durableData(T obj, String saveOrUpdate) throws Exception {
        Map resultMap = new HashMap<>();
        List<String> objFields = null;
        List<Object> objcolumnValue = null;
        List<Map<String, Object>> updates = null;
        try {
            //表名注解
            ItlTables itlTables = obj.getClass().getAnnotation(ItlTables.class);
            resultMap.put("table", itlTables.value());
            Field[] childs = obj.getClass().getDeclaredFields();
            Class superClass = obj.getClass().getSuperclass();
            Field[] supers = superClass.getDeclaredFields();
            //将父子类都添加进list
            List<Field> fields = new ArrayList<>();
            for (Field superField : supers) {
                fields.add(superField);
            }
            for (Field childField : childs) {
                fields.add(childField);
            }
            objFields = new ArrayList<>();
            objcolumnValue = new ArrayList<>();
            updates = new ArrayList<>();
            for (Field field : fields) {
                //列名注解
                ItlField itlField = field.getAnnotation(ItlField.class);
                //跳过非数据库绑定属性字段（如createDate、updateDate）
                if (null == itlField)
                {
                    continue;
                }
                //获取get属性方法名
                String name = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                //获取属性的类型
                String type = field.getGenericType().toString();
                if (saveOrUpdate.equals("UPDATE")) {
                    if (itlField != null && itlField.value().equals("id") && !itlField.logicKey().equals("key")) {
                        //如果是更新，跳过id
                        continue;
                    }
                    if (itlField != null && itlField.logicKey().equals("key")) {
                        //如果是逻辑主键，添加至resultMap
                        Method m = obj.getClass().getMethod("get" + name);
                        Object value = m.invoke(obj);
                        resultMap.put("logicKeyName", itlField.value());
                        resultMap.put("logicKeyValue", value);
                        continue;
                    }
                }
                //设置需要添加或修改的参数
                setSaveOrUpdateParam(objFields, objcolumnValue, updates, type, field, obj, name, saveOrUpdate);
            }
        } catch (Exception e) {
            throw new Exception("从bean转换为map时异常!", e);
        }
        if (saveOrUpdate.equals("SAVE")) {
            resultMap.put("fields", objFields);
            resultMap.put("columnValue", objcolumnValue);
        } else {
            resultMap.put("list", updates);
        }
        return resultMap;
    }

    /**
     * <desc>
     *      获取字段类型然后获取字段值拼接插入语句
     * </desc>
     *
     * @param {@link java.util.List} objFields  save的列名
     * @param {@link java.util.List} objcolumnValue save的值
     * @param {@link java.util.List} updates 更新的参数集
     * @param {@link java.lang.String}  type 反射字段类型
     * @param {@link java.lang.reflect} f   反射字段
     * @param {@link java.util.List} saveList 插入参数
     * @param {@link java.lang.Object} t  对象
     * @param {@link java.lang.String} name 对象
     * @param {@link java.lang.String} isSave 保存标识
     * @return
     * @author zhangs
     * @createDate 2018/04/11
     */
    private static void setSaveOrUpdateParam(
            List<String> objFields,
            List<Object> objcolumnValue,
            List<Map<String, Object>> updates,
            String type,
            Field f,
            Object t,
            String name,
            String isSave) {
        Map<String, Object> map = new HashMap<>();
        try {
            //更新时对可以为空字符串或null的字段进行操作
            if (isSave.equals("UPDATE")) {
                ItlField fruitName = f.getAnnotation(ItlField.class);
                if (fruitName != null && fruitName.notNull().equals("false")) {
                    Method m = t.getClass().getMethod("get" + name);
                    Object value = m.invoke(t);
                    map.put("columnName", fruitName.value());
                    map.put("columnValue", value);
                    updates.add(map);
                    return;
                }
            }
            Method m = t.getClass().getMethod("get" + name);
            //如果type是类类型，则前面包含"class "，后面跟类名
            Object value;
            if (type.equals("class java.lang.String")) {
                //调用getter方法获取属性值
                value = m.invoke(t);
                if (value != null && !value.equals("")) {
                    ItlField fruitName = f.getAnnotation(ItlField.class);
                    if (isSave.equals("SAVE")) {
                        objFields.add(fruitName.value());
                        objcolumnValue.add(value);
                    } else {
                        map.put("columnName", fruitName.value());
                        map.put("columnValue", value);
                        updates.add(map);
                    }
                    return;
                }
            }
            value = m.invoke(t);
            if (type.equals("int") || value != null) {
                ItlField fruitName = f.getAnnotation(ItlField.class);
                if (isSave.equals("SAVE")) {
                    objFields.add(fruitName.value());
                    if (type.equals("class java.util.Date")) {
                        objcolumnValue.add(DateUtil.fomatDate((Date) value));
                    } else {
                        objcolumnValue.add(value);
                    }
                } else {
                    map.put("columnName", fruitName.value());
                    if (type.equals("class java.util.Date")) {
                        map.put("columnValue", DateUtil.fomatDate((Date) value));
                    } else {
                        map.put("columnValue", value);
                    }
                    updates.add(map);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * 校验SQL注入是否通过
     * @param content 检测内容
     * @return
     * @author zhangs
     * @createDate 2019/01/04
     */
    public static boolean preventSQLInjection(String content) {
        if (StringUtils.isEmpty(content)) {
            return true;
        }
        String regular = ".*([';]+|(--)+).*";

        int beforeLength = content.length();
        int afterLength = content.replaceAll(regular, "").length();
        if (beforeLength == afterLength) {
            return true;
        }
        return false;
    }
}
