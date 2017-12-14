package com.kimb.webapp.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
public class UploadImple implements UploadDao {

	private SqlSession sqlSession;
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public void addExcel(List<HashMap<String, String>> list) {
		for(HashMap<String, String> map : list){
			sqlSession.insert("com.kimb.webapp.itemdao.insertList", map);
		}
		
	}
	
	@Override
	public void addProductItem(Map<String, String> paraMap) {
		sqlSession.insert("com.kimb.webapp.itemdao.insertList", paraMap);
		
	}
	
	@Override
	public void delProductItem(Map<String, String> paraMap) {
		sqlSession.delete("com.kimb.webapp.itemdao.deleteList", paraMap);
		
	}
}
