<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 페이징 포함 목록</title>
</head>
<body>
	<table>
		 <thead>
		  <tr>
		   <th><h1>.  번호  .</h1></th>
		   <th><h1>.  제목  .</h1></th>
		   <th><h1>.   작성자   .</h1></th>
		   <th><h1>.   작성일   .</h1></th>
		   <th><a href="/board/write">글작성하기</th>
		  </tr>
		 </thead>
 
 	<tbody>
  		<c:forEach items="${list}" var="list">
			 <tr>
			  <td>${list.bno}</td>
			  <td><a href="/board/view?no=${list.bno}">${list.title}</a></td>
			  <td>${list.writer}</td>
			  <td><fmt:formatDate value="${list.regDate}" pattern="yyyy-MM-dd" /></td>
			 </tr>
		</c:forEach>
 	</tbody>
</table>
		<div>
		 	<c:if test="${page.prev}">
				 <span>[ <a href="/board/listPage?num=${page.startPageNum - 1}">이전</a> ]</span>
				</c:if>
				
				<c:forEach begin="${page.startPageNum}" end="${page.endPageNum}" var="num">
				 <span>
				 
				  <c:if test="${select != num}">
				   <a href="/board/listPage?num=${num}">${num}</a>
				  </c:if>    
				  
				  <c:if test="${select == num}">
				   <b>${num}</b>
				  </c:if>
				    
				 </span>
				</c:forEach>
				
				<c:if test="${page.next}">
				 <span>[ <a href="/board/listPage?num=${page.endPageNum + 1}">다음</a> ]</span>
			</c:if>
		</div>

</body>
</html>