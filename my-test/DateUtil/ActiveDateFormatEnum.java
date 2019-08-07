package DateUtil;

/**
 * 有效时间解析枚举
 * @author zhangs
 * @create 2019/6/25
 */
public enum ActiveDateFormatEnum {

    ACTIVE_DATE_FORMAT_SECOND("ss", 1, 13),
    ACTIVE_DATE_FORMAT_MINUTE("mm", 2, 12),
    ACTIVE_DATE_FORMAT_HOUR("HH", 3, 11),
    ACTIVE_DATE_FORMAT_DAY("dd", 4, 5),
    ACTIVE_DATE_FORMAT_MONTH("MM", 5, 2),
    ACTIVE_DATE_FORMAT_YEAR("yyyy", 6, 1);

    private String typeName;
    private Integer typeCode;
    /**
     * Calendar中定义的field
     */
    private int field;

    ActiveDateFormatEnum(String typeName, Integer typeCode, int field) {
        this.typeName = typeName;
        this.typeCode = typeCode;
        this.field = field;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(Integer typeCode) {
        this.typeCode = typeCode;
    }

    public int getField() {
        return field;
    }

    public void setField(int field) {
        this.field = field;
    }

    /**
     * 获取解析式
     */
    public static String getActiveDateFormat(Integer typeCode) {
        if (typeCode == null) {
            return null;
        }
        ActiveDateFormatEnum[] activeDateFormatEnums = ActiveDateFormatEnum.values();
        for (ActiveDateFormatEnum limitCategory:activeDateFormatEnums) {
            if (limitCategory.typeCode.equals(typeCode)) {
                return limitCategory.typeName;
            }
        }
        return null;
    }

    /**
     * 获取枚举
     */
    public static ActiveDateFormatEnum getEnum(Integer typeCode) {
        if (typeCode == null) {
            return null;
        }
        ActiveDateFormatEnum[] activeDateFormatEnums = ActiveDateFormatEnum.values();
        for (ActiveDateFormatEnum limitCategory:activeDateFormatEnums) {
            if (limitCategory.typeCode.equals(typeCode)) {
                return limitCategory;
            }
        }
        return null;
    }

}

