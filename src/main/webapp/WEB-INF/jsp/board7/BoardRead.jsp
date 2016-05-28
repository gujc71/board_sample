<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>board7</title>
<script src="js/jquery-2.2.3.min.js"></script>
<script>
function fn_formSubmit(){
	if ( $.trim($("#rewriter1").val()) == "") {
		alert("작성자를 입력해주세요.");
		$("#rewriter1").focus();
		return;
	}
	if ($.trim($("#rememo1").val()) == "") {
		alert("글 내용을 입력해주세요.");
		$("#rememo1").focus();
		return;
	}
	$("#form1").submit();	
}

function fn_replyDelete(reno){
	if (!confirm("삭제하시겠습니까?")) {
		return;
	}
	$("#form2").attr("action", "board7ReplyDelete");
	$("#reno2").val(reno);
	$("#form2").submit();	
} 

var updateReno = updateRememo = null;
function fn_replyUpdate(reno){
	hideDiv("replyDialog");
	
	$("#replyDiv").show();
	
	if (updateReno) {
		$("#replyDiv").appendTo(document.body);
		$("#reply"+updateReno).text(updateRememo);
	} 
	
	$("#reno2").val(reno);
	$("#rememo2").val($("#reply"+reno).text());
	$("#reply"+reno).text("");
	$("#replyDiv").appendTo($("#reply"+reno));
	$("#rememo2").focus();
	updateReno   = reno;
	updateRememo = $("#rememo2").val();
} 

function fn_replyUpdateSave(){
	if ($.trim($("#rememo2").val()) == "") {
		alert("글 내용을 입력해주세요.");
		$("#rememo2").focus();
		return;
	}
	
	$("#form2").attr("action", "board7ReplySave");
	$("#form2").submit();	
} 

function fn_replyUpdateCancel(){
	hideDiv("#replyDiv");
	
	$("#reply"+updateReno).text(updateRememo);
	updateReno = updateRememo = null;
} 

function hideDiv(id){
	$(id).hide();
	$(id).appendTo(document.body);
}

function fn_replyReply(reno){
	$("#replyDialog").show();
	
	if (updateReno) {
		fn_replyUpdateCancel();
	} 
	
	$("#reparent3").val(reno);
	$("#rememo3").val("");
	$("#replyDialog").appendTo($($("#reply"+reno)));
	$("#rewriter3").focus();
} 
function fn_replyReplyCancel(){
	hideDiv("#replyDialog");
} 

function fn_replyReplySave(){
	if ( $.trim($("#rewriter3").val()) == "") {
		alert("작성자를 입력해주세요.");
		$("#rewriter3").focus();
		return;
	}
	if ($.trim($("#rememo3").val()) == "") {
		alert("글 내용을 입력해주세요.");
		$("#rememo3").focus();
		return;
	}
	$("#form3").attr("action", "board7ReplySave");
	$("#form3").submit();
}
</script>

</head>
<body>
		<table border="1" style="width:600px">
			<caption>게시판</caption>
			<colgroup>
				<col width='15%' />
				<col width='*%' />
			</colgroup>
			<tbody>
				<tr>
					<td>작성자</td> 
					<td><c:out value="${boardInfo.brdwriter}"/></td> 
				</tr>
				<tr>
					<td>제목</td> 
					<td><c:out value="${boardInfo.brdtitle}"/></td>  
				</tr>
				<tr>
					<td>내용</td> 
					<td><c:out value="${boardInfo.brdmemo}" escapeXml="false"/></td> 
				</tr>
				<tr>
					<td>첨부</td> 
					<td>
						<c:forEach var="listview" items="${listview}" varStatus="status">	
            				<a href="fileDownload?filename=<c:out value="${listview.filename}"/>&downname=<c:out value="${listview.realname }"/>"> 							 
							<c:out value="${listview.filename}"/></a> <c:out value="${listview.size2String()}"/><br/>
						</c:forEach>					
					</td> 
				</tr>
			</tbody>
		</table>    
		<a href="board7List">돌아가기</a>
		<a href="board7Delete?brdno=<c:out value="${boardInfo.brdno}"/>">삭제</a>
		<a href="board7Form?brdno=<c:out value="${boardInfo.brdno}"/>">수정</a>
		<p>&nbsp;</p>
		<div style="border: 1px solid; width: 600px; padding: 5px">
			<form id="form1" name="form1" action="board7ReplySave" method="post">
				<input type="hidden" id="brdno1" name="brdno" value="<c:out value="${boardInfo.brdno}"/>"> 
				작성자: <input type="text" id="rewriter1" name="rewriter" size="20" maxlength="20"> <br/>
				<textarea id="rememo1" name="rememo" rows="3" cols="60" maxlength="500" placeholder="댓글을 달아주세요."></textarea>
				<a href="#" onclick="fn_formSubmit()">저장</a>
			</form>
		</div>
		
		<c:forEach var="replylist" items="${replylist}" varStatus="status">
			<div id="replyItem<c:out value="${replylist.reno}"/>" 
				 style="border: 1px solid gray; width: 600px; padding: 5px; margin-top: 5px; margin-left: <c:out value="${20*replylist.redepth}"/>px; display: inline-block">	
				<c:out value="${replylist.rewriter}"/> <c:out value="${replylist.redate}"/>
				<a href="#" onclick="fn_replyDelete('<c:out value="${replylist.reno}"/>')">삭제</a>
				<a href="#" onclick="fn_replyUpdate('<c:out value="${replylist.reno}"/>')">수정</a>
				<a href="#" onclick="fn_replyReply('<c:out value="${replylist.reno}"/>')">댓글</a>
				<br/>
				<div id="reply<c:out value="${replylist.reno}"/>"><c:out value="${replylist.rememo}"/></div>
			</div><br/>
		</c:forEach>

		<div id="replyDiv" style="width: 99%; display:none">
			<form id="form2" name="form2" action="board7ReplySave" method="post">
				<input type="hidden" id="brdno2" name="brdno" value="<c:out value="${boardInfo.brdno}"/>"> 
				<input type="hidden" id="reno2" name="reno"> 
				<textarea id="rememo2" name="rememo" rows="3" cols="60" maxlength="500"></textarea>
				<a href="#" onclick="fn_replyUpdateSave()">저장</a>
				<a href="#" onclick="fn_replyUpdateCancel()">취소</a>
			</form>
		</div>
		
		<div id="replyDialog" style="width: 99%; display:none">
			<form id="form3" name="form3" action="board7ReplySave" method="post">
				<input type="hidden" id="brdno3" name="brdno" value="<c:out value="${boardInfo.brdno}"/>"> 
				<input type="hidden" id="reno3" name="reno"> 
				<input type="hidden" id="reparent3" name="reparent"> 
				작성자: <input type="text" id="rewriter3" name="rewriter" size="20" maxlength="20"> <br/>
				<textarea id="rememo3" name="rememo" rows="3" cols="60" maxlength="500"></textarea>
				<a href="#" onclick="fn_replyReplySave()">저장</a>
				<a href="#" onclick="fn_replyReplyCancel()">취소</a>
			</form>
		</div>							
</body>
</html>
