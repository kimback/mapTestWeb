package com.kimb.webapp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

public class SnowGoGoService {
	
	private SqlSession sqlSession;
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	/**
	 * 토탈순위
	 * getSearchList
	 * @return
	 */
	public List<HashMap<String, Object>> getSearchList(){
		
		List<HashMap<String, Object>> list = sqlSession.selectList("com.kimb.webapp.itemdao.getSearchList");
		return (List<HashMap<String, Object>>) list;
		
	}

	/**
	 * 개인통계데이터 1 차트 데이터
	 * getSearchList
	 * @param paraMap
	 * @return
	 */
	public List<List<HashMap<String,Object>>> getSearchChartList(HashMap<String, String> paraMap) {
		
		
		List<List<HashMap<String,Object>>> mastList = new ArrayList<List<HashMap<String,Object>>>();
		
		//포인트가 저장된 스키장 리스트
		List<HashMap<String, Object>> list1 = sqlSession.selectList("com.kimb.webapp.itemdao.getSkiList", paraMap);
		
		String resultStr = "";
		
		for(int i=0; i<list1.size(); i++) {
			String dynamicStr = "CASE WHEN d.skiResortName = #{AAA} THEN d.cnt ELSE '' END as #{BBB}";
			HashMap<String, Object> tempMap = list1.get(i);
			
			dynamicStr = dynamicStr.replace("#{AAA}", "'" + tempMap.get("skiResortName").toString() + "'");
			dynamicStr = dynamicStr.replace("#{BBB}", tempMap.get("skiResortCode").toString());
			if(i+1 != list1.size()) {
				dynamicStr += ", ";
			}
			resultStr += dynamicStr;
			
		}
		
		Map<String, Object> multiParam = new HashMap<String, Object>();
		multiParam.put("userId", paraMap.get("userId"));
		multiParam.put("dynamicColumns", resultStr);
		
		List<HashMap<String, Object>> list2 = sqlSession.selectList("com.kimb.webapp.itemdao.getSearchChartListByParam", multiParam);
		mastList.add(list1);
		mastList.add(list2);
		
		return mastList;
	}
	
	/**
	 * 개인통계데이터 1 그리드 데이터
	 * getSearchList
	 * @param paraMap
	 * @return
	 */
	public List<HashMap<String, Object>> getSearchList(HashMap<String, String> paraMap) {
		List<HashMap<String, Object>> list = sqlSession.selectList("com.kimb.webapp.itemdao.getSearchListByParam", paraMap);
		return (List<HashMap<String, Object>>) list;
	}
	
	/**
	 * 개인통계데이터 2 차트 데이터
	 * getSearchList
	 * @param paraMap
	 * @return
	 */
	public List<HashMap<String, Object>> getDayNameChartData(HashMap<String, String> paraMap) {
		List<HashMap<String, Object>> list = sqlSession.selectList("com.kimb.webapp.itemdao.getDayNameChartData", paraMap);
		return (List<HashMap<String, Object>>) list;
	}
	
	
	/**
	 * 개인통계데이터 2 그리드 데이터
	 * getSearchList
	 * @param paraMap
	 * @return
	 */
	public List<HashMap<String, Object>> getGridData2(HashMap<String, String> paraMap) {
		List<HashMap<String, Object>> list = sqlSession.selectList("com.kimb.webapp.itemdao.getGridData2", paraMap);
		return (List<HashMap<String, Object>>) list;
	}
	
	
	/**
	 * 그룹별 순위
	 * getSearchGroupRankList
	 * 
	 * @return
	 */
	public List<HashMap<String, Object>> getSearchGroupRankList(){
		
		List<HashMap<String, Object>> list = sqlSession.selectList("com.kimb.webapp.itemdao.getSearchGroupRankList");
		return (List<HashMap<String, Object>>) list;
		
	}

	/**
	 * 로그인 처리 관련
	 * 유저데이터 확인
	 * getUserData
	 * @param paraMap
	 * @return
	 */
	public List<HashMap<String, Object>> getUserData(HashMap<String, String> paraMap) {
		List<HashMap<String, Object>> list = sqlSession.selectList("com.kimb.webapp.itemdao.getUserData", paraMap);
		return (List<HashMap<String, Object>>) list;
	}
	
	
	/**
	 * 로그인 처리 관련
	 * 로그인 처리
	 * getLoginData
	 * @param paraMap
	 * @return
	 */
	public List<HashMap<String, Object>> getLoginData(HashMap<String, String> paraMap) {
		List<HashMap<String, Object>> list = sqlSession.selectList("com.kimb.webapp.itemdao.getLoginData", paraMap);
		return (List<HashMap<String, Object>>) list;
	}
	
	
	
	/**
	 * userJoinService
	 * @param paraMap
	 * @return
	 */
	public int userJoinService(HashMap<String, String> paraMap) {
		int result = 0;
		try {
			result = sqlSession.insert("com.kimb.webapp.itemdao.userJoinService", paraMap);
		}catch(Exception e) {
			result = 0;
		}
		
		return result;
	}
	
	
	
	/**
	 * getSkiResortList
	 * @param paraMap
	 * @return
	 */
	public List<HashMap<String, Object>> getSkiResortList(HashMap<String, String> paraMap) {
		List<HashMap<String, Object>> list = sqlSession.selectList("com.kimb.webapp.itemdao.getSkiResortList", paraMap);
		return (List<HashMap<String, Object>>) list;
	}
	

	/**
	 * getGeoDataList
	 * @param paraMap
	 * @return
	 */
	public List<HashMap<String, Object>> getGeoDataList(HashMap<String, String> paraMap) {
		List<HashMap<String, Object>> list = sqlSession.selectList("com.kimb.webapp.itemdao.getGeoDataList", paraMap);
		return (List<HashMap<String, Object>>) list;
	}
	
	

	/**
	 * getGeoDataList
	 * @param paraMap
	 * @return
	 */
	public int geoDataUpdateByTrigger(HashMap<String, String> paraMap) {
		int result = sqlSession.insert("com.kimb.webapp.itemdao.geoDataUpdateByTrigger", paraMap);
		return result;
	}
	
	
}
