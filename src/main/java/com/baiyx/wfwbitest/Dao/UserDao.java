package com.baiyx.wfwbitest.Dao;

import com.baiyx.wfwbitest.Entity.EsUser;
import com.baiyx.wfwbitest.Entity.Projbase;
import com.baiyx.wfwbitest.Entity.User;
import com.baiyx.wfwbitest.Utils.RowConvertColUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: baiyx
 * @Date: 2021/6/30 上午 11:28
 * @Description: DAO层
 */
@Mapper
@CacheConfig(cacheNames = "UserDao")
public interface UserDao {

    // 查
    // @Cacheable(cacheNames = "findAll",cacheManager = "cacheManager")
    @Cacheable
    @DS("slave")
    List<User> findAll();

    List<User> findAll2();

    List<EsUser> findAll3();

    // 增
    @DS("slave")
    void insertOne(User user);

    // @Options(useGeneratedKeys=true, keyProperty="id") // 注解的方式获取插入信息后的自增ID
    void insertOne3(User user);

    // 删
    void deleteByName(String username);

    // 改
    void updateOne(User user);

    // 根据id查
    @Cacheable
    User findById(int id);

    // 根据id查
    List<EsUser> findById2(Long id);

    // 根据name查
    @Cacheable
    User findByName(String username);

    // 根据时间区间查询
    // @Cacheable(value = "findByTime",cacheManager = "expire1min")
    @Cacheable(cacheManager = "expire1min")
    List<User> findByTime(Date startTime,Date endTime);

    // 行转列
    RowConvertColUtil RowConvertCol(Date startTime, Date endTime);

    // 批量插入
    void insertAll(List<User> userList);

    // 消除转义
    void removeESC(List<Projbase> projbase);

    // 批量查询
    // xml中的sql定义的resultType="string",这里方法的返回值类型为ArrayList,导致红色下划波浪线
    ArrayList findByprojbase(ArrayList projbase);
}
