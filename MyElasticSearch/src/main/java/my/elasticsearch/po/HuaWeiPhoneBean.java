package my.elasticsearch.po;

import lombok.Data;

import java.util.List;

/**
 * @author zhangs
 * @Description
 * @createDate 2020/4/1
 */
@Data
public class HuaWeiPhoneBean {
    private String productName;

    private List<ColorModeBean> colorsItemMode;

    private List<String> sellingPoints;
}
