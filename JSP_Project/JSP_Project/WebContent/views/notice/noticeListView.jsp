<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList, com.kh.notice.model.vo.Notice" %>
    
<%
	ArrayList<Notice> list = (ArrayList<Notice>)request.getAttribute("list");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항 페이지</title>
<style>
    .outer{
        background-color: black;
        color: cornflowerblue;
        width: 1000px;
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
	<%@ include file = "../common/menubar.jsp" %>
	
	<div class="outer">
		<br>
		<h1>공지사항</h1>
		<br>
		
		<!--  공지사항은 관리자만 작성가능 -->
		<% if(loginUser != null && loginUser.getUserId().equals("admin")) { %>
			<div align="right" style="width : 850px;">
				<!-- a태그를 쓰고도 버튼 모양으로 만들 수가 있음 (부트스트랩에서 제공하는 btn class) -->
				<a class="btn btn-secondary" href="<%=contextPath%>/enrollForm.no">글작성</a>
			</div>
		<% } %>
		
		<table class="list-area" align="center">
			<thead>
				<tr>
					<td>글번호</td>
					<td width="400">글제목</td>
					<td width="100">작성자</td>
					<td>조회수</td>
					<td width="100">작성일</td>
				</tr>
			</thead>
			<tbody>
				<% if(list.isEmpty()) { %>
					<!--  리스트가 비어있는 경우. -->
					<tr>
						<td colspan="5">존재하는 공지사항이 없습니다.</td>
					</tr>
				
				<% }  else {%>
					<% for(Notice n : list) { %>
						<tr>
							<td><%= n.getNoticeNo() %></td>
							<td><%= n.getNoticeTitle() %></td>
							<td><%= n.getNoticeWriter() %></td>
							<td><%= n.getCount() %></td>
							<td><%= n.getCreateDate() %></td>
						</tr>
				
					<% } %>
				<% } %>
			</tbody>
		</table>
		

	</div>
	
	<script>
		$(function(){
			$(".list-area>tbody>tr").click(function(){
				// 클릭시 해당 공지사항의 번호를 넘겨야함.
				// 해당 tr요소의 자손중에서 첫번째 td의 영역의 내용이 필요.
				
				let nno = $(this).children().eq(0).text();	// 1, 2
				// 내가 클릭한 tr자손들중 0번째에 위치한 자식의 textnode내용을 가져온다.
				
				// 요청할 url?키=값&키=값&키=값
				// 물음표 뒤에 내용을 쿼리스트링이라고 부른다. => 직접 만들어서 넘겨줘야함.
				location.href = '<%= contextPath %>/detail.no?nno='+nno;
			})
		})
	
	</script>
	
	
	
</body>
</html>