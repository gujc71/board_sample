<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>board9</title>
<script src="js/jquery-2.2.3.min.js"></script>
<script>
function chkInputValue(id, msg){
	if ( $.trim($(id).val()) == "") {
		alert(msg+" 입력해주세요.");
		$(id).focus();
		return false;
	} 
	return true;
}
function fn_formSubmit(){
	if ( ! chkInputValue("#brdwriter", "작성자를")) return;
	if ( ! chkInputValue("#brdtitle", "글 제목을")) return;
	if ( ! chkInputValue("#brdmemo", "글 내용을")) return;
	
	$("#form1").submit();
} 
</script> s
</head>
<body>
	<h1><c:out value="${bgInfo.bgname}"/></h1>	
	<form id="form1" name="form1" action="board9Save" method="post" enctype="multipart/form-data">
		<table border="1" style="width:600px">
			<caption>게시판</caption>
			<colgroup>
				<col width='15%' />
				<col width='*%' />
			</colgroup>
			<tbody>
				<tr>
					<td>작성자</td> 
					<td><input type="text" id="brdwriter" name="brdwriter" size="20" maxlength="20" value="<c:out value="${boardInfo.brdwriter}"/>"></td> 
				</tr>
				<tr>
					<td>제목</td> 
					<td><input type="text" id="brdtitle" name="brdtitle" size="70" maxlength="250" value="<c:out value="${boardInfo.brdtitle}"/>"></td> 
				</tr>
				<tr>
					<td>내용</td> 
					<td><textarea id="brdmemo" name="brdmemo" rows="5" cols="60"><c:out value="${boardInfo.brdmemo}"/></textarea></td> 
				</tr>
				<tr>
					<td>첨부</td> 
					<td>
						<c:forEach var="listview" items="${listview}" varStatus="status">
							<input type="checkbox" name="fileno" value="<c:out value="${listview.fileno}"/>">	
            				<a href="fileDownload?filename=<c:out value="${listview.filename}"/>&downname=<c:out value="${listview.realname }"/>"> 							 
							<c:out value="${listview.filename}"/></a> <c:out value="${listview.size2String()}"/><br/>
						</c:forEach>					
					
						<input type="file" name="uploadfile" multiple="" />
					</td> 
				</tr>
			</tbody>
		</table>     
		<input type="hidden" name="bgno" value="<c:out value="${bgno}"/>"> 
		<input type="hidden" name="brdno" value="<c:out value="${boardInfo.brdno}"/>"> 
		<a href="#" onclick="fn_formSubmit()">저장</a>
	</form>	
</body>
</html>
