package SqlUtil;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <desc>
 *      数据表名注解
 * </desc>
 *
 * @createDate 2017/09/14
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ItlTables {
    String value() default "";                  //表名
}
