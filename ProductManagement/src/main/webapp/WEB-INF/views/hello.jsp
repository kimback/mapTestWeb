<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  
<c:set var="productList" value="${productList}"/>
 
<!DOCTYPE html>
<html lang="ko">
  <head>
    <title> 구순LEE 재고판매관리 </title>
   <jsp:directive.include file = "comm/commHead.jsp" />

 	<script type="text/javascript"> 
 		var jsonObject = "";
	    $(document).ready(function() {
			//데이터
		    jsonObject = ${productList};
		    //그리드 초기
		    initGrid ();
		    
		    $("#excelUpload").click(function(){
	    			excelUploadAction();
		    });
		    
		    $("#excelDownload").click(function(){
		    		excelDownloadAction();
		    });
		    
		    $("#filePath").change(function(e) { 
		    		//파일 선택 여부 체크
		    	});
		    	
		    
			$("#detailsDialog").dialog({
		        autoOpen: false,
		        width: 400,
		        close: function() {
		            $("#detailsForm").validate().resetForm();
		            $("#detailsForm").find(".error").removeClass("error");
		        }
		    });
	 
		    $("#detailsForm").validate({
		        rules: {
		        		productCode: "required",
		        		productName: "required"
		            
		        },
		        messages: {
		        		productCode: "Please enter productCode",
		        		productName: "Please enter productName"
		        },
		        submitHandler: function() {
		            formSubmitHandler();
		        }
		    });
		    
		    
	    }); //ready end
	    
    		var formSubmitHandler = $.noop;
 
	    var showDetailsDialog = function(dialogType, client) {
	        $("#productCode").val(client.productCode);
	        $("#productName").val(client.productName);
	        $("#brandCode").val(client.brandCode);
	        $("#typeCode").val(client.typeCode);
	        $("#factoryPrice").val(client.factoryPrice);
	        $("#customerPrice").val(client.customerPrice);
	        $("#regDate").val(client.regDate);
		    
	        formSubmitHandler = function() {
	            saveClient(client, dialogType === "Add");
	        };
	 
	        $("#detailsDialog").dialog("option", "title", dialogType + " Client")
	                .dialog("open");
	    };
 
	    var saveClient = function(client, isNew) {
	    	
	        $.extend(client, {
	            productCode: $("#productCode").val(),
	            productName: $("#productName").val(),
	            brandCode: $("#brandCode").val(),
	            typeCode: $("#typeCode").val(),
	            factoryPrice: $("#factoryPrice").val(),
	            customerPrice: $("#factoryPrice").val(),
	            regDate: $("#regDate").val(),
	        });
	 
	        $("#jsGrid").jsGrid(isNew ? "insertItem" : "updateItem", client);
	        
	        $("#detailsDialog").dialog("close");
	   	 };

	    
	    //그리드 초기화
	    function initGrid(){
	    		$("#jsGrid").jsGrid({
	            width: "94%",
	            editing: true,
	            sorting: true,
	            paging: false,
	            autoload: true,
	            
	            rowClick: function(args) {
	            	 	//showDetailsDialog("Edit", args.item);
	            },
	            
	            insertItem : function ( item ) {
	            		
		            	return $.ajax({
	                    type: "POST",
	                    url: "./insertItems",
	                    data: item,
	                    dataType: "json"
	                }).always(function (data) { 
	                	
		                	if(data[0].actionStatus != undefined && data[0].actionStatus == '1'){
	                			alert("제품이 추가 되었습니다.");
	                			
	                			//그리드리플래쉬
	                			//jsonObject = JSON.stringify(data[0]);
	                			//alert(jsonObject);
	                			
	                			//페이지 리플레쉬
	                			location.reload();
	                			
	                			
	                		}else{
	                			alert("제품 추가에 실패하였습니다.");
	                		}
	                   
	               });
		            	
	            	},
	            	//deleteConfirm: function(item){
            	 		//return "삭제하시겠습니까?";
            		//},
            
	            deleteItem : function(item){
	            		return $.ajax({
	                    type: "POST",
	                    url: "./deleteItems",
	                    data: item,
	                    dataType: "json"
	                }).always(function (data) { 
		                	if(data[0].actionStatus != undefined && data[0].actionStatus == '1'){
		                		alert("삭제가 완료 되었습니다.");
		                		//페이지 리플레쉬
	                			location.reload();
		                	}
	                });
	            },
	            	
	            /*
	            
	            	
	            	updateItem : function ( item ) {
	            		return $.ajax({
                        type: "POST",
                        url: "/updateItems",
                        data: item
                    });
	            	},
	            
	            
	            */
	            
	            data: jsonObject,
	     
	            fields: [
	            	 	{ name: "seq", type: "text",  visible: false, width: 50 },
	                { title: "제품코드",  name: "productCode", type: "text", width: 50 },
	                { title: "제품명", name: "productName", type: "text", width: 50 },
	                { title: "브랜드코드", name: "brandCode", type: "text", width: 50 },
	                { title: "의류타입", name: "typeCode", type: "text", width: 50 },
	                { title: "공장도가격", name: "factoryPrice", type: "text", width: 50 },
	                { title: "소비자가격", name: "customerPrice", type: "text", width: 50 },
	                { title: "등록일", name: "regDate", type: "text", width: 50 },
	                {
	                    type: "control",
	                    width: "30px",
	                    modeSwitchButton: false,
	                    editButton: false,
	                    headerTemplate: function() {
	                        return $("<button>").attr("type", "button").text("Add")
	                                .on("click", function () {
	                                    showDetailsDialog("Add", {});
	                                });
	                    }
	                }//type end
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
	    
	    
	    //엑셀 다운로드
	    function excelDownloadAction(){
	    		var data = $('#jsGrid').jsGrid('option', 'data');
	    		var csv = JSONToCSVConvertor(data, "Excel Report", true);
	    		//alert(csv);
	    		
	    }
	    
	    //엑셀 업로드
	    function excelUploadAction(){
		    	if( $("#filePath").val() != "" ){
		    		$("#excelForm").submit();
	    		}else{
	    			alert("파일을 선택하세요");
	    		}
		    	
	    }
	    
	    /* csv export */
	    function JSONToCSVConvertor(JSONData, ReportTitle, ShowLabel) {
	        //If JSONData is not an object then JSON.parse will parse the JSON string in an Object
	        var arrData = typeof JSONData != 'object' ? JSON.parse(JSONData) : JSONData;

	        var CSV = '';    
	        //Set Report title in first row or line

	        CSV += ReportTitle + '\r\n\n';

	        //This condition will generate the Label/Header
	        if (ShowLabel) {
	            var row = "";

	            //This loop will extract the label from 1st index of on array
	            for (var index in arrData[0]) {

	                //Now convert each value to string and comma-seprated
	                row += index + ',';
	            }

	            row = row.slice(0, -1);

	            //append Label row with line break
	            CSV += row + '\r\n';
	        }

	        //1st loop is to extract each row
	        for (var i = 0; i < arrData.length; i++) {
	            var row = "";

	            //2nd loop will extract each column and convert it in string comma-seprated
	            for (var index in arrData[i]) {
	                row += '"' + arrData[i][index] + '",';
	            }

	            row.slice(0, row.length - 1);

	            //add a line break after each row
	            CSV += row + '\r\n';
	        }

	        if (CSV == '') {        
	            alert("Invalid data");
	            return;
	        }   

	        //Generate a file name
	        var fileName = "MyReport_";
	        //this will remove the blank-spaces from the title and replace it with an underscore
	        fileName += ReportTitle.replace(/ /g,"_");   

	        //Initialize file format you want csv or xls
	        var uri = 'data:text/csv;charset=utf-8,' + encodeURIComponent(CSV);

	        // Now the little tricky part.
	        // you can use either>> window.open(uri);
	        // but this will not work in some browsers
	        // or you will not get the correct file extension    

	        //this trick will generate a temp <a /> tag
	        var link = document.createElement("a");    
	        link.href = uri;

	        //set the visibility hidden so it will not effect on your web-layout
	        link.style = "visibility:hidden";
	        link.download = fileName + ".csv";

	        //this part will append the anchor tag and remove it after automatic click
	        document.body.appendChild(link);
	        link.click();
	        document.body.removeChild(link);
	    }
	    
	    
 	</script> 
  	<style>
        .ui-widget *, .ui-widget input, .ui-widget select, .ui-widget button {
            font-family: 'Helvetica Neue Light', 'Open Sans', Helvetica;
            font-size: 14px;
            font-weight: 300 !important;
        }

        .details-form-field input,
        .details-form-field select {
            width: 250px;
            float: right;
        }

        .details-form-field {
            margin: 30px 0;
        }

        .details-form-field:first-child {
            margin-top: 10px;
        }

        .details-form-field:last-child {
            margin-bottom: 10px;
        }

        .details-form-field button {
            display: block;
            width: 100px;
            margin: 0 auto;
        }

        input.error, select.error {
            border: 1px solid #ff9999;
            background: #ffeeee;
        }

        label.error {
            float: right;
            margin-left: 100px;
            font-size: .8em;
            color: #ff6666;
        }
    </style>
  
  </head>
  <body>
    <h1 style="margin-left: 30px; margin-top: 10px;"> 상품 리스트 </h1>
    
    <div id="buttonLine" style="text-align: right; margin-right: 3%; margin-top: 20px; margin-bottom: 10px;">
	   
		
		<!-- 엑셀 버튼 -->
	    <button id="excelUpload" type="button" class="btn btn-info">엑셀업로드</button>
	    <button id="excelDownload" type="button" class="btn btn-success">엑셀다운로드</button>
	
    </div>
    		
    
    <!-- grid -->
    <div id="jsGrid" style="margin-left: 3%; margin-bottom: 5%;"></div>
    
    <!-- shareData -->
    <input id="productList" type="hidden" value="${productList}"/>
    
	
	<!-- 레이어 팝업 -->
	 <div id="detailsDialog">
        <form id="detailsForm" action="" method="POST">
            <div class="details-form-field">
                <label for="productCode">제품코드:</label>
                <input id="productCode" name="productCode" type="text" />
            </div>
            <div class="details-form-field">
                <label for="productName">제품:</label>
                <input id="productName" name="productName" type="text" />
            </div>
            <div class="details-form-field">
                <label for="brandCode">브랜드코드:</label>
                <input id="brandCode" name="brandCode" type="text" />
            </div>
            <div class="details-form-field">
               <label for="typeCode">의류타입:</label>
                <input id="typeCode" name="typeCode" type="text" />
            </div>
            <div class="details-form-field">
                <label for="factoryPrice">공장도가격:</label>
                <input id="factoryPrice" name="factoryPrice" type="text" />
            </div>
            <div class="details-form-field">
                <label for="customerPrice">소비자가격:</label>
                <input id="customerPrice" name="customerPrice" type="text" />
            </div>
            <div class="details-form-field">
                <button type="submit" id="save">저장</button>
            </div>
        </form>
    </div>
    
    <div style="float: left; margin-bottom: 20px;">
    		<form:form id="excelForm" action="./excelUpload" commandName="excel" enctype="multipart/form-data" method="POST">
			<table style="margin-left: 30px;">
				<tr>
					<td>파일을 선택하세요 : </td>
					<td><form:input id="filePath" type="file" path="file"/></td>
				</tr>
			</table>
	
		</form:form>
    
    </div>
  </body>
</html>
