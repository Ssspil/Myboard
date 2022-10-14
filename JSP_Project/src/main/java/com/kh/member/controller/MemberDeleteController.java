package com.kh.member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.member.model.service.MemberService;
import com.kh.member.model.vo.Member;

/**
 * Servlet implementation class MemberDeleteController
 */
@WebServlet("/delete.me")
public class MemberDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberDeleteController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
	    request.setCharacterEncoding("UTF-8");
	    
	    // 로그인한 회원의 정보를 얻어내는 방법.
	    // 방법1. input type="hidden"으로 애초에 요청시 해당 값을 숨겨서 전달 받음.
	    // 방법2. (안전한 방법). session 영역에 담겨있는 회원객체로부터 뽑기.
	    
	    // 세션에 담겨있는 기존의 로그인된 사용자의 정보를 얻어오기
	    HttpSession session = request.getSession();
	    String userId = ((Member)session.getAttribute("loginUser")).getUserId();
	    
	    String userPwd = request.getParameter("userPwd");
	    
	    int result = new MemberService().deleteMember(userId, userPwd);
	   
	    
	    if (result > 0) {  // 성공했을 때 => 메인페이지에 alert, 로그아웃.
	        session.setAttribute("alertMsg", "성공적으로 회원탈퇴 되었습니다. 그 동안 이용해주셔서 감사합니다.");
	        
	        // invalidate() : 세션이 만료되어 alertMsg를 사용하지 못하게됨
	        // removeAttribute(키값)을 이용해서 로그인한 사용자의 정보를 지워주는 방식으로 로그아웃처리함.
	        
	        session.removeAttribute("loginUser");
	        response.sendRedirect(request.getContextPath());
	        
	        System.out.println("회원탈퇴에 성공했습니다.");
	        
	    } else {   // 실패 => 에러메세지.
	        
	        request.setAttribute("errorMsg", "회원 탈퇴에 실패했습니다.");
	        
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
