package SqlUtil;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <desc>
 *      数据字段注解
 * </desc>
 *
 * @createDate 2017/09/14
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ItlField {
    String value() default "";                  //列名
    String logicKey() default "";               //逻辑主键 key
    String notNull() default "";                //不能为空 true false
}
