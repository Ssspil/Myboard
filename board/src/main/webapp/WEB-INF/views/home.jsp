<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	남서울대학교 커뮤니티 사이트 
</h1>

<P>  지금 시간은 ${serverTime} 입니다. </P>


<p>
<a href="/board/list">게시물 목록</a><br />
<a href="/board/write">게시물 작성</a>
</p>
</body>
</html>
