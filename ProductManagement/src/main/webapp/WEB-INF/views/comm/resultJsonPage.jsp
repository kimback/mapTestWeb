<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	String resultJson = (String) request.getAttribute("resultJson");
	if(resultJson != null){
		out.write(resultJson);
	}
	
%>