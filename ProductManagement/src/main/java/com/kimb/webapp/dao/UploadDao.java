package com.kimb.webapp.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface UploadDao {
	void addExcel(List<HashMap<String, String>> list);

	void addProductItem(Map<String, String> paraMap);

	void delProductItem(Map<String, String> paraMap);
}
