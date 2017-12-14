
var PUBLIC_JS = {};

//------------------------------------------------------
var g_XMLHttpRequest_ActiveX;

// init HttpRequest
PUBLIC_JS.initXMLHttpRequest = function(){
 var objHTTP = null;

 if(window.XMLHttpRequest){
  objHTTP = new XMLHttpRequest();
 }else{
  if(window.ActiveXObject){ //IE6이하
   if(g_XMLHttpRequest_ActiveX){
    objHTTP = new ActiveXObject(g_XMLHttpRequest_ActiveX);
   }else{
    var xmlhttp = new Array('   Msxml2.XMLHTTP.7.0',
    'Msxml2.XMLHTTP.6.0','Msxml2.XMLHTTP.5.0','Msxml2.XMLHTTP.4.0',
    'MSXML2.XMLHTTP.3.0','MSXML2.XMLHTTP','Microsoft.XMLHTTP');
    for(var i = 0; i < xmlhttp.length; i++){
     try{
      objHTTP = new ActiveXObject(xmlhttp[i]);
      if(objHTTP != null){
       g_XMLHttpRequest_ActiveX = xmlhttp[i];
       break;
      }
     }catch(e){}
    }
   }
  }
 }

 return objHTTP;
}

// JSON Type의 XMLAsync 모듈
PUBLIC_JS.XMLAsyncJSON = function(sURL, sParms, sResponseHandler, sTest){
 var sPassedParms = "";
 var sMethods = "";
 var iArgCount = PUBLIC_JS.XMLAsyncJSON.arguments.length;
 var aArgs;
 if(iArgCount > 4){
  aArgs = PUBLIC_JS.XMLAsyncJSON.arguments;
  for(var i = 4; i < iArgCount; i++){
   sPassedParms += ", aArgs[" + i + "]";
  }
 }

 var objHTTP = PUBLIC_JS.initXMLHttpRequest();

 var responseText;
  if(typeof objHTTP.onload != "undefined" && objHTTP.onload != null){
   objHTTP.onload = function(){
    try{
     responseText = objHTTP.responseText;
     eval(sResponseHandler + "(eval('(' + responseText + ')')" + sPassedParms + ")");
    }catch(e){
     responseText = objHTTP.responseText;
     if(responseText.indexOf("LoginFlag") > -1){
      var sLoginFlag = PUBLIC_JS.ReturnText(responseText, "LoginFlag");
      eval(sResponseHandler + "('" + sLoginFlag + "')");
     }
    }

   };
  }else{
    objHTTP.onreadystatechange = function(){
    if(objHTTP.readyState == 4){
     if(typeof objHTTP.status == "undefined" || objHTTP.status == 200 || objHTTP.status == 201 || objHTTP.status == 302 || objHTTP.status == 304){
      try{
       responseText = objHTTP.responseText;
       eval(sResponseHandler + "(eval('(' + responseText + ')')" + sPassedParms + ")");
      }catch(e){
       responseText = objHTTP.responseText;
       if(responseText.indexOf("LoginFlag") > -1){
        var sLoginFlag = PUBLIC_JS.ReturnText(responseText, "LoginFlag");
        eval(sResponseHandler + "('" + sLoginFlag + "')");
       }
      }
     }else{
      if(objHTTP.status == 404){
       eval(sResponseHandler + "('Error 404')");
      }
     }
    }
   };
  }

  if(iArgCount > 3){
   if("GET,POST,PUT,DELETE".indexOf(aArgs[3]) != -1){
    sMethods = aArgs[3];
   }else{
    sMethods = "GET";
   }
  }else{
   sMethods = "GET";
  }

  if(typeof sParms == "object"){
   sParms = JSON.stringify(sParms);
  }

  objHTTP.open(sMethods, sURL, true);
  objHTTP.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
  //objHTTP.setRequestHeader("Charset", "UTF-8");

  objHTTP.send(sParms);

  return objHTTP;

}

PUBLIC_JS.ReturnText = function(retTxt, retKind){
 var Result = PUBLIC_JS.Left(PUBLIC_JS.Right(retTxt, "<"+retKind+">"), "</"+retKind+">");
 return Result;
}

// Glue 서비스 파라메터 조합
PUBLIC_JS.initGlueServiceUrl = function(callUrl, ServiceName, operation, paramStr){
 var serviceInfo = "";

 serviceInfo += callUrl;
 serviceInfo += "?ServiceName="+ServiceName;
 serviceInfo += "&"+operation+"=1";

 if(paramStr != "") serviceInfo += "&"+paramStr;

 return serviceInfo;
}

