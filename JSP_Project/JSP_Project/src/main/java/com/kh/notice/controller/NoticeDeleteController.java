package com.kh.notice.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.member.model.vo.Member;
import com.kh.notice.model.service.NoticeService;

/**
 * Servlet implementation class NoticeDeleteController
 */
@WebServlet("/delete.no")
public class NoticeDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeDeleteController() {
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
            request.setAttribute("errorMsg", "공지사항 삭제권한이 없습니다.");
            request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
            
            return;
        }
	    
	    int noticeNo = Integer.parseInt(request.getParameter("nno"));
	    
	    int result = new NoticeService().deleteNotice(noticeNo);
	    
	    if (result > 0) {  // 삭제 성공시 -> /jsp/list.no
	        request.getSession().setAttribute("alertMsg", "성공적으로 공지사항이 삭제되었습니다.");
	        
	        response.sendRedirect(request.getContextPath() + "/list.no");
	         
	    } else {
	        request.setAttribute("errorMsg", "공지사항 삭제 실패");
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
