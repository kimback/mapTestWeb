package com.kimb.webapp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kimb.webapp.service.SnowGoGoService;
/**
 * Handles requests for the application home page.
 */
@Controller
public class SnowGoGoController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	private SnowGoGoService snowGoGoService;
	
	public void setSnowGoGoService(SnowGoGoService snowGoGoService) {
		this.snowGoGoService = snowGoGoService;
	}
	
	/**
	 * 메인 대쉬보드 로드 (상위 100위 + 그룹별 순위)
	 */
	@RequestMapping(value = "/snowGoGoHello")
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		String jsonStr = "";
		List<HashMap<String, Object>> list = snowGoGoService.getSearchList(); //전체
		List<HashMap<String, Object>> list2 = snowGoGoService.getSearchGroupRankList(); //그룹별
		
		if(list != null) {
			jsonStr = JSONArray.toJSONString(list);
			model.addAttribute("searchList", jsonStr );
		}
		if(list2 != null) {
			jsonStr = JSONArray.toJSONString(list2);
			model.addAttribute("searchGroupRankList", jsonStr );
		}
		
		return "dashboard";
	}
	
	
	/**
	 * 유저 정보 통계치 화면로드
	 */
	@RequestMapping(value = "/snowGoGoUserData")
	public String myData(Locale locale, Model model, HttpServletRequest request) {
		logger.info("Welcome home! The client locale is {}.", locale);
		HashMap<String, String> paraMap = new HashMap<String, String>();
		String userId = request.getParameter("userId");
		paraMap.put("userId", userId);
		
		String jsonStr = "";
		
		List<List<HashMap<String, Object>>> masterList = new ArrayList<List<HashMap<String,Object>>>();
		
		//개인데이터1 - 그리드 데이터
		List<HashMap<String, Object>> list1 = snowGoGoService.getSearchList(paraMap);
		//개인데이터1 - 차트 데이터
		List<HashMap<String, Object>> list2 = snowGoGoService.getSearchChartList(paraMap);
		
		masterList.add(list1);
		masterList.add(list2);
		
		
		if(masterList != null) {
			jsonStr = JSONArray.toJSONString(masterList);
		}

		model.addAttribute("searchList", jsonStr );
		
		return "userdataboard";
	}

	
	
}