//Glue 서비스 Insert, Update 파라메터 조합
PUBLIC_JS.initGlueServiceUrlUpsert = function(callUrl, ServiceName, ProgramId, operation, paramStr){
 var serviceInfo = "";

 serviceInfo += callUrl;
 serviceInfo += "?ServiceName="+ServiceName;
 serviceInfo += "&ProgramId="+ProgramId;
 serviceInfo += "&"+operation+"=1";

 if(paramStr != "") serviceInfo += "&"+paramStr;

 return serviceInfo;
}


// IE 호환성여부 체크
PUBLIC_JS.getIeCompatible = function(){
 var info = PUBLIC_JS.BrowserInfo();

 if(info.ie) {
  if(info.ieCompatible) {
   return "ie-compatible";
  } else {
   return "ie";
  }
 } else {
  return "none-ie";
 }
}

//------------------------------------------------------
//브라져 정보 반환
//------------------------------------------------------
PUBLIC_JS.BrowserInfo = function(){
 var info = new Object;
 var ver  = -1;
 var u = navigator.userAgent;
 var v = navigator.vendor || "";

 function f(s,h){ return ((h||"").indexOf(s) > -1) };

 info.opera  = (typeof window.opera != "undefined") || f("Opera",u);
 info.ie  = !info.opera && f("MSIE",u);
 info.chrome = f("Chrome",u);
 info.safari = !info.chrome && f("Apple",v);
 info.mozilla   = f("Gecko",u) && !info.safari && !info.chrome;
 info.firefox   = f("Firefox",u);
 info.camino = f("Camino",v);
 info.netscape  = f("Netscape",u);
 info.omniweb   = f("OmniWeb",u);
 info.icab   = f("iCab",v);
 info.konqueror = f("KDE",v);

 try{
  if(info.ie){
   ver = u.match(/(?:MSIE) ([0-9.]+)/)[1];

   // 호환성 보기 활성화 여부 체크
   if(u.indexOf( "MSIE 7" ) > 0 && u.indexOf( "Trident" )) {
    info.ieCompatible = true;
   } else {
    info.ieCompatible = false;
   }

  }else if(info.firefox||info.opera||info.omniweb){
   ver = u.match(/(?:Firefox|Opera|OmniWeb)\/([0-9.]+)/)[1];
  }else if(info.mozilla){
   ver = u.match(/rv:([0-9.]+)/)[1];
  }else if(info.safari){
   ver = parseFloat(u.match(/Safari\/([0-9.]+)/)[1]);
   if(ver == 100){
    ver = 1.1;
   }else{
    ver = [1.0,1.2,-1,1.3,2.0,3.0][Math.floor(ver/100)];
   }
  }else if(info.icab){
   ver = u.match(/iCab[ \/]([0-9.]+)/)[1];
  }else if (info.chrome){
   ver = u.match(/Chrome[ \/]([0-9.]+)/)[1];
  }

  info.version = parseFloat(ver);
  if (isNaN(info.version)) info.version = -1;
 }catch(e){
  info.version = -1;
 }
 return info;
}

//NULL 체크
PUBLIC_JS.getNullToEmpty = function(val){
 if(val == null) return "";
 else if(val == "null") return "";
 else return val;
}

