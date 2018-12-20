package com.goyo.project.service;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface DataInitService {
    /**通过username查找用户信息;*/
	public void executeUpdate(String sql);
}