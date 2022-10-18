<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.kh.board.model.vo.*"%>
<%
	Board b = (Board) request.getAttribute("b");
	// 게시글번호, 카테고리명, 제목, 내용, 작성자아이디, 작성일

	Attachment at = (Attachment) request.getAttribute("at");
	// 파일번호, 원본명, 수정명, 저장경로
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>일반게시판 상세보기 페이지</title>
<style>
    .outer{
        background-color: black;
        color: cornflowerblue;
        width: 1000px;
        height: 100%;
        margin: auto;	/* 가운데로 자동 정렬 */
        margin-top: 50px;
    }
</style>
</head>
<body>
	<%@ include file = "../common/menubar.jsp" %>
	
	<div class="outer">
		<br><h1>일반게시판 상세보기</h1>><br>
		
		<table id="detail-area" align="center" style="border : 1px solid cornflowerblue;">
			<tr>
				<th width="70">카테고리</th>
				<td width="70"><%= b.getCategory()  %></td> 
				<th width="70">제목</th>
				<td width="350"><%= b.getBoardTitle()  %></td>
			</tr>
			<tr>
				<th>작성자</th>
				<td><%= b.getBoardWriter() %></td> 
				<th>작성일</th>
				<td><%= b.getCreateDate() %></td> 
			</tr>
			<tr>
				<th>내용</th>
				<td colspan="3">
					<p style="height:200px"><%= b.getBoardContent() %></p>
				</td> 
			</tr>
			<tr>
				<th>첨부파일</th>
				<td colspan="3">
					<% if(at == null) { %>
						<!--  첨부파일이 없는 경우 -->
						첨부파일이 없습니다.
						
					<% } else  {%>
						<!--  첨부파일이 있는 경우 -->
						<!-- 브라우저에서 http://localhost:8080/jsp/resources/board_upfiles/xxx.jpg -->
						<a href="<%= contextPath %>/<%= at.getFilePath() + at.getChangeName() %>" download="<%= at.getOriginName() %>">
							<%= at.getOriginName() %></a>
					<% } %>
				</td> 
			</tr>
			
		</table>
		
		<br>
		
		<div align="center">
			<a href="<%= contextPath %>/list.bo?currentPage=1" class="btn btn-secondary btn-sm">목록</a>
			
			<% if(loginUser != null && loginUser.getUserId().equals(b.getBoardWriter())) { %>
				<!--  현재 로그인한 사용자가 해당 글을 작성한 작성자일 경우에만 보여진다.  -->
				<a href="<%= contextPath %>/updateForm.bo?bno=<%= b.getBoardNo() %>" class="btn btn-warning btn-sm">수정하기</a>
				<a href="<%= contextPath %>/delete.bo?bno=<%= b.getBoardNo() %>" class="btn btn-danger btn-sm">삭제하기</a>
				
			<% } else { %>
			
			
			<% } %>
		</div>
		
		<br>
		
		<!--  우선 화면구현 먼저 진행하고 기능구현은 ajax배우고 할것이다 -->
		<div id="reply-area">
			
			<table border="1" align="center">
				<thead>
					<% if (loginUser != null) {%>
						<!--  로그인이 되어있을 경우. -->
						<tr>
							<th>댓글작성</th>
							<td>
								<textarea id="replyContent" cols="50" rows="3" style="resize:mome;"></textarea>
							</td>
							<td><button onclick="">댓글등록></button></td>	
						</tr>
					
					<% } else { %>
						<tr>
							<th>댓글작성</th>
							<td>
								<textarea cols="50" rows="3" style="resize:mome;" readonly>로그인 후 이용가능한 서비스 입니다.</textarea>
							</td>
							<td><button disabled>댓글등록</button></td>
								
					<% } %>
				</thead>	
			</table>
		</div>
	
	
	</div>
</body>
</html>