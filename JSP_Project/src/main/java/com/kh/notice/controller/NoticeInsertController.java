package com.kh.notice.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.notice.model.service.NoticeService;
import com.kh.notice.model.vo.Notice;

/**
 * Servlet implementation class NoticeInsertController
 */
@WebServlet("/insert.no")
public class NoticeInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeInsertController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
	    request.setCharacterEncoding("UTF-8");
	            
        String userNo = request.getParameter("userNo");
        String noticeTitle = request.getParameter("title");
        String noticeContent = request.getParameter("content");
        
        Notice n = new Notice();
        n.setNoticeTitle(noticeTitle);
        n.setNoticeContent(noticeContent);
        n.setNoticeWriter(userNo);
        
        int result = new NoticeService().insertNotice(n);
        
        if (result > 0) {   // 성공시 => list.no리스트페이지로 리 다이렉트.
            request.getSession().setAttribute("alertMsg", "성공적을 공지사항이 등록되었습니다.");
            response.sendRedirect(request.getContextPath()+"/list.no"); // jsp/list.no에 보낸거
        } else {    // 실패시 -> 에러페이지 보여지도록
            request.setAttribute("errorMsg", "공지사항 등록 실패");
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
