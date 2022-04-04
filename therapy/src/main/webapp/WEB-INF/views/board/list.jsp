<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 목록</title>
</head>
<body>
<table>
	<thead>
  	<tr>
	   <th>번호(No.)</th>
	   <th>ID</th>
	   <th>이름</th>
	   <th>핸드폰 번호</th>
	 </tr>
 	</thead>
 
 	<tbody>
  	<c:forEach items="${list}" var="list">
 <tr>
  <td>${list.bno}</td>
  <td>${list.title}</td>
  <td>${list.regDate}</td>
  <td>${list.writer}</td>
  <td>${list.viewCnt}</td>
 </tr>
</c:forEach>
 	</tbody>

</table>
</body>
</html>