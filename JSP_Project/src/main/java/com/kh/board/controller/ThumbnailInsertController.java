package com.kh.board.controller;

import java.io.IOException;
import java.util.ArrayList;

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
import com.kh.member.model.vo.Member;
import com.oreilly.servlet.MultipartRequest;

/**
 * Servlet implementation class ThumbnailInsertController
 */
@WebServlet("/insert.th")
public class ThumbnailInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ThumbnailInsertController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
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

	    
	    
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
