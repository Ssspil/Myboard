package com.kh.notice.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.notice.model.service.NoticeService;
import com.kh.notice.model.vo.Notice;
import com.kh.member.model.vo.Member;

/**
 * Servlet implementation class NoticeUpdateController
 */
@WebServlet("/update.no")
public class NoticeUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeUpdateController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	    // 관리자가 아니면 실행 안되게 하는 것.
	    if( !(request.getSession().getAttribute("loginUser") != null && 
	            ((Member)request.getSession().getAttribute("loginUser")).getUserId().equals("admin"))) {
	        request.setAttribute("errorMsg", "공지사항 수정권한이 없습니다.");
	        request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
	        
	        return;
	    }
	    
	    request.setCharacterEncoding("UTF-8");
	    
	    int noticeNo = Integer.parseInt(request.getParameter("nno"));
	    String noticeTitle = request.getParameter("title");
	    String noticeContent = request.getParameter("content");
	    
	    Notice n = new Notice();
	    n.setNoticeNo(noticeNo);
	    n.setNoticeTitle(noticeTitle);
	    n.setNoticeContent(noticeContent);
	    
	    int result = new NoticeService().updateNotice(n);
	    
	    if(result > 0) {   // 성공시 => 상세보기 화면으로 이동.
	        request.getSession().setAttribute("alertMsg", "성공적으로 공지사항이 수정되었습니다.");
	        response.sendRedirect(request.getContextPath() + "/detail.no?nno=" + noticeNo);
	        // /jsp/detail.no?nno=1
	        
	        System.out.println("공지사항 수정완료");
	        
	    } else {   // 실패시 => 에러페이지로 이동
	        request.setAttribute("errorMsg", "공지사항 수정 실패");
	        request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);

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
