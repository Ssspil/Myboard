package com.kh.board.controller;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Attachment;
import com.kh.board.model.vo.Board;
import com.kh.common.MyFileRenamePolicy;
import com.oreilly.servlet.MultipartRequest;

/**
 * Servlet implementation class BoardInsertController
 */
@WebServlet("/insert.bo")
public class BoardInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardInsertController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
	    request.setCharacterEncoding("UTF-8");
	    
	    
//	    String category = request.getParameter("category");
//	    String boardTitle = request.getParameter("title");
	    /*
	     * 폼 전송을 일반방식이 아니라 multipart/form-data로 전송하는 경우에는 request로 값을 뽑을 수 없다.
	     * multipart라는 객체로 값읅 이관시켜서 다뤄야한다.
	     */
	    
	    // enctype 이 multipart/form-data로 전송되는 경우,
	    if(ServletFileUpload.isMultipartContent(request)) {
	        // 1. 전송되는 파일을 처리할 작업 내용(전송되는 파일의 용량 , 전달된 파일을 저장할 폴더경로
	        
	        /*
	         * 단위정리
	         * byte -> kbyte -> mbyte -> gbyte -> tbyte ->
	         *  1kbyte = 1024byte (2의 10승)
	         *  1mbyte = 1024kbyte
	         * 
	         */
	        
	        // 1-1) 전송파일 용량제한 (int maxSize -> byte 단위의 값을 기술) => 10Mbyte로 제한
	        int maxSize = 1024 * 1024 * 10;
	        
	        // 1-2) 전달될 파일을 저장하ㅓㄹ 서버의 폴더경로 알아내기(String savePath)
	        /*
	         * 세션객체에서 제공하는 getRealPath 메소드를 알아내기
	         * 다만 WebContents폴더로부터 board_upfiles 폴더까지의 경로는 매개변수로 제시해줘야한다.
	         * 그리고 결론적으로 board_upfiles폴더 내부에 파일들이 저장될것이기 때문에 / 를 추가로 붙여준다.
	         */
	        
	        String savePath = request.getSession().getServletContext().getRealPath("/resources/board_upfiles/");
	        // System.out.println(savePath);
	        // /Users/jeongseungpil/Web-workspace2/JSP_Project/WebContent/resources/board_upfiles/
	        
	        /*
	         * 2. 전달된 파일명 수정 및 서버에 업로드 작업.
	         * - HttpServletRequest request -> MultipartRequest multiRequest로 변환
	         * 
	         * 매개변수 생성자로 생성(cos.jsar에서 제공하는 객체)
	         * MultipartRequest multiRequest = new MultipartRequest(request, 저장할 폴더경오(savePath), 용량제한(maxSize),
	         *                                                     인코딩값, 파일명을 수정시켜주는 객체);
	         *                                                     
	         *                                                     
	         * 위 구문 한줄만 실행하면 첨부파일이 해당폴더로 자동으로 업로드된다.
	         * 그리고 사용자가 올린 파일명은 그대로 해당폴더에 업로드 하지 않는게 일반적이다.
	         * - 같은 파일명이 있을 경우 에러가 날 수 있고, 한글, 특수문자, 띄어쓰기가 포함된 파일명일 경우 서버에 따라 문제발생가능          
	         * 
	         * 기본적으로 파일명 수정작업을 해주는 객체
	         * - DefaultFileRenamePolicy 객체 (cos.jar에서 제공하는 객체)
	         * - 기본적으로 동일한 파일명이 존재할경우 뒤에 카운팅되니 숫자를 붙여서 파일명 수정을 진행함.
	         * ex) aaa.jpg, aaa1.jpg, bbb.jpg, bbb1.jpg
	         * 
	         * - 입맛대로 파일명이 절대 안겹치겠금 rename 해볼것이다.
	         *     내 입맛대로 나만의 파일명 생성규칙을 만들어서 바꿔주는 객체를 만들기.
	         *     com.kh.common.MyFileRenamePolicy 클래스 만들기 
	         */
	        
	        MultipartRequest multiRequest = new MultipartRequest(request, savePath, maxSize, "UTF-8", new MyFileRenamePolicy());
	        
	        //3. db에 기록할 데이터들을 뽑아서 Attachment객체에 담기
            // - 카테고리 번호, 제목, 내용 , 작성자회원번호 -> Board에 INSERT
            // - 넘어온 첨부파일이 있다면 ㅁ원본명, 수정명, 폴더경로 뽑아서 -> Attachment에 INSERT
            
            String category = multiRequest.getParameter("category");
            String boardTitle = multiRequest.getParameter("title");
            String boardContent = multiRequest.getParameter("content");
            String boardWriter = multiRequest.getParameter("userNo");
        
            Board b = new Board();
            b.setCategory(category);
            b.setBoardTitle(boardTitle);
            b.setBoardContent(boardContent);
            b.setBoardWriter(boardWriter);
            
            Attachment at = null;
            
            //multiRequest.getOriginFileName("키값");
            //=> 첨부파일이 ^ㅇ^있을경우 원본면, 없을경우 null이 뜸
            if(multiRequest.getOriginalFileName("upfile") != null) {
                
                at = new Attachment();
                at.setOriginName(multiRequest.getOriginalFileName("upfile")); //원본명
                at.setChangeName(multiRequest.getFilesystemName("upfile")); // 수정명(실제 서버에 업로드 되어있는 파일명)
                at.setFilePath("resources/board_upfiles/");
            }
            
            //4. 서비스 요청
            int result = new BoardService().insertBoard(b, at);
            
            if(result > 0 ){//성공 => list.bo?currentPage=1로 리다이렉트 시킴
                request.getSession().setAttribute("alertMsg", "게시글 작성 성공!");
                response.sendRedirect(request.getContextPath()+"/list.bo");
            
            }else { //실패시 => 첨부파일 있었을 경우 이미 업로드된 첨부파일을 서버에 보관할 이유 없음(용량만 차지)-> 삭제
                if (at != null) {
                    //삭제시키고자 하는 파일 객체 생성 -> delete메소드호출
                    new File(savePath+at.getChangeName()).delete();
                    }
                request.setAttribute("errorMsg", "게시글 작성 실패");
                request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
            }
            
        }
    
    
    }
	        
	    
	    
	    
	    
	    


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