//Date 포멧변환 YYYY-MM-DD HH:MI:SS
function dateFormat (dateVal, dateType, num) {
 var year = dateVal.getFullYear();
 var month = dateVal.getMonth()+1;
 var date = dateVal.getDate();
 var hours = dateVal.getHours();
 var minutes = dateVal.getMinutes();
 var seconds = dateVal.getSeconds();

 if (dateType == "year") {
  year = dateVal.getFullYear()+num;
  if (month.toString().length < 2) month = '0'+ month;
  if (date.toString().length < 2) date = '0'+ date;
  if (hours.toString().length < 2) hours = '0'+ hours;
  if (minutes.toString().length < 2) minutes = '0'+ minutes;
  if (seconds.toString().length < 2) seconds = '0'+ seconds;
  return year + "-" + month + "-" + date + " " + hours + ":" + minutes + ":" + seconds;

 } else if (dateType == "month") {
  month = dateVal.getMonth()+1+num;
  if (month.toString().length < 2) month = '0'+ month;
  if (date.toString().length < 2) date = '0'+ date;
  if (hours.toString().length < 2) hours = '0'+ hours;
  if (minutes.toString().length < 2) minutes = '0'+ minutes;
  if (seconds.toString().length < 2) seconds = '0'+ seconds;
  return year + "-" + month + "-" + date + " " + hours + ":" + minutes + ":" + seconds;
 } else if (dateType == "day") {
  date = dateVal.getDate()+num;
  if (month.toString().length < 2) month = '0'+ month;
  if (date.toString().length < 2) date = '0'+ date;
  if (hours.toString().length < 2) hours = '0'+ hours;
  if (minutes.toString().length < 2) minutes = '0'+ minutes;
  if (seconds.toString().length < 2) seconds = '0'+ seconds;
  return year + "-" + month + "-" + date + " " + hours + ":" + minutes + ":" + seconds;
 } else {
  if (month.toString().length < 2) month = '0'+ month;
  if (date.toString().length < 2) date = '0'+ date;
  if (hours.toString().length < 2) hours = '0'+ hours;
  if (minutes.toString().length < 2) minutes = '0'+ minutes;
  if (seconds.toString().length < 2) seconds = '0'+ seconds;
  return year + "-" + month + "-" + date + " " + hours + ":" + minutes + ":" + seconds;
 }
}

// 로딩바 추가 2016.11.29
function ajaxLoader (el, options) {
 // Becomes this.options
 var defaults = {
  bgColor: '#fff',
  duration: 800,
  opacity: 0.7,
  classOveride: false
 }
 this.options = jQuery.extend(defaults, options);
 this.container = $(el);

 this.init = function() {
  var container = this.container;
  var w, h;
  // Delete any other loaders
  this.remove();
  if(container.length > 0) {
   w = container[0].offsetWidth;
   h = container[0].offsetHeight;
  } else {
   w = container.width();
   h = container.height();
  }
  // Create the overlay
  var overlay = $('<div></div>').css({
    'background-color': this.options.bgColor,
    'opacity':this.options.opacity,
    'width':w,
    'height':h,
    'position':'absolute',
    'top':'0px',
    'left':'0px',
    'z-index':99999
  }).addClass('ajax_overlay');
  // add an overiding class name to set new loader style
  if (this.options.classOveride) {
   overlay.addClass(this.options.classOveride);
  }
  // insert overlay and loader into DOM
  container.append(
   overlay.append(
    $('<div></div>').addClass('ajax_loader')
   ).fadeIn(this.options.duration)
  );
 };

 this.remove = function(){
  var overlay = this.container.children(".ajax_overlay");
  if (overlay.length) {
   overlay.fadeOut(this.options.classOveride, function() {
    overlay.remove();
   });
  }
 }

 this.init();
}

/*
 * 날짜포맷에 맞는지 검사
 * 사용볍: isValidDate('2016-03-14') == true
 */
function isDateFormat(d) {
 var df = /[0-9]{4}-[0-9]{2}-[0-9]{2}/;
 return d.match(df);
}

/*
 * 윤년여부 검사
 */
function isLeaf(year) {
 var leaf = false;

 if (year % 4 == 0) {
  leaf = true;

  if (year % 100 == 0) {
   leaf = false;
  }

  if (year % 400 == 0) {
   leaf = true;
  }
 }

 return leaf;
}

/*
 * 날짜가 유효한지 검사
 */
function isValidDate(d) {
 // 포맷에 안맞으면 false리턴
 if (!isDateFormat(d)) {
  return false;
 }

 var month_day = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];

 var dateToken = d.split('-');
 var year = Number(dateToken[0]);
 var month = Number(dateToken[1]);
 var day = Number(dateToken[2]);

 // 날짜가 0이면 false
 if (day == 0) {
  return false;
 }

 var isValid = false;

 // 윤년일때
 if (isLeaf(year)) {
  if (month == 2) {
   if (day <= month_day[month - 1] + 1) {
    isValid = true;
   }
  } else {
   if (day <= month_day[month - 1]) {
    isValid = true;
   }
  }
 } else {
  if (day <= month_day[month - 1]) {
   isValid = true;
  }
 }
 return isValid;
}
// 현재시간-7 가져오기
function getTimeStamp(val1, val2) {
 var d = new Date();
 d.setHours(d.getHours());
 if(val1 == 'y') {
  d.setFullYear(d.getFullYear()+val2);
 } else if (val1 == 'm') {
  d.setMonth(d.getMonth()+val2);
 }  else if (val1 == 'd') {
  d.setDate(d.getDate()+val2);
 } else if (val1 == 'h') {
  d.setHours(d.getHours()+val2);
 } else if (val1 == 'mi') {
  d.setMinutes(d.getMinutes()+val2);
 } else if (val1 == 's') {
  d.setSeconds(d.getSeconds()+val2);
 }

 var s =
  leadingZeros(d.getFullYear(), 4) + '-' +
  leadingZeros(d.getMonth() + 1, 2) + '-' +
  leadingZeros(d.getDate(), 2) + ' ' +
  leadingZeros(d.getHours(), 2) + ':' +
  leadingZeros(d.getMinutes(), 2) + ':' +
  leadingZeros(d.getSeconds(), 2);
 return s;
}

