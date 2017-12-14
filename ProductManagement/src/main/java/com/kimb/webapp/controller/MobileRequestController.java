package com.kimb.webapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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
public class MobileRequestController {
	
	private static final Logger logger = LoggerFactory.getLogger(MobileRequestController.class);
	private SnowGoGoService snowGoGoService;
	
	public SnowGoGoService getSnowGoGoService() {
		return snowGoGoService;
	}

	public void setSnowGoGoService(SnowGoGoService snowGoGoService) {
		this.snowGoGoService = snowGoGoService;
	}


	@RequestMapping(value = "/snowGoGoRESTApi/myLogin")
	public String myLogin(Locale locale, Model model, HttpServletRequest request) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		String userId = request.getParameter("uid");
		String userPw = request.getParameter("upw");
		
		HashMap<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("userId", userId);
		paraMap.put("userPw", userPw);
		
		String jsonStr = "";
		
		//1. 유저데이터 확인
		List<HashMap<String, Object>> userList = snowGoGoService.getUserData(paraMap);
		if(userList != null && userList.size() > 0) {
			
			Map<String, Object> map = userList.get(0);
			String existUser = map.get("loginAuth").toString();
			
			//유저가 있으면 
			if(existUser != null && existUser.equals("T")) {
				
				//2. 로그인처리 LT / LF로 return
				List<HashMap<String, Object>> userList2 = snowGoGoService.getLoginData(paraMap);
				if(userList2 != null) {
					jsonStr = JSONArray.toJSONString(userList2);
				}
				model.addAttribute("resultJson", jsonStr );
				
			
			//유저가 없으면 F return
			}else if(existUser != null && existUser.equals("F")) {
				
				if(userList != null) {
					jsonStr = JSONArray.toJSONString(userList);
				}
				model.addAttribute("resultJson", jsonStr );
				
			}
		}
			
		return "/comm/resultJsonPage";
	}
	
	
	//가입처리관련
	@RequestMapping(value = "/snowGoGoRESTApi/userJoinService")
	public String userJoinService(Locale locale, Model model, HttpServletRequest request) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		String userId = request.getParameter("uid");
		String userPw = request.getParameter("upw");
		
		HashMap<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("userId", userId);
		paraMap.put("userPw", userPw);
		
		String jsonStr = "";
		int result = snowGoGoService.userJoinService(paraMap);
		jsonStr = "[{result:" + result + "}]";
			
		model.addAttribute("resultJson", jsonStr );
		
		return "/comm/resultJsonPage";
	}
	
	
	@RequestMapping(value = "/snowGoGoRESTApi/searchSkiResortData")
	public String searchSkiResortData(Locale locale, Model model, HttpServletRequest request) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		String userId = request.getParameter("loginId");
		
		HashMap<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("userId", userId);
		
		String jsonStr = "";
		List<HashMap<String, Object>> userList = snowGoGoService.getSkiResortList(paraMap);
		if(userList != null) {
			jsonStr = JSONArray.toJSONString(userList);
		}
		model.addAttribute("resultJson", jsonStr );
		
		return "/comm/resultJsonPage";
	}


	@RequestMapping(value = "/snowGoGoRESTApi/geoDataList")
	public String geoDataList(Locale locale, Model model, HttpServletRequest request) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		String selectedResort = request.getParameter("selectedResort");
		
		HashMap<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("selectedResort", selectedResort);
		
		String jsonStr = "";
		List<HashMap<String, Object>> userList = snowGoGoService.getGeoDataList(paraMap);
		if(userList != null) {
			jsonStr = JSONArray.toJSONString(userList);
		}
		model.addAttribute("resultJson", jsonStr );
		
		return "/comm/resultJsonPage";
	}
	
	
	@RequestMapping(value = "/snowGoGoRESTApi/geoDataUpdateByTrigger")
	public String geoDataUpdateByTrigger(Locale locale, Model model, HttpServletRequest request) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		String selectedResort = request.getParameter("skiResort");
		String userId = request.getParameter("userId");
		String blockCode = request.getParameter("blockCodes");
		
		HashMap<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("selectedResort", selectedResort);
		paraMap.put("userId", userId);
		
		JSONParser jsonParser = new JSONParser();
		try {
			JSONArray jsonArray = (JSONArray) jsonParser.parse(blockCode);
			paraMap.put("blockCode", jsonArray.get(0).toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String jsonStr = "";
		int result = snowGoGoService.geoDataUpdateByTrigger(paraMap);
		jsonStr = "[{result:" + result + "}]";
		
		model.addAttribute("resultJson", jsonStr );
		
		return "/comm/resultJsonPage";
	}

	
	
}
