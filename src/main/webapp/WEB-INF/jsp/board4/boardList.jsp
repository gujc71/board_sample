<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>board4</title>
<script>
function fn_formSubmit(){
	document.form1.submit();	
}
</script>
</head>
<body>
	<a href="board4Form">글쓰기</a>
					
	<table border="1" style="width:650px">
		<caption>게시판</caption>
		<colgroup>
			<col width='8%' />
			<col width='*%' />
			<col width='15%' />
			<col width='15%' />
			<col width='10%' />
			<col width='10%' />
		</colgroup>
		<thead>
			<tr>
				<th>번호</th> 
				<th>제목</th>
				<th>등록자</th>
				<th>등록일</th>
				<th>조회수</th>
				<th>첨부</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="listview" items="${listview}" varStatus="status">	
				<c:url var="link" value="board4Read">
					<c:param name="brdno" value="${listview.brdno}" />
				</c:url>		
										  				
				<tr>
					<td><c:out value="${searchVO.totRow-((searchVO.page-1)*searchVO.displayRowCount + status.index)}"/></td>
					<td style="max-width: 100px; border: 1px solid black; overflow: hidden; white-space: nowrap; text-overflow: ellipsis;">
						<a href="${link}"><c:out value="${listview.brdtitle}"/></a>
					</td>
					<td><c:out value="${listview.brdwriter}"/></td>
					<td><c:out value="${listview.brddate}"/></td>
					<td><c:out value="${listview.brdhit}"/></td>
					<td><c:out value="${listview.filecnt}"/></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<form id="form1" name="form1"  method="post">
	    <jsp:include page="/WEB-INF/jsp/common/pagingforSubmit.jsp" />
	    
		<div>
			<input type="checkbox" name="searchType" value="brdtitle" <c:if test="${fn:indexOf(searchVO.searchType, 'brdtitle')!=-1}">checked="checked"</c:if>/>
			<label class="chkselect" for="searchType1">제목</label>
			<input type="checkbox" name="searchType" value="brdmemo" <c:if test="${fn:indexOf(searchVO.searchType, 'brdmemo')!=-1}">checked="checked"</c:if>/>
			<label class="chkselect" for="searchType2">내용</label>
			<input type="text" name="searchKeyword" style="width:150px;" maxlength="50" value='<c:out value="${searchVO.searchKeyword}"/>' onkeydown="if(event.keyCode == 13) { fn_formSubmit();}">
			<input name="btn_search" value="검색" class="btn_sch" type="button" onclick="fn_formSubmit()" />
		</div>
	</form>	
</body>
</html>