function leadingZeros(n, digits) {
 var zero = '';
 n = n.toString();

 if (n.length < digits) {
  for (i = 0; i < digits - n.length; i++)
  zero += '0';
 }
 return zero + n;
}

// 텍스트창에 숫자만 입력
function onlyNumDecimalInput(){
 var code = window.event.keyCode;
 if ((code >= 48 && code <= 57) || (code >= 96 && code <= 105) || code == 110 || code == 190 || code == 8 || code == 9 || code == 13 || code == 46){
  window.event.returnValue = true;
  return;
 }
 window.event.returnValue = false;
}


//Form내용으로 조회를 하여 Func으로 리턴받음(서비스ID, 리턴Func명, 다이어그램 Flow명, FormID)
PUBLIC_JS.frmSearch = function(serviceName, callbackFuncName, operation, formId){
 var callUrl  = "comm/basicJSON.mvc";
 var paramStr = ""

 if(formId != null && formId != ''){
  paramStr = $('#' + formId).serialize();
  //paramStr = decodeURIComponent((paramStr + '').replace(/\+/g,'%20'));
 }

 try {
  var serviceUrl = PUBLIC_JS.initGlueServiceUrl(callUrl, serviceName, operation, paramStr);
  PUBLIC_JS.XMLAsyncJSON(serviceUrl, "", callbackFuncName, "POST", '');
 } catch (e) {
  alert(e);
 }
 return null;
}

// Form내용으로 그리드내용 조회 후 그리드에 DATA를 Reload함(서비스ID, 다이어그램 Flow명, GridID, FormID)
PUBLIC_JS.gridDataLoad = function(serviceName, operation, gridId, formId){
 var callUrl  = "comm/basicJSON.mvc";
 var paramStr = ""

 if(formId != null && formId != ''){
  paramStr = $('#' + formId).serialize();
  //paramStr = decodeURIComponent((paramStr + '').replace(/\+/g,'%20'));
 }

 try {
  var serviceUrl = PUBLIC_JS.initGlueServiceUrl(callUrl, serviceName, operation, paramStr);
  PUBLIC_JS.XMLAsyncJSON(serviceUrl, "", "PUBLIC_JS.getGridList", "POST", gridId);
 } catch (e) {
  alert(e);
 }
 return null;
}

// 그리드에 데이터를 reload 한다.
PUBLIC_JS.getGridList = function(rstData, gridId){
 $('#' + gridId).jsGrid({
  controller: {
   loadData: function(filter) {
    return rstData;
   }
  },
 });
 return null;
}


// Form내용으로 등록, 수정함(서비스ID, 현재 Page명, 다이어그램 Flow명, FormID)
PUBLIC_JS.frmUpsert = function(serviceName, programId, operation, formId){
 var callUrl  = "comm/basicJSON.mvc";
 var paramStr = ""

 if(formId != null && formId != ''){
  paramStr = $('#' + formId).serialize();
  //paramStr = decodeURIComponent((paramStr + '').replace(/\+/g,'%20'));
 }

 try {
  var serviceUrl = PUBLIC_JS.initGlueServiceUrlUpsert(callUrl, serviceName, programId, operation, paramStr);
  PUBLIC_JS.XMLAsyncJSON(serviceUrl, "", "PUBLIC_JS.frmUpsertResult", "POST", '');
 } catch (e) {
  alert(e);
 }
 return null;
}

