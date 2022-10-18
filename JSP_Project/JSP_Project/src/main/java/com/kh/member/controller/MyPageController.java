package com.kh.member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class MyPageController
 */
@WebServlet("/myPage.me")
public class MyPageController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyPageController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    // url로 직접 요청도 가능하기 때문에  
	    // 로그인 전 요청시 -> 메인페이지로 , 로그인 후 요청시 -> 마이페이지로 포워딩.
	    HttpSession session = request.getSession();
	    
	    if (session.getAttribute("loginUser") == null) { // 로그인 안한 상태
	        session.setAttribute("alertMsg", "로그인 후 이용가능한 서비스입니다.");
	        response.sendRedirect(request.getContextPath());
	        
	    } else {   // 로그인 한 상태
	        request.getRequestDispatcher("views/member/myPage.jsp").forward(request, response);
	        
	        System.out.println("마이페이지로 이동했습니다.");
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
