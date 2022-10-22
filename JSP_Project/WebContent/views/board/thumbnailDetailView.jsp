<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList, com.kh.board.model.vo.*"%>
<%
	Board b = (Board) request.getAttribute("b");
	ArrayList<Attachment> list = (ArrayList<Attachment>) request.getAttribute("list");
%>    
    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사진게시판 상세보기 페이지</title>
<style>
    .outer{
        background-color: black;
        color: cornflowerblue;
        width: 1000px;
        height: 800px;
        margin: auto;	/* 가운데로 자동 정렬 */
        margin-top: 50px;
    }
</style>
</head>
<body>
	<%@ include file = "../common/menubar.jsp" %>
	
	
	<div class="outer">
		<br><h1>사진게시판 상세보기</h1><br>
		
		
    <table class="detail-area" align="center" border="1">
        <tr>
            <th width="70">제목</th>
            <td colspan="3" width="600"><%= b.getBoardTitle() %></td>
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
                <p style="height:50px"><%= b.getBoardContent() %></p>
                </td>
        </tr>
        <tr>
            <th>대표사진</th>
            <td colspan="3">
                <div>
                    <img src="<%= contextPath %>/<%= list.get(0).getFilePath() + list.get(0).getChangeName() %>" width="500" height="300">
                </div>
            </td>
        </tr>
            
        <tr>
            <td>상세사진</td>
            <td colspan="3">
                <% for (int i = 1; i < list.size(); i++) { %>
                    <img src="<%= contextPath %>/<%= list.get(i).getFilePath() + list.get(i).getChangeName() %>" width="200" height="150">
                    
                <% } %>
            </td>
        </tr>
        
    </table>

	
	</div>
	
	
	
	
	
	
	
	
	
	
	
	
	
	
</body>
</html>