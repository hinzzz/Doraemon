package com.hinz.jsr303.bean;

import lombok.Getter;

@Getter
public enum ChangeColumnTypeEnum {
    TEXT("1", "文本"),
    IMAGE("2", "图片");
    private String type;
    private String typeName;

    ChangeColumnTypeEnum(String type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }
}
