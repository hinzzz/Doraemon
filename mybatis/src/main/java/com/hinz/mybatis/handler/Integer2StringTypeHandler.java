package com.hinz.mybatis.handler;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Integer2StringTypeHandler implements TypeHandler<List<String>> {

    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, List<String> arr, JdbcType jdbcType) throws SQLException {
        System.out.println("009");
        StringBuffer sb = new StringBuffer();
        for (Object o : arr) {
            sb.append(o).append(",");
        }
        preparedStatement.setString(i, sb.toString().substring(0, sb.toString().length() - 1));
    }

    @Override
    public List<String> getResult(ResultSet resultSet, String s) throws SQLException {
        System.out.println("0001");
        String[] arr = resultSet.getString(s).split(",");
        return Arrays.stream(arr).collect(Collectors.toList());
    }

    @Override
    public List<String> getResult(ResultSet resultSet, int i) throws SQLException {
        System.out.println("0002");
        String[] arr = resultSet.getString(i).split(",");
        return Arrays.stream(arr).collect(Collectors.toList());
    }

    @Override
    public List<String> getResult(CallableStatement callableStatement, int i) throws SQLException {
        System.out.println("0003");
        String[] arr = callableStatement.getString(i).split(",");
        return Arrays.stream(arr).collect(Collectors.toList());
    }
}
