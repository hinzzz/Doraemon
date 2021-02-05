package com.hinz.mybatis.service;

import com.hinz.mybatis.mapper.MyBatisBaseDao;

import java.io.Serializable;

/**
 * @author ：quanhz
 * @date ：Created in 2021/2/5 11:33
 * @Description : No Description
 */
public interface BaserService<Model,PK,Dao extends MyBatisBaseDao> {

    int deleteByPrimaryKey(PK id);

    int insert(Model record);

    int insertSelective(Model record);

    Model selectByPrimaryKey(PK id);

    int updateByPrimaryKeySelective(Model record);

    int updateByPrimaryKey(Model record);
}
