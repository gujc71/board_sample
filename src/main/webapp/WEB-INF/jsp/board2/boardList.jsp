<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>board2</title>
</head>
<body>
	<a href="board2Form">글쓰기</a>
					
	<table border="1" style="width:600px">
		<caption>게시판</caption>
		<colgroup>
			<col width='8%' />
			<col width='*%' />
			<col width='15%' />
			<col width='15%' />
			<col width='10%' />
		</colgroup>
		<thead>
			<tr>
				<th>번호</th> 
				<th>제목</th>
				<th>등록자</th>
				<th>등록일</th>
				<th>조회수</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="listview" items="${listview}" varStatus="status">	
				<c:url var="link" value="board2Read">
					<c:param name="brdno" value="${listview.brdno}" />
				</c:url>		
										  				
				<tr>
					<td>
						<c:out value="${pageVO.totRow-((pageVO.page-1)*pageVO.displayRowCount + status.index)}"/>					
					</td>
					<td><a href="${link}"><c:out value="${listview.brdtitle}"/></a></td>
					<td><c:out value="${listview.brdwriter}"/></td>
					<td><c:out value="${listview.brddate}"/></td>
					<td><c:out value="${listview.brdhit}"/></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:if test="${pageVO.totPage>1}">
		<div class="paging">
			<c:forEach var="i" begin="${pageVO.pageStart}" end="${pageVO.pageEnd}" step="1">
				<c:url var="pageLink" value="board2List">
					<c:param name="page" value="${i}" />
				</c:url>						
	            <c:choose>
	                <c:when test="${i eq pageVO.page}">
	                	<c:out value="${i}"/>
	                </c:when>
	                <c:otherwise>
	                	<a href="${pageLink}"><c:out value="${i}"/></a>
	                </c:otherwise>
	            </c:choose>
	        </c:forEach>
		</div>
		<br/>
	</c:if>		    
</body>
</html>
