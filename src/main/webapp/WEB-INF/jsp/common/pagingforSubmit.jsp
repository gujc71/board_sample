<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${searchVO.totPage>1}">
	<div class="paging">
		<c:if test="${searchVO.page>1}">
			<a href="javascript:fnSubmitForm(1);">[처음]</a>
			<a href="javascript:fnSubmitForm(${searchVO.page-1});">[이전]</a>
		</c:if>
				
		<c:forEach var="i" begin="${searchVO.pageStart}" end="${searchVO.pageEnd}" step="1">
            <c:choose>
                <c:when test="${i eq searchVO.page}">
                	<c:out value="${i}"/>
                </c:when>
                <c:otherwise>
                	<a href="javascript:fnSubmitForm(${i});"><c:out value="${i}"/></a>
                </c:otherwise>
            </c:choose>
            <c:if test="${not status.last}">|</c:if>
        </c:forEach>
        
		<c:if test="${searchVO.totPage > searchVO.page}">
			<a href="javascript:fnSubmitForm(${searchVO.page+1});">[다음]</a>
			<a href="javascript:fnSubmitForm(${searchVO.totPage});">[마지막]</a>
		</c:if>
	</div>
	<br/>
		
	<input type="hidden" name="page" id="page" value="" />
		
	<script type="text/javascript">
	function fnSubmitForm(page){ 
		document.form1.page.value=page;
		document.form1.submit();
	}
	</script>
</c:if>
