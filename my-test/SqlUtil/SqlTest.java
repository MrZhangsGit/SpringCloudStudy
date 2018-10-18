package SqlUtil;

import com.alibaba.fastjson.JSON;

import java.util.Date;

public class SqlTest {
    public static void main(String[] args) {
        try {
            BasePO basePO = new BasePO(1, 1, new Date(), new Date());
            System.out.println(JSON.toJSON(SqlUtil.durableData(basePO, "SAVE")));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
