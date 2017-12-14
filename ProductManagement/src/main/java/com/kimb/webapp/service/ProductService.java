package com.kimb.webapp.service;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

public class ProductService {
	
	private SqlSession sqlSession;
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public List<HashMap<String, Object>> getProductList(){
		
		List<HashMap<String, Object>> list = sqlSession.selectList("com.kimb.webapp.itemdao.getProductList");
		return (List<HashMap<String, Object>>) list;
		
	}

	public List<HashMap<String, Object>> getProductList(HashMap<String, String> paraMap) {
		List<HashMap<String, Object>> list = sqlSession.selectList("com.kimb.webapp.itemdao.getProductListByParam", paraMap);
		return (List<HashMap<String, Object>>) list;
	}
	
}
