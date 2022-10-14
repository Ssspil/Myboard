<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import="java.util.ArrayList , com.kh.board.model.vo.Board , com.kh.common.model.vo.PageInfo" %>
<%
	ArrayList<Board> list = (ArrayList<Board>)request.getAttribute("list");
	PageInfo pi = (PageInfo) request.getAttribute("pi");
	
	int currentPage = pi.getCurrentPage();
	int startPage = pi.getStartPage();
	int endPage = pi.getEndPage();
	int maxPage = pi.getMaxPage();
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>일반게시판</title>
<style>
    .outer{
        background-color: black;
        color: cornflowerblue;
        width: 1000px;
        height: 100%;
        margin: auto;	/* 가운데로 자동 정렬 */
        margin-top: 50px;
    }
    .list-area{
    	border : 1px solid cornflowerblue;
    	text-align:center;
    }
    .list-area>tbody>tr:hover{
    	background-color : orange;
    	cursor : pointer;
    	color : darkblue;
    }
</style>
</head>
<body>
	<%@ include file="../common/menubar.jsp" %>
	
	<div class="outer">
		<br><h1>일반게시판</h1><br>
		
		<% if(loginUser != null) {%>
			<div align="right" style="width:850px;">
				<a href="<%=contextPath%>/enrollForm.bo" class="btn btn-secondary">글작성</a>
				<br>
				<br>
				
			</div>
		<% } %>
	
	
		<table align="center" class="list-area">
			<thead>
				<tr>
					<th width="70">글번호</th>
					<th width="70">카테고리</th>
					<th width="300">제목</th>
					<th width="100">작성자</th>
					<th width="50">조회수</th>
					<th width="100">작성일</th>
				</tr>
			</thead>
			<tbody>
				<% if(list.isEmpty()) { %>
					<tr>
						<td colspan="6">조회된 리스트가 없습니다.</td>
					</tr>
				<% } else {%>
					<% for(Board b : list) {%>
						<tr>
							<td><%= b.getBoardNo() %></td>
							<td><%= b.getCategory() %></td>
							<td><%= b.getBoardTitle() %></td>
							<td><%= b.getBoardWriter() %></td>
							<td><%= b.getCount() %></td>
							<td><%= b.getCreateDate() %></td>
						</tr>
					<% } %>
				<% } %>
			</tbody>
		</table>
		
		<script>
			$(function(){
				$(".list-area>tbody>tr").click(function(){
					// 클릭시 해당 공지사항의 번호를 넘겨야함.
					// 해당 tr요소의 자손중에서 첫번째 td의 영역의 내용이 필요.
					
					let bno = $(this).children().eq(0).text();	// 1, 2
					// 내가 클릭한 tr자손들중 0번째에 위치한 자식의 textnode내용을 가져온다.
					
					// 요청할 url?키=값&키=값&키=값
					// 물음표 뒤에 내용을 쿼리스트링이라고 부른다. => 직접 만들어서 넘겨줘야함.
					location.href = '<%= contextPath %>/detail.bo?bno='+bno;
				})
			})
		</script>
		
		<br><br>
		
		<!-- 페이징 바를 표시해줄 공간 -->
		<div align="center" class="paging-area">
			<% if (currentPage != 1) { %>
				<button onclick="doPageClick(<%= currentPage - 1 %>)">&lt;</button>
			<% } %>
			
			<% for (int i = startPage; i <= endPage; i++) {%>
				<% if (i != currentPage) { %>
					<button onclick="doPageClick(<%= i %>)"><%= i %></button>
				<% } else { %>
					<button disabled><%= i %></button>
				<% } %>
			<% } %>
		
			<% if (currentPage != maxPage) { %>
				<button onclick="doPageClick(<%= currentPage + 1 %>)">&gt;</button>
			
			<% } %>
		</div>
		
		<script>
			function doPageClick(currentPage){
				location.href = "<%= contextPath %>/list.bo?currentPage=" + currentPage;
			}
		</script>
		
		
		
		
	</div>
	
	
	
	

	
</body>
</html>