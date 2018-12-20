package com.goyo.project.core;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
/**
 * @author: JinYue
 * @ClassName: BaseRepository 
 * @Description: 通用仓库接口
 */
public interface BaseRepository<T,ID extends Serializable>{
    /**
     * 保存数据对象
     * @param entity
     * @return
     */
    T save(T entity);
    /**
     * 根据id查询
     * @param id
     * @param t
     * @return
     */
    T findById(T t,Object id);
    /**
     * 根据表名，字段，参数查询，拼接sql语句
     * @param  tablename 表名
     * @param filed 字段名
     * @param o 字段参数
     * @return
     */
    List<T> findBysql(String tablename,String filed,Object o);
    Object findObjiectBysql(String tablename,String filed,Object o);

    /**
     * 多个字段的查询
     * @param tablename 表名
     * @param map 将你的字段传入map中
     * @return
     */
    List<T> findByMoreFiled(String tablename,LinkedHashMap<String,Object> map);

    /**
     * 多字段查询分页
     * @param tablename 表名
     * @param map 以map存储key,value
     * @param start 第几页
     * @param pageNumer 一个页面的条数
     * @return
     */
    List<T> findByMoreFiledpages(String tablename, LinkedHashMap<String,Object> map, int start, int pageNumer);
    /**
     * 一个字段的分页
     * @param  tablename 表名
     * @param filed 字段名
     * @param o 字段参数
     * @param start 第几页
     * @param pageNumer 一个页面多少条数据
     * @return
     */
    List<T> findpages(String tablename,String filed,Object o,int start,int pageNumer);
    /**
     * 根据表的id删除数据
     * @param  entity
     */
    boolean delete(T entity);
    /**
     * 更新对象
     * @param e
     * @return
     */
    boolean update(T e);
    /**
     * 根据传入的map遍历key,value拼接字符串，以id为条件更新
     * @param tablename 表名
     * @param map 传入参数放入map中
     * @return
     */
    Integer updateMoreFiled(String tablename,LinkedHashMap<String,Object> map);


    /**
     * 根据条件查询总条数返回object类型
     * @param tablename  表名
     * @param map 传入参数放入map中
     * @return
     */
    Object findCount(String tablename, LinkedHashMap<String,Object> map);
    
    //DML
    public boolean excuteSql(String sql);
    
    //源码中copy过来的分页接口
    //****************************************************************************************
    //由于自己根据源码实现的分页，现在存在，加入具体的过滤条件后不好使，先不用，改为用传统jpa，一个表一个仓库（有时间再回来弄）
    
    public List<T> findAll(Class<T> clazz);
    public Page<T> findAll(Pageable pageable,Class<T> clazz);
    public Page<T> findAll(@Nullable Specification<T> spec, Pageable pageable,Class<T> clazz);
    
    
}