<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 상세조회</title>
</head>

<body>
<label><h3>제목</h3></label><br />
${view.title}<br />

<label><h3>작성자</h3></label><br />
${view.writer}<br />

<label><h4>내용</h4></label><br />
${view.content}<br />

<div>
	<a href="/board/modify?no=${view.no}">게시물 수정</a> ,<a href="/board/list">게시물 목록으로</a>, <a href="/board/delete?no=${view.no}">게시물 삭제</a>
</div>

</body>
</html>