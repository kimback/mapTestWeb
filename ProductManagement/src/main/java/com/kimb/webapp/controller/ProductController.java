package com.kimb.webapp.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kimb.webapp.service.IUploadService;
import com.kimb.webapp.service.ProductService;
import com.kimb.webapp.util.Excel;

/**
 * Handles requests for the application home page.
 */
@Controller
public class ProductController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
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
	@RequestMapping(value = "/hello")
	public String home(Locale locale, Model model, Excel excel) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		String jsonStr = "";
		List<HashMap<String, Object>> list = productService.getProductList();
		if(list != null) {
			jsonStr = JSONArray.toJSONString(list);
			
		}
		model.addAttribute("excel", excel);
		model.addAttribute("productList", jsonStr );
		
		return "hello";
	}

	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/insertItems")
	public String insertItems(Locale locale, Model model, HttpServletRequest request) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		//단일 인서트 
		
		String productCode = (String)request.getParameter("productCode");
		String productName = (String)request.getParameter("productName");
		String brandCode = (String)request.getParameter("brandCode");
		String typeCode = (String)request.getParameter("typeCode");
		String factoryPrice = (String)request.getParameter("factoryPrice");
		String customerPrice = (String)request.getParameter("customerPrice");
		
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("productCode", productCode);
		paraMap.put("productName", productName);
		paraMap.put("brandCode", brandCode);
		paraMap.put("typeCode", typeCode);
		paraMap.put("factoryPrice", factoryPrice);
		paraMap.put("customerPrice", customerPrice);
		
		//insert
		iUploadService.addProductItem(paraMap);
		
		JSONArray jsonArray = new JSONArray();
		
		//select
		/*
		String jsonStr = "";
		List<HashMap<String, Object>> list = productService.getProductList();
		if(list != null) {
			jsonArray.add(list);
			
		}
		*/
		
		HashMap<String, String> statusMap = new HashMap<String, String>();
		statusMap.put("actionStatus", "1");
		jsonArray.add(statusMap);
		
		
		model.addAttribute("resultJson",  jsonArray.toString());
		
		return "/comm/resultJsonPage";
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteItems")
	public String deleteItems(Locale locale, Model model, HttpServletRequest request) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		//단일 삭제
		String productSeq = (String)request.getParameter("seq");
		String productCode = (String)request.getParameter("productCode");
		String productName = (String)request.getParameter("productName");
		String brandCode = (String)request.getParameter("brandCode");
		String typeCode = (String)request.getParameter("typeCode");
		String factoryPrice = (String)request.getParameter("factoryPrice");
		String customerPrice = (String)request.getParameter("customerPrice");
		
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("productSeq", productSeq);
		paraMap.put("productCode", productCode);
		paraMap.put("productName", productName);
		paraMap.put("brandCode", brandCode);
		paraMap.put("typeCode", typeCode);
		paraMap.put("factoryPrice", factoryPrice);
		paraMap.put("customerPrice", customerPrice);
		
		//delete
		iUploadService.delProductItem(paraMap);
		
		JSONArray jsonArray = new JSONArray();
		
		//select
		/*
		String jsonStr = "";
		List<HashMap<String, Object>> list = productService.getProductList();
		if(list != null) {
			jsonArray.add(list);
			
		}
		*/
		HashMap<String, String> statusMap = new HashMap<String, String>();
		statusMap.put("actionStatus", "1");
		jsonArray.add(statusMap);
		
		model.addAttribute("resultJson",  jsonArray.toString());
		
		return "/comm/resultJsonPage";
	}
	
	
	
	 
	 
	@SuppressWarnings("resource")
	@RequestMapping(value = "/excelUpload")
	public String processForm(@ModelAttribute("excel") Excel excel, BindingResult result) throws IOException{
		List<HashMap<String, String>> paramList = new ArrayList<HashMap<String, String>>();
		
		if(!result.hasErrors()){
				FileOutputStream outputStream = null;
				
				//save & load location
				String filePath = System.getProperty("java.io.tmpdir") + excel.getFile().getOriginalFilename();
				System.out.println(filePath);
				
				//save
				outputStream = new FileOutputStream(new File(filePath));
				outputStream.write(excel.getFile().getFileItem().get()); 
					
				//load
		        FileInputStream file = new FileInputStream(new File(filePath));
	
		        XSSFWorkbook workbook = new XSSFWorkbook(file);
		        XSSFSheet sheet = workbook.getSheetAt(0);
		            
		        Iterator<Row> rowIterator = sheet.iterator();
		        List<String> keyList = new ArrayList<String>();
		        while (rowIterator.hasNext()) {
			        	Row row = rowIterator.next();
			        	System.out.println(row.getRowNum());
			        	
			        	//header no insert
		            if(row.getRowNum()==0){
		            		System.out.println("====Excel to DB Insert====");
		            		for(int i=0 ; i<row.getLastCellNum(); i++) {
		            			keyList.add(row.getCell(i).toString());
		            		}
		            		
	                }else{
	                	 	HashMap<String,String> map = new HashMap<String,String>();
	                	 	for(int i=0 ; i<row.getLastCellNum(); i++) {
	                	 		map.put(keyList.get(i), row.getCell(i).toString());
		            		}
	                	 	
	                	 	paramList.add(map);
	                }
	            }
	            file.close();
	            
		        iUploadService.addExcel(paramList);
	            
		}
		
		return "hello";
	}	
	
	
}
