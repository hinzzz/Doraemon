package com.hinz.bean;

import lombok.Data;

@Data
public class QueryCornerResDto {
    private Integer type;
    private Integer typeCount;
    private String typeName;

    public QueryCornerResDto(Integer type, Integer typeCount, String typeName) {
        this.type = type;
        this.typeCount = typeCount;
        this.typeName = typeName;
    }
}
