<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  

<c:set var="searchList" value="${searchList}"/>
<c:set var="searchGroupRankList" value="${searchGroupRankList}"/>

 <%
 
 // [메인] 토탈 데이터 현황
 %>
<!DOCTYPE html>
<html lang="ko">
  <head>
    <title> 눈밥스(스노우마일리지) 대쉬보드 </title>
   <jsp:directive.include file = "comm/commHead.jsp" />

 	<script type="text/javascript"> 
 		var PrivateTotalRank = "";
 		var GroupTotalRank = "";
	    
 		$(document).ready(function() {
			//데이터
		    PrivateTotalRank = ${searchList};
		    GroupTotalRank = ${searchGroupRankList};
		    
		    //전체데이터 초기화
		    initTop100Grid ();
		    initGroupGrid ();
		    
		    
		    $('#dataSearch').click(function(){
		    		alert('준비중');
		    	
		    });
	 
	    }); //ready end

	    
	  	//top100 그리드 초기화
	    function initTop100Grid(){
	    		$("#top100_jsGrid").jsGrid({
	            width: "95%",
	            height: "300px",
	            editing: true,
	            sorting: true,
	            paging: false,
	            autoload: true,
	            
	            rowClick: function(args) {
	            	 	//showDetailsDialog("Edit", args.item);
	            },
	            
	            data: PrivateTotalRank,
	     
	            fields: [
	            	 	{ title: "스키장",  name: "skiResortName", type: "text", width: 50 },
	                { title: "아이디", name: "id", type: "text", width: 50 },
	                { title: "포인트", name: "sumCnt", type: "text", width: 50 },
	                { title: "그룹명", name: "groupName", type: "text", width: 50 }
	              //fields end
	            ], 
	            
	            onDataLoaded : function ( args ) {
	            	
	            },
	            onItemInserted : function ( args ) {
	            	
	            }, // on controller.insertItem 완료 됨  
	            onItemUpdated : function ( args ) {
	            	
	            }, // on controller.updateItem의 수행 중입니다.
	            onItemDeleted : function ( args ) {
	            	
	            }, // controller.deleteItem의 수행 중입니다.  
	                
	        });
	    }
	    
	  	//그룹별 그리드 초기화
	    function initGroupGrid(){
	    		$("#group_jsGrid").jsGrid({
	            width: "95%",
	            height: "300px",
	            editing: true,
	            sorting: true,
	            paging: false,
	            autoload: true,
	            
	            rowClick: function(args) {
	            	 	//showDetailsDialog("Edit", args.item);
	            },
	            
	            data: GroupTotalRank,
	     
	            fields: [
	            		{ title: "그룹명", name: "groupName", type: "text", width: 50 },
	            	 	{ title: "스키장",  name: "skiResortName", type: "text", width: 50 },
	                { title: "포인트", name: "sumCnt", type: "text", width: 50 }
	              //fields end
	            ], 
	            
	            onDataLoaded : function ( args ) {
	            	
	            },
	            onItemInserted : function ( args ) {
	            	
	            }, // on controller.insertItem 완료 됨  
	            onItemUpdated : function ( args ) {
	            	
	            }, // on controller.updateItem의 수행 중입니다.
	            onItemDeleted : function ( args ) {
	            	
	            }, // controller.deleteItem의 수행 중입니다.  
	                
	        });
	    }
	    
	      
 	</script> 
  	
  </head>
  <body>
    <h1 style="margin-left: 30px; margin-top: 10px; text-align: center;"> 눈밥스(스노우마일리지) 전체랭킹</h1>
    
    <!-- 버튼라인-->
    <div id="buttonLine" style="text-align: right; margin-right: 3%; margin-top: 20px; margin-bottom: 10px;">
	    <!-- <input class="form-control" id="inputdefault" type="text" style="width: 100px; display:inline; margin-right:5px;"> -->
	    <button id="dataSearch" type="button" class="btn btn-info">조회</button>
	    <button id="excelDownload" type="button" class="btn btn-success">더보기</button>
    </div>
    		
    	<!-- === 전체 기준 데이터 === -->
    	<!-- ===랭킹 top 100 grid=== -->
    <div id="title_rank_top100" style="margin-left: 3%; height: 50px;">
    		<h3>[랭킹 TOP 100]</h3>
    </div>
    <div id="top100_jsGrid" style="margin-left: 3%; margin-bottom: 5%;"></div>
    
    <!-- ===그룹별 전투력=== grid -->
    <div id="title_group" style="margin-left: 3%; height: 50px; margin-top: 10px;">
    		<h3>[그룹별 전투력]</h3>
    </div>
    <div id="group_jsGrid" style="margin-left: 3%; margin-bottom: 5%;"></div>
    

    <!-- shareData -->
    <input id="searchList" type="hidden" value="${searchList}"/>
    <input id="searchGroupRankList" type="hidden" value="${searchGroupRankList}"/>
    
  </body>
</html>
