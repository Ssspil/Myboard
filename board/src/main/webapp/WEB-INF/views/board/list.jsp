<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>insert title here</title>
</head>
<body>

<div id="nav">
 <%@ include file="../include/nav.jsp" %>
</div>
<table>
 <thead>
  <tr>
   <th>이름</th>
   <th>전화번호</th>
   <th>번호</th>
   <th>작성자</th>
   <th>조회수</th>
  </tr>
 </thead>
 
 <tbody>
 
 
 <c:forEach items="${list}" var="list">
 <tr>
  <td>${list.bno}</td>
  <td>
    <a href="/board/view?bno=${list.bno}">${list.title}</a>
</td>
<td>
  <fmt:formatDate value="${list.regDate}" pattern="yy-MM-dd" />
  </td>
  <td>${list.writer}</td>
  <td>${list.viewCnt}</td>
 </tr>
</c:forEach>
 
 </tbody>
 </table>
 





</body>
</html>