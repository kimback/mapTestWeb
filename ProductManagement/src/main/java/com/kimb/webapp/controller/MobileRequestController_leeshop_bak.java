package com.kimb.webapp.controller;

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

import com.kimb.webapp.service.IUploadService;
import com.kimb.webapp.service.ProductService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class MobileRequestController_leeshop_bak{
	
	private static final Logger logger = LoggerFactory.getLogger(MobileRequestController.class);
	private ProductService productService;
	
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	
	private IUploadService iUploadService;
	
	public void setIUploadService(IUploadService iUploadService) {
		this.iUploadService = iUploadService;
	}
	

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/mobileRest/select")
	public String home(Locale locale, Model model, HttpServletRequest request) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		String barCode = request.getParameter("barCode");
		HashMap<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("barCode", barCode);
		
		String jsonStr = "";
		List<HashMap<String, Object>> list = productService.getProductList(paraMap);
		if(list != null) {
			jsonStr = JSONArray.toJSONString(list);
			
		}
		model.addAttribute("resultJson", jsonStr );
		
		return "/comm/resultJsonPage";
	}

	
	
}
