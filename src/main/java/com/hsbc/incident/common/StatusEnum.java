package com.hsbc.incident.common;

public enum StatusEnum {
    OPEN(1, "Open"), IN_PROGRESS(2, "In Progress"), RESOLVED(3, "Resolved");

    private Integer code;

    private String text;

    StatusEnum(int code, String text) {
        this.code = code;
        this.text = text;
    }

    public static String getTextByCode(Integer code) {
        for (StatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status.getText();
            }
        }
        return "Undefined";  // Return "Undefined" for unknown code
    }

    public static Integer getCodeByText(String text) {
        for (StatusEnum status : StatusEnum.values()) {
            if (status.getText().equalsIgnoreCase(text)) {
                return status.getCode();
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
