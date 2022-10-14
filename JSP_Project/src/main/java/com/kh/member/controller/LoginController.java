package com.kh.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.member.model.service.MemberService;
import com.kh.member.model.vo.Member;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/login.me")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
        // TODO Auto-generated constructor stub
    }
    private MemberService ms = new MemberService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * <HttpServletRequest 객체와 HttpServletResponse 객체>
		 * 
		 * - request : 서버로 요청할 때의 정보들이 담겨있음 (요청시 전달값, 요청전송 방식등)
		 * - response : 요청에 대해 응답할 때 필요한 객체.
		 * 
		 * <GET방식과 POST 방식차이>
		 * 
		 * - get : 사용자가 입력한 값들이 url에 노출o / 데이터길이제한o / 대신 즐겨찾기에 편리.
		 * -post : 사용자가 입력한 값들이 url노출 x / 데이터길이제한x / 대신 timeout이 존재.
		 * 
		 */
		
		// 1) 전달값에 한글이 있을 경우 인코딩 처리해줘야한다 (post방식 일경우)
		request.setCharacterEncoding("UTF-8");
		
		// 2) 요청 시 전달값을 꺼내서 변수 또는 객체에 기록하기.
		String userId = request.getParameter("userId");
		String userPwd = request.getParameter("userPwd");
		
		// 3) 해당 요청을 처리하는 서비스의 메소드 호출.
		Member loginUser = ms.loginMember(userId, userPwd);
		
		// 4) 처리된 결과를 가지고 사용자가 보게될 뷰를 지정.
		
		/*
		 * 
		 * 응답페이지에 전달할 값이 있을 경우 값을 어딘가에 담아야함. (담아줄 수 있는 Servlet scope 내장 객체 4종류)
		 * 1) application : application 에 담은 데이터는 웹 애플리케이션 전역에서 다 꺼내쓸 수 있음.
		 * 2) session : session에 담은 데이터는 모든 jsp와 servlet에서 꺼내 쓸 수 있음.
		 * 				한번 담은 데이터는 내가 직접 지우기 전까지, 서버가 멈추기 전까지, 브라우저 종료전까지 접근가능.
		 * 2-1) cookie : 클라이언트에서 정보를 담기위한것.
		 * 3) request : request에 담은 데이터는 해당 request를 포워딩한 응답 jsp에서만 꺼내 쓸 수 있음.
		 * 4) page : 해당 jsp페이지에서만 꺼내 쓸수 있음.
		 * 
		 * 자주 쓰이는 건 request 와 session
		 * 
		 */
		
		
		if(loginUser == null) {     // 로그인실패 => 에러페이지 응답.

			request.setAttribute("errorMsg", "로그인에 실패했습니다.");
			
			// 응답페이지 jsp에 위임시 필요한 객체 (RequestDispatcher)
			RequestDispatcher view = request.getRequestDispatcher("views/common/errorPage.jsp");
			
			// 포워딩 방식 : 해당경로로 선택된 뷰가 보여질 뿐이고 url은 변하지 않음.
			view.forward(request, response);
			
			
		} else {     // 로그인 성공 =? index페이지 응답.

			// 로그인한 회원의 정보를 로그아웃하기 전까지 계속 가져다 쓸것이기 때문에 session에 담기.
			// Servlet에서 JSP내장 객체중 Session에 접근하고자 한다면 우선session객체를 얻어와야한다.
			HttpSession session = request.getSession();
			session.setAttribute("loginUser", loginUser);
			
			session.setAttribute("alertMsg", "성공적으로 로그인이 되었습니다.");
			
			/*
			 * 1. 포워딩 방식 응답 뷰 출력하기
			 * RequestDispatcher view = request.getRequestDispatcher("index.jsp");
			 * view.forward(request, response);
			 * 해당 선택된 jsp가 보여질 뿐이고, url에는 여전히 현재 이 서블릿 매핑값이 남아있음.
			 * localhost:8080/jsp/login.me 이렇게 출력되므로 login.me를 없애줘야한다.
			 * 
			 * 2. url 재요청방식 (sendRedirect방식)
			 * 		localhost:8080/jsp
			 * 
			 * 
			 */
			response.sendRedirect(request.getContextPath());
			
			System.out.println("로그인 했습니다.");
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
