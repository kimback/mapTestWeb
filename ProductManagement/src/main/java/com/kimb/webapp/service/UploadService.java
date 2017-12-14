package com.kimb.webapp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.kimb.webapp.dao.UploadDao;


@Service
public class UploadService implements IUploadService {
    
	private UploadDao uploadDao;
	
	public void setUploadDao(UploadDao uploadDao) {
		this.uploadDao = uploadDao;
	}

	@Override
	public void addExcel(List<HashMap<String, String>> list) {
		uploadDao.addExcel(list);
		
	}
	
	@Override
	public void addProductItem(Map<String, String> paraMap) {
		
		uploadDao.addProductItem(paraMap);
	}
	
	
	@Override
	public void delProductItem(Map<String, String> paraMap) {
		uploadDao.delProductItem(paraMap);
		
	}

}
