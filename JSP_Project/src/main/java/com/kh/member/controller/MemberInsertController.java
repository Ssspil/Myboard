package com.kh.member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.common.AESCryptor;
import com.kh.member.model.service.MemberService;
import com.kh.member.model.vo.Member;

/**
 * Servlet implementation class MemberInsertController
 */
@WebServlet(name="memberInsertServlet", urlPatterns="/insert.me")
public class MemberInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberInsertController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
	    // 1) 인코딩 작업
	    request.setCharacterEncoding("UTF-8");
	    
	    // 2) 요청시 전달값을 뽑아서 변수 및 객체에 기록하기.
	    String userId = request.getParameter("userId");
	    String userPwd = request.getParameter("userPwd");
	    String userName = request.getParameter("userName");
	    String phone = request.getParameter("phone");
	    String email = request.getParameter("email");
	    String address = request.getParameter("address");
	    String[] interestArr = request.getParameterValues("interest");
	    
	    email = AESCryptor.encrypt(email);
	    
	    // "운동, 등산"
	    String interest = interestArr != null ? String.join(",", interestArr) : "";
	    
	    // 매개변수 생성자를 이용해서 Member객체에 담기
	    Member m = new Member(userId, userPwd, userName, phone, email, address, interest);
	    
	    // 3) 요청 처리 (서비스 메소드 호출 및 결과받기)
	    int result = new MemberService().insertMember(m);
	    
	    // 4) 처리결과를 가지고 사용자가 보게될 응답 뷰를 지정.
	    if(result > 0) {   // 성공
	        HttpSession session = request.getSession();
	        session.setAttribute("alertMsg", "회원가입에 성공했습니다.");
	        
	        response.sendRedirect(request.getContextPath());
	        
	        System.out.println("회원가입을 완료했습니다.");
	        
	    } else {   // 실패 => 에러페이지.
	        request.setAttribute("errorMsg", "회원가입에 실패했습니다.");
	        
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
