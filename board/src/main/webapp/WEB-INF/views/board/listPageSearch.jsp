<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 페이징 포함 목록</title>
</head>
<body>
	<table>
		 <thead>
		  <tr>
		   <th><h1>.  번호  .</h1></th>
		   <th><h1>.  제목  .</h1></th>
		   <th><h1>.   작성자   .</h1></th>
		   <th><h1>.   작성일   .</h1></th>
		   <th><a href="/board/write">글작성하기</th>
		  </tr>
		 </thead>
 
 	<tbody>
  		<c:forEach items="${list}" var="list">
			 <tr>
			  <td>${list.no}</td>
			  <td><a href="/board/view?no=${list.no}">${list.title}</a></td>
			  <td>${list.writer}</td>
			  <td><fmt:formatDate value="${list.regDate}" pattern="yyyy-MM-dd" /></td>
			 </tr>
		</c:forEach>
 	</tbody>
</table>
		<div>
		 	<c:if test="${page.prev}">
 <span>[ <a href="/board/listPageSearch?num=${page.startPageNum - 1}${page.searchTypeKeyword}">이전</a> ]</span>
</c:if>

<c:forEach begin="${page.startPageNum}" end="${page.endPageNum}" var="num">
 <span>
 
  <c:if test="${select != num}">
   <a href="/board/listPageSearch?num=${num}${page.searchTypeKeyword}">${num}</a>
  </c:if>    
  
  <c:if test="${select == num}">
   <b>${num}</b>
  </c:if>
    
 </span>
</c:forEach>

<c:if test="${page.next}">
 <span>[ <a href="/board/listPageSearch?num=${page.endPageNum + 1}${page.searchTypeKeyword}">다음</a> ]</span>
</c:if>
			
			<div>
				 <select name="searchType">
				     <option value="title" <c:if test="${searchType eq 'title'}">selected</c:if>>제목</option>
				        <option value="content" <c:if test="${searchType eq 'content'}">selected</c:if>>내용</option>
				     <option value="title_content" <c:if test="${searchType eq 'title_content'}">selected</c:if>>제목+내용</option>
				     <option value="writer" <c:if test="${searchType eq 'writer'}">selected</c:if>>작성자</option>
				 </select>
				 
				 <input type="text" name="keyword" value="${keyword}"/>
				 
				 <button type="button" id="searchBtn">검색</button>
			</div>
		</div>
<script>

 document.getElementById("searchBtn").onclick = function () {
    
	  let searchType = document.getElementsByName("searchType")[0].value;
	  let keyword =  document.getElementsByName("keyword")[0].value;
	  
	  location.href = "/board/listPageSearch?num=1" + "&searchType=" + searchType + "&keyword=" + keyword;
 };
</script>
</body>
</html>