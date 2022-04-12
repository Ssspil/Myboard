<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 목록</title>
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
			  <td>${list.no}</td>
			  <td><a href="/board/view?no=${list.bno}">${list.title}</a></td>
			  <td>${list.writer}</td>
			  <td><fmt:formatDate value="${list.regDate}" pattern="yyyy-MM-dd" /></td>
			 </tr>
		</c:forEach>
 	</tbody>
	
	
</table>
</body>
</html>