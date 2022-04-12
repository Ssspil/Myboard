<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
	<a href="/board/modify?bno=${view.bno}">게시물 수정</a> ,<a href="/board/listPageSearch?num=1">게시물 목록으로</a>, <a href="/board/delete?bno=${view.bno}">게시물 삭제</a>
</div>
	<!-- 댓글 시작 -->
<hr />

<c:forEach items="${reply}" var="reply">
<li>
	<div>
		<p>${reply.writer} / ${reply.regDate}</p>
		<p>${reply.content }</p>
	</div>
</li>	
</c:forEach>

<div>

	<form method="post" action="/reply/write">
	
		<p>
			<label>댓글 작성자</label> <input type="text" name="writer">
		</p>
		<p>
			<textarea rows="5" cols="50" name="content"></textarea>
		</p>
		<p>
		<input type="hidden" name="bno" value="${view.bno}">
			<button type="submit">댓글 작성</button>
		</p>
	</form>
	
</div>
	<!--  댓글 끝 -->


</body>
</html>