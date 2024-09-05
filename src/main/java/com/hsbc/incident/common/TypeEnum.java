package com.hsbc.incident.common;

public enum TypeEnum {
    SERVER(1, "Server"), DATABASE(2, "Database"), NETWORK(3, "Network"), STORAGE(4, "Storage");

    TypeEnum(int code, String text) {
        this.code = code;
        this.text = text;
    }
    private Integer code;
    private String text;

    public static String getTextByCode(Integer code) {
        for (TypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type.getText();
            }
        }
        return "Undefined";  // Return "Undefined" for unknown code
    }

    public static int getCodeByText(String text) {
        for (TypeEnum type : TypeEnum.values()) {
            if (type.getText().equalsIgnoreCase(text)) {
                return type.getCode();
            }
        }
        return 0;  // Return 0 for undefined text
    }

    public String getText() {
        return text;
    }

    public Integer getCode() {
        return code;
    }
}
