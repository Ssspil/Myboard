<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList, com.kh.board.model.vo.Board"%>
<%
	ArrayList<Board> list = (ArrayList<Board>) request.getAttribute("list");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사진게시판 페이지</title>
<style>
    .outer{
        background-color: black;
        color: cornflowerblue;
        width: 1000px;
        height: 800px;
        margin: auto;	/* 가운데로 자동 정렬 */
        margin-top: 50px;
    }
    .list-area{
    	width : 760px;
    	margin : auto;
    	
    }
</style>
</head>
<body>

	<%@ include file="../common/menubar.jsp" %>

	<div class="outer">
		<br><h1>사진게시판</h1><br>
		
		<% if(loginUser != null) { %>
			<div align="right" style="width:850px">
				<a href="<%= contextPath %>/enrollForm.th" class="btn btn-secondary">글작성</a>
				<br>
				<br>
			</div>
	
		<% } %>
		<div class="list-area">
			<% if(!list.isEmpty()) { %>
				<% for (Board b : list) { %>
					<div class="thumbnail" align="center" 
					<%-- </div>onclick="location.href='<%= contextPath %>/detail.th?bno=<%= b.getBoardNo() %>' " 아래 스크립트 부분하고 일치한다--%>>
						<input type="hidden" value="<%= b.getBoardNo() %>">
						<img src="<%= contextPath %>/<%= b.getTitleImg() %>" width="200px" height="150px">
						<!--  /jsp/resources/board_upfiles/이미지파일? -->
						<p>
							No.<%= b.getBoardNo() %> <%= b.getBoardTitle() %><br>
							조회수 : <%= b.getCount() %>
						</p>
					</div>
				
				<% } %>
			
			<% } else { %>
				등록된 게시글이 없습니다.
				
			<% } %>
		</div>
		
		<script>
			$(function(){
				$(".thumbnail").click(function(){
					location.href = "<%= contextPath %>/detail.th?bno=" + $(this).children().eq(0).val();
				});
			})
		</script>
		
		
		
	</div>
















</body>
</html>