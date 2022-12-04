# Myboard Ssspil🧑‍💻
개인 프로젝트 - 게시판 만들기
<br><br>

## Description🥰
> &nbsp; 2022. 10 ~ 2022. 11
<br>

## Contests📌
```sh
일반게시판, 사진게시판, 썸네일을 보여주는 게시판
```
<br>

## Tools💯
* HTML5
* CSS3
* JavaScript
* JAVA
* jQuery
* Ajax
* Oracle
* Tomcat9
* Eclipse
* Visual Studio Code
<br>


## Code Review👻
```sh
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

System.out.println("사진게시판 첨부파일 업로드 기능");

if(ServletFileUpload.isMultipartContent(request)) {
    // 1-1) 전송 용량제한
    int maxSize = 10 * 1024 * 1024; //10mByte

    // 1-2) 저장할 폴더의 물리적인 경로
    String savePath = request.getServletContext().getRealPath("/resources/thumbnail_upfiles/");

    // 2) 전달된 파일명 수정 작업 후 서버에 업로드
    MultipartRequest multiRequest = new MultipartRequest(request, savePath, maxSize, "UTF-8", new MyFileRenamePolicy());

    // 3) db에 저장
    // Board에 insert할 값 뽑기
    Board b = new Board();
    b.setBoardWriter( ((Member)request.getSession().getAttribute("loginUser")).getUserNo() + "" );
    b.setBoardTitle(multiRequest.getParameter("boardTitle"));
    b.setBoardContent(multiRequest.getParameter("boardContent"));

    // Attachment 테이블에 여러번 insert할 데이터 뽑기
    // 단, 여러개의 첨부파일이 있을것이기 때문에 attachment들 ArrayList에 담을것
    // => 적어도 1개이상은 담길 예정
    ArrayList<Attachment> list = new ArrayList<>();

    for(int i = 1; i <= 4; i++) {  // 파일의 갯수는 최대 4개이다. file1, file2, file3, file4
        String key = "file" + i;

        if(multiRequest.getOriginalFileName(key) != null) {
            // 첨부파일이 있는경우
            // Attachment 객체 생성 + 원본명 , 수정명, 파일경로 + 파일레벨 담기
            // list에 추가하기
           Attachment at = new Attachment();
           at.setOriginName(multiRequest.getOriginalFileName(key));
           at.setChangeName(multiRequest.getFilesystemName(key));
           at.setFilePath("/resources/thumbnail_upfiles/");
           at.setFileLevel(i);

           list.add(at);
        }
    }

    int result = new BoardService().insertThumbnailBoard(b, list);

    if (result > 0) {  // 성공 -> list.th 재요청
        request.getSession().setAttribute("alertMsg", "성공적으로 업로드 되었습니다.");
        response.sendRedirect(request.getContextPath() + "/list.th");

    } else {   // 실패 -> 에러페이지
        request.setAttribute("errorMsg", "사진게시판 업로드 실패");
        request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);

    }

}
```
<br>



## About Project🇰🇷
#### 0. 메인
<img width="600" alt="스크린샷 2022-12-04 오후 9 24 02" src="https://user-images.githubusercontent.com/92138800/205490483-40cdb3bc-c130-4fec-adb6-b298c8ad4150.png">
<br>

#### 1. 로그인
<img width="600" alt="스크린샷 2022-12-04 오후 9 24 25" src="https://user-images.githubusercontent.com/92138800/205490490-ec3c7787-06c5-4f8c-af36-142174eb467f.png">
<br>

#### 2. 회원가입
<img src="https://user-images.githubusercontent.com/92138800/205491278-a7bb36eb-9b93-48a1-adc3-b6dda9333f36.png" width="600" />
<br>

#### 3. 내 정보 수정
<img src="https://user-images.githubusercontent.com/92138800/205491286-27480fcf-fd85-4e3a-a376-a42db07c7c7f.png" width="600"/>
<img src="https://user-images.githubusercontent.com/92138800/205491284-d9265ff1-a398-4c43-aa2b-58b5960df117.png"  width="600"/>
<br>

#### 4. 회원탈퇴
<img src="https://user-images.githubusercontent.com/92138800/205491283-6fb5bc5c-6265-4047-bfec-ca2093b9855a.png"  width="600"/>
<br>

#### 5. 일반게시판 (공지사항과 동일) 페이징
<img width="600" alt="스크린샷 2022-12-04 오후 9 28 54" src="https://user-images.githubusercontent.com/92138800/205490487-43e1a7fe-e691-4aeb-a5e6-48d3de840f05.png">
<img width="600" alt="스크린샷 2022-12-04 오후 9 28 06" src="https://user-images.githubusercontent.com/92138800/205490489-f4ce6f83-dab4-4d68-bf6d-3d5f7dcc4cb0.png">
<img src="https://user-images.githubusercontent.com/92138800/205491281-83ef0fcb-08d1-4e2f-bef7-4b02457a87e2.png" width="600" />
<img src="https://user-images.githubusercontent.com/92138800/205491660-a297a929-7043-4adb-a057-fe0cbca2d6c5.png" width="600" />
<br>

#### 6. 사진게시판
<img src="https://user-images.githubusercontent.com/92138800/205491280-90507069-4b9d-417d-8a12-3d0ab4a159e2.png" width="600" />
<img src="https://user-images.githubusercontent.com/92138800/205491282-78fcaf69-b381-462a-a220-7b474f7c393f.png"  width="600"/>
<img src="https://user-images.githubusercontent.com/92138800/205491274-064726d3-6654-448b-ac91-c58b12388f15.png" width="600" />
<br><br><br><br>








