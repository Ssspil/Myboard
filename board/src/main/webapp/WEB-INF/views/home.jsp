<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<title>남서울대 게시판에 오신걸 환영합니다.</title>
</head>
<body>
<h1>
	남서울대 커뮤니티 사이트
</h1>

<P>  현재 시간은  ${serverTime} 입니다. </P>

<form>

	아이디 : <input type="text" name="UserID"><br>
	비밀번호 : <input type="password" name="UserPassword"><br>
	 <p> <a href="/board/list"> 로그인 (게시물 목록 들어가는 곳)</a>
	<p> <a href="/board/write"> 게시물 작성하기</a>
	<p> <a href="/board/listPage?num=1"> 로그인 (게시물 페이징목록 들어가는 곳)</a>
</form>


</body>
</html>
