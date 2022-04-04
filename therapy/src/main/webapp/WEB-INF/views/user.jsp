<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
  <!DOCTYPE html>
  <html> 
  <head> 
  <meta charset="UTF-8">
   <title>사용자 목록</title>
    <style> 
    body {
     margin:5% 
     } 
    table, th, td { 
    border: 1px solid black; 
    border-collapse: collapse; 
    } 
    table {
    width: 70%; 
    text-align:center; 
    margin:auto; 
    } 
    </style>
    </head>
     <body>
     <h1>사용자 목록</h1>
     <h2>총 사용자 : ${userCount } 명</h2>
     <table class="table"> 
     	<thead> 
     	<tr>
     	 <th>No</th>
     	 <th>ID</th> 
     	 <th>이름</th> 
     	 <th>핸드폰 번호</th> 
     	 </tr> 
     	 </thead> 
     	<tbody> 
     	<c:choose> 
     	<c:when test="${fn:length(userList) !=0 }"> 
     	<c:forEach var="item" items="${userList}" varStatus="status"> <tr>
     	<td>${status.count }</td> 
     	<td><c:out value="${item.id}" /></td> 
     	<td><c:out value="${item.name}" /></td> 
     	<td><c:out value="${item.phone}" /></td> 
     	</tr> 
     	</c:forEach> 
     	</c:when> 
     	<c:otherwise> 
     	<tr> 
     	<td colspan="4" >조회된 정보가 없습니다.</td>
     	 </tr> 
     	 </c:otherwise> 
     	</c:choose> 
     	</tbody> 
     </table> 
     <p><a href="/board/list">게시글 목록</a></p>
   </body> 
</html>
