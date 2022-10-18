package com.kh.member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LogoutController
 */
@WebServlet("/logout.me")
public class LogoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogoutController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    // 로그아웃 요청 처리 => session에 저장되어 있는 loginUser정보를 만료시키기 (세션 무효화)
	    HttpSession session = request.getSession();
	    
	    session.invalidate();  // 세션에 저장되어있는 객체들을 다 없앤다. 반환형 void
	    
	    // 응답페이지 -> /jsp
	    // url 재요청방식 => index.jsp
	    // response.sendRedirect("/jsp");
	    
	    response.sendRedirect(request.getContextPath());       // "/jsp"
	    
	    System.out.println("로그아웃 했습니다.");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