// Insert, Update 후에 Msg를 리턴받고 각 화면의 CallBack Func에서 이후 처리를 한다.
PUBLIC_JS.frmUpsertResult = function(rstMsg) {
 if(rstMsg[0].result == 'success'){
  alert(rstMsg[0].msg);
  // 각 화면에 아래 Function 생성하여 Callback로직을 구현해야함(ex:그리드 재조회 or 모달창 닫기등)
  upsertSuccess();
 }else{
  alert(rstMsg[0].msg);
 }
 return null;
}

//비동기로 데이터 조회
//(java ServiceName, Operation, formId)
PUBLIC_JS.dataLoad = function(serviceName, operation, formId, callback){
 var callUrl  = "comm/basicJSON.mvc";
 var paramStr = ""

 if(formId != null && formId != ''){
  paramStr = $('#' + formId).serialize();
  paramStr = decodeURIComponent((paramStr + '').replace(/\+/g,'%20'));
 }

 try {
  var serviceUrl = PUBLIC_JS.initGlueServiceUrl(callUrl, serviceName, operation, paramStr);
  PUBLIC_JS.XMLAsyncJSON(serviceUrl, "", callback);
 } catch (e) {
  alert(e);
 }
 return null;
}

// Query String to JSon
function getUrlParams() {
 var params = {};
 window.location.search.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(str, key, value) { params[key] = value; });
 return params;
}

function lpad(s, padLength, padString){

    while(s.length < padLength)
        s = padString + s;
    return s;
}

function rpad(s, padLength, padString){
    while(s.length < padLength)
        s += padString;
    return s;
}

/* 팝업창 열릴때 사이즈 조정. popup_wrap class의 ID를 필수 */
function popupViewResize(wrapId){
 $("#" + wrapId).css({"top":"50px","left":"50%"});

 var popupW = parseInt($("#" + wrapId).width());
 var popupX = parseInt($("#" + wrapId).css("left"));
 var popupW_size = parseInt(popupW/2);
 var popupX_pos = parseInt(popupX-popupW_size);

 $("#" + wrapId).css({"left":popupX-popupW_size});
}

// 팝업열기
// 팝업전체를 감싸는 DIV(.popup_dim), 바로 다음 DIV(.popup_wrap)의 ID 필요
function popupOpen(dimId, wrapId){
 $("#" + dimId).show();
 $("#" + dimId).animate({"opacity":"1"},{duration:100, easing:"easeInOutQuart"});

 /*
 if($(this).parents(".popup_wrap").index() >= 0){
  $("#"+wrapId).show();
  $("#"+wrapId).animate({"opacity":"1"},{duration:100, easing:"easeInOutQuart"});
 }else{
  $("#"+wrapId).css({"display":"block","opacity":"1"});
 }
  */
 $("#"+wrapId).show();
 $("#"+wrapId).animate({"opacity":"1"},{duration:100, easing:"easeInOutQuart"});
 $("#"+wrapId).css({"display":"block","opacity":"1"});


 $("#"+wrapId).css({"top":"50px","left":"50%"});

 var popupW = parseInt($("#"+wrapId).width());
 var popupX = parseInt($("#"+wrapId).css("left"));
 var popupW_size = parseInt(popupW/2);
 var popupX_pos = parseInt(popupX-popupW_size);

 $("#"+wrapId).css({"left":popupX-popupW_size});
}

//팝업닫기
//팝업전체를 감싸는 DIV(.popup_dim)의 ID 필요
function popupClose(dimId){

    $("#"+dimId).animate({"opacity":"0"},{
     duration:100, easing:"easeInOutQuart",complete:function(){
      $("#"+dimId).hide();
      $("#"+dimId).css({"display":"none","opacity":"0"});
     }
    });

    // popup의 모든 textbox val 초기화
    $("#" + dimId + ' input:text').val('');

    // popup의 모든 select 선택 초기화
   /*
    $("#" + dimId).find('select option').each(function(){
     $(this).prop("selected", false);
    });
    */

    // popup의 모든 select 0번째 선택
    $("#" + dimId).find('select').each(function(){
     $(this).find('option:eq(0)').prop("selected", true);
    });

    // popup의 모든 checkbox 체크 해제
    $("#" + dimId).find('input:checkbox').prop('checked', false);
}


 function numkeyCheck(e) { 
	 var keyValue = event.keyCode; if( ((keyValue >= 48) && (keyValue <= 57)) ) return true; else return false; 
}
 