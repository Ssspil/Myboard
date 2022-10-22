<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사진게시판 작성하기 페이지</title>
<style>
    .outer{
        background-color: black;
        color: cornflowerblue;
        width: 1000px;
        height: 700px;
        margin: auto;	/* 가운데로 자동 정렬 */
        margin-top: 50px;
    }
    #enroll-form table{
    	border : 1px solid cornflowerblue;
    }
    #enroll-form input, #enroll-form textarea{
    	width:100%;
    	box-sizing : border-box;
    }
</style>
</head>
<body>

	<%@ include file="../common/menubar.jsp" %>
	<div class="outer">
		<br><h1>사진게시판 작성하기</h1><br>
		
		<form action="<%=contextPath %>/insert.th" id="enroll-form" method="post" enctype="multipart/form-data">
			<input type="hidden" name="userNo" value="<%= loginUser.getUserNo() %>">
			
			<table align="center">
				<tr>
					<th width="100">제목</th>
					<td colspan="3"><input type="text" name="boardTitle"></td>
				</tr>
				<tr>
					<th>내용</th>
					<td colspan="3">
						<textarea name="boardContent" style="resize : none;" rows="5" required></textarea>
					</td>
				</tr>
				<tr>
					<th>대표이미지</th><!-- 미리보기라는 뜻 -->
					<td colspan="3" align-="center">
						<img id="titleImg" width="250" height="170">
					</td>
				</tr>
				<tr>
					<th>상세이미지</th><!-- 미리보기 -->
					<td><img id="contentImg1" width="150" height="120"></td>
					<td><img id="contentImg2" width="150" height="120"></td>
					<td><img id="contentImg3" width="150" height="120"></td>
				</tr>			
			</table>
			
			<div id="file-area">
				<input type="file" id="file1" name="file1" onchange="loadImg(this, 1);" required> <!-- 대표이미지 -->
				<input type="file" id="file2" name="file2" onchange="loadImg(this, 2);" > <!-- 대표이미지 -->
				<input type="file" id="file3" name="file3" onchange="loadImg(this, 3);" > <!-- 대표이미지 -->
				<input type="file" id="file4" name="file4" onchange="loadImg(this, 4);" > <!-- 대표이미지 -->
				<!--  onchange : input태그의 내용물이 변경될시 발생하는 이벤트 -->
			</div>
			
			<script>
				$(function(){
					$("#file-area").hide();
					
					$("#titleImg").click(function(){
						$("#file1").click();
					});
					
					$("#contentImg1").click(function(){
						$("#file2").click();
					});
					
					$("#contentImg2").click(function(){
						$("#file3").click();
					});
					
					$("#contentImg3").click(function(){
						$("#file4").click();
					});
				});
				
				function loadImg(inputFile, num){
					// inputFile : 현재 변화가 생긴 input type="file"요소 객체
					// num : 몇번째 input요소인지 확인후 해당 영역에 미리보기하기위한 하드코딩된 변수
					
					/* console.log(inputFile.files, inputFile.files.length) 
				 	 *	이건 조건문으로 하기위해 출력해본것이고
				 	 *	파일 선택시 length=1, 파일 선택 취소시 length= 0 이 출력된다 => 즉, 파일의 존재유무를 알수있다.
				 	 *	files속성은 업로드된 파일의 정보들을 배열형식으로 여러개 묶어서 반환, length는 그 배열의 크
					 */
					 
					 if(inputFile.files.length != 0){
						 // 선택된 파일이 존재할 경우
						 // 선택된 파일들을 읽어들여서 그 영역에 맞는 곳에 미리보기
						 
						 // 파일을 읽어들을 FileReader 객체 생성
						 let reader = new FileReader();
						 
						 // 파일을 읽어들이는 메소드 -> 어느 파일을 읽어들일지 매개변수로 제시해야함.
						 // 0번 인덱스에 담긴 파일정보를 제시
						 // => 해당 파일을 읽어들이는 순간 해당 파일만의 고유한 url이 부여된다.
						 // => 해당url의 src의 속성값으로 제시
						 reader.readAsDataURL(inputFile.files[0]);
						 
						 // 파일 읽기가 완료되었을 때 실행할 함수 정의
						 reader.onload = function(e){	// e :윈도우객체에 타켓속성에 접근할수 있는 매개변수. / e. targetr.result에 reader의 고유 url담김
							 // 각 영역에 맞춰서 이미지 미리보기
							 switch(num){
							 case 1 : $("#titleImg").attr("src", e.target.result);
							 		  break;
							 case 2 : $("#contentImg1").attr("src", e.target.result);
							 		  break;
							 case 3 : $("#contentImg2").attr("src", e.target.result);
					 		 		  break;
							 case 4 : $("#contentImg3").attr("src", e.target.result);
					 		  		  break;
							 }	 
						 }
						 
					 } else {
						 // 선택된 파일이 사라졌을 경우 미리보기도 사라지게끔 작업
						 switch(num){
						 case 1 : $("#titleImg").attr("src", null);
						 		  break;
						 case 2 : $("#contentImg1").attr("src", null);
						 		  break;
						 case 3 : $("#contentImg2").attr("src", null);
				 		 		  break;
						 case 4 : $("#contentImg3").attr("src", null);
				 		  		  break;
						 }	 
					 }
				}
			</script>
			
			<div align="center">
				<button type="submit">등록하기</button>
			</div>
			
		
		</form>
		
		
	</div>








</body>
</html>