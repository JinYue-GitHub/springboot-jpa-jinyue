package com.goyo.project.core;

import java.lang.reflect.Field;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.goyo.project.service.DataInitService;

/**
 * @author: JinYue
 * @ClassName: DataInit 
 * @Description: 数据初始化
 */
@Component
public class DataInit implements InitializingBean {
	
	@Resource
	private DataInitService datainitservice;

    @Override
    public void afterPropertiesSet() throws Exception {
    	initTableComment();
    }
    /**
     * 初始化表字段注释
     * @throws ClassNotFoundException 
     */
    private void initTableComment() throws ClassNotFoundException {
        // 获取特定包下所有的类
    	List<Class<?>> clsList = ClassUtil.getClasses("com.goyo.project.model");
        if (CollectionUtils.isNotEmpty(clsList)) {
            for (Class<?> cls : clsList) {
                // 选出包含注解@Entity和@Table的类
                if (cls.isAnnotationPresent(Entity.class) && cls.isAnnotationPresent(Table.class)) {
                    Table table = cls.getAnnotation(Table.class);
                    Field[] fields = cls.getDeclaredFields();
                    if (ArrayUtils.isNotEmpty(fields)) {
                        for (Field field : fields) {
                            // 选出包含注解@Comment和@Column的类
                            if (field.isAnnotationPresent(Comment.class) && field.isAnnotationPresent(Column.class)) {
                                Comment comment = field.getAnnotation(Comment.class);
                                Column column = field.getAnnotation(Column.class);
                                String sql = "comment on column " + table.name() + "." + column.name() + " is '" + comment.value() + "'";
                                datainitservice.executeUpdate(sql);
                            }
                        }
                    }
                }
            }
        }
    }
}