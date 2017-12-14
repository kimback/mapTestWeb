<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  

<c:set var="searchList" value="${searchList}"/>
<% 
// [유저] 개인 데이터 현황
 
 %>
<!DOCTYPE html>
<html lang="ko">
  <head>
    <title> 눈밥 유저 데이터 </title>
   <jsp:directive.include file = "comm/commHead.jsp" />

 	<script type="text/javascript"> 
 		var jsonObject = "";
 		var data1Chart = "";
 		var data1Grid = "";
 		
	    $(document).ready(function() {
			//데이터
		    jsonObject = ${searchList};
		    
		    data1Grid = jsonObject[0];
		    data1Chart = jsonObject[1];
		    
		    
		    //개인데이터 초기화
		    initMydata1Grid();
		    initMydata2Grid();
		    initInGroupGrid();
		    
		    $('#dataSearch').click(function(){
		    		alert('조회');
		    	
		    });
	 
	    }); //ready end

	    
	  	
	  	//그룹안에서 랭킹 그리드 초기화
	    function initInGroupGrid(){
	    		$("#inGroup_jsGrid").jsGrid({
	            width: "95%",
	           	height: "300px",
	            editing: true,
	            sorting: true,
	            paging: false,
	            autoload: true,
	            
	            rowClick: function(args) {
	            	 	//showDetailsDialog("Edit", args.item);
	            },
	            
	            data: jsonObject,
	     
	            fields: [
	            	 	{ title: "스키장",  name: "skiResortName", type: "text", width: 50 },
	                { title: "리프트명", name: "blockName", type: "text", width: 50 },
	                { title: "아이디", name: "userId", type: "text", width: 50 },
	                { title: "이름", name: "userName", type: "text", width: 50 },
	                { title: "포인트", name: "cnt", type: "text", width: 50 },
	                { title: "등록일", name: "regDt", type: "text", width: 50 },
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
	  	
	  	
	  //개인 데이터1 그리드 초기화
	    function initMydata1Grid(){
	    		$("#mydata1_jsGrid").jsGrid({
	            width: "95%",
	            height: "300px",
	            editing: true,
	            sorting: true,
	            paging: false,
	            autoload: true,
	            
	            rowClick: function(args) {
	            	 	//showDetailsDialog("Edit", args.item);
	            },
	            
	            data: data1Grid,
	     
	            fields: [
	            	 	{ title: "스키장",  name: "skiResortName", type: "text", width: 50 },
	                { title: "리프트명", name: "blockName", type: "text", width: 50 },
	                { title: "횟수", name: "cnt", type: "text", width: 50 },
	                { title: "등록일", name: "regDt", type: "text", width: 50 },
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
	  
	  	//개인 데이터2 그리드 초기화
	    function initMydata2Grid(){
	    		$("#mydata2_jsGrid").jsGrid({
	            width: "95%",
	            height: "300px",
	            editing: true,
	            sorting: true,
	            paging: false,
	            autoload: true,
	            
	            rowClick: function(args) {
	            	 	//showDetailsDialog("Edit", args.item);
	            },
	            
	            data: data1Grid,
	     
	            fields: [
	            		{ title: "스키장",  name: "skiResortName", type: "text", width: 50 },
	                { title: "리프트명", name: "blockName", type: "text", width: 50 },
	                { title: "횟수", name: "cnt", type: "text", width: 50 },
	                { title: "등록일", name: "regDt", type: "text", width: 50 },
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
	    
	  //area 차트관련
      google.charts.load('current', {'packages':['corechart']});
      google.charts.setOnLoadCallback(drawChart1);

      function drawChart1() {
 
    	  	//데이터 가공 해야함
    	  
    	  	// Create the data table.
    	    /*
    	    var data = new google.visualization.DataTable();
    	  	
    	  	
    	    data.addColumn('string', 'Topping');
    	    data.addColumn('number', 'Slices');
    	    data.addRows([
    	        ['Mushrooms', 3],
    	        ['Onions', 1],
    	        ['Olives', 1],
    	        ['Zucchini', 1],
    	        ['Pepperoni', 2]
    	    ]);
    	    */
    	    
    	    
    	  	
        var data = google.visualization.arrayToDataTable([
          ['Year', 'Sales', 'Expenses'],
          ['2013',  1000,      400],
          ['2014',  1170,      460],
          ['2015',  660,       1120],
          ['2016',  1030,      540]
        ]);
    	  	

        var options = {
          title: '일자별 전투력',
          hAxis: {title: 'Year',  titleTextStyle: {color: '#333'}},
          vAxis: {minValue: 0}
        };

        var chart = new google.visualization.AreaChart(document.getElementById('mydata1_chart'));
        chart.draw(data, options);
      }
      

      //pie 차트관련
      google.charts.load('current', {'packages':['corechart']});
      google.charts.setOnLoadCallback(drawChart2);

      function drawChart2() {

        var data = google.visualization.arrayToDataTable([
          ['Task', 'Hours per Day'],
          ['Work',     11],
          ['Eat',      2],
          ['Commute',  2],
          ['Watch TV', 2],
          ['Sleep',    7]
        ]);

        var options = {
          title: '요일별 전투력',
        	  colors: ['#e0440e', '#e6693e', '#ec8f6e', '#f3b49f', '#f6c7b6']
        };

        var chart = new google.visualization.PieChart(document.getElementById('mydata2_chart'));
        chart.draw(data, options);
      }
      
      
      
	 //responsive charts width
     $(window).resize(function(){
    	 	drawChart1();
    	 	drawChart2();
    	 });
	     
	 
 	</script> 
  	
  </head>
  <body>
    <h1 style="margin-left: 30px; margin-top: 10px; text-align: center;"> 개인통계 데이터 </h1>
    
    <!-- 버튼라인-->
    <div id="buttonLine" style="text-align: right; margin-right: 3%; margin-top: 20px; margin-bottom: 10px;">
	    <button id="dataSearch" type="button" class="btn btn-info">조회</button>
	    <button id="excelDownload" type="button" class="btn btn-success">더보기</button>
    </div>
    
   
    	
    	<!-- ===개인 전투력=== -->
    <div id="title_mydata1" style="margin-left: 3%; height: 50px; margin-top: 10px;">
    		<h3>[개인 전투력]</h3>
    </div>
    
    	<!-- ===in group 전투력=== grid -->
    <div id="title_inGroup" style="margin-left: 3%; height: 50px; margin-top: 10px;">
    		<h3>[그룹내부 전투력]</h3>
    </div>
    <div id="inGroup_jsGrid" style="margin-left: 3%; margin-bottom: 5%;"></div>
    	

	<!-- chart -->
	<div id="mydata1_chart" style="width: 100%; height: 500px;"></div>
    	<!-- grid -->
    <div id="mydata1_jsGrid" style="margin-left: 3%; margin-bottom: 5%;"></div>
    	
    	<!-- chart -->
	<div id="mydata2_chart" style="width: 100%; height: 500px;"></div>
	<!-- grid -->
    <div id="mydata2_jsGrid" style="margin-left: 3%; margin-bottom: 5%;"></div>
	
    
    <!-- shareData -->
    <input id="searchList" type="hidden" value="${searchList}"/>
    
  </body>
</html>
