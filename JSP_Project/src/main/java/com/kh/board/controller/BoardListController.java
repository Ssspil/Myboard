package com.kh.board.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Board;
import com.kh.common.model.vo.PageInfo;

/**
 * Servlet implementation class BoardListController
 */
@WebServlet("/list.bo")
public class BoardListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardListController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
	    // -------------------------- 페이징 처리 시작 --------------------------------
	    int listCount;         // 현재 총 게시글 개수.
	    int currentPage;       // 현재 페이지(즉, 사용자가 요청한 페이지)
	    int pageLimit;         // 페이지 하단에 보여질 페이징바의 페이지 최대갯수
	    int boardLimit;        // 페이지에 보여질 게시글의 최대갯수
	    
	    int maxPage;           // 가장 마지막 페이지가 몇번쨰 페이지인지(총 페이지 수)
	    int startPage;         // 페이지 하단에 보여질 페이징바의 시작수
	    int endPage;           // 페이지 하단에 보여질 페이징 바의 끝수.
	    
	    // listCount : 총 게시글 갯수.
	    listCount = new BoardService().selectListCount();
	    
	    // currentPage : 현재페이지
	    currentPage = Integer.parseInt(request.getParameter("currentPage") == null ? "1" : request.getParameter("currentPage"));
	    
	    // pageLimit : 페이지 하단에 보여질 페이징바의 페이지 최대갯수(페이지 목록들 몇개 단위로 끊을건지) - 10개씩
	    pageLimit = 10;
	    
	    // boardLimit : 한 페이지에 보여질 게시글의 최대갯수 (게시글 몇개 단위씩)
	    boardLimit = 10;
	    
	    // * maxPage : 가장 마지막 페이지가 몇번 페이지 인지(총 페이지수)
	    /*
	     * listCount, boardLimit 에 영향을 받는다.
	     * 
	     * - 공식 구하기
	     *     단, boardLimit값이 10이라는 가정하에 규칙을 구하기
	     *     
	     *     총 갯수                    boardLimit                          maxPAge
	     *     100개        /               10개         =>  10              10번 페이지
	     *     101개        /               10개         =>  10.1            11번 페이지
	     *     109개        /               10개         =>  10.9            11번 페이지
	     *     110개        /               10개         =>  11              11번 페이지
	     *     111개        /               10개         =>  11.1            12번 페이지                               
	     *     => 나눗셈 결과를 올림처리하면 maxPage의 값이 나온다.
	     *     
	     *     1) listCount를 double로 형변환
	     *     2) listCount / boardLimit
	     *     3) 결과에 올림처리 Math.ceil() 메소드 호출
	     *     4) 결과값을 int로 형 변환
	     * 
	     */
	    
	    maxPage = (int) Math.ceil((double)listCount / boardLimit);
	    
	    // startPage : 페이지 하단에 보여질 페이징바의 시작수
	    /*
	     * 단, pageLimit, currentPage 에 영향을 받음.
	     * 
	     * - 공식 구하기
	     * 단, pageLimit 10 이라는 가정하에 규칙을 세워보기
	     * 
	     * startPage : 1, 11, 21, 31 ... -> n*10 + 1 => 10의 배수 + 1
	     * 
	     * 만약에 pageLimit 5라면? 1,6,11,16,21 ... => 5의 배수 + 1
	     * 
	     * 즉, n* pageLimit + 1
	     * 
	     * currentPage                 startPage
	     *     1                           1           => [n *10 +1 = 1]  =>  n = 0
	     *     5                           1           => n = 0
	     *     10                          1           => n = 0
	     *     
	     *     11                          11          => [n * 10 + 1 = 11]    =>  n = 1
	     *     15                          11          =>  n = 1
	     *     20                          11          =>  n = 1
	     * 
	     * => 1~10 : n = 0
	     * => 11~20 : n = 1
	     * => 21~30 : n = 2
	     * 
	     * n = (currentPage - 1) / pageLimit
	     *        0~9 / 10 = 0
	     *        10~19 / 10 = 1 
	     *        ...
	     *        
	     * startPage = n * pageLimit + 1
	     *           => (currentPage - 1) / pageLimit * pageLimit + 1
	     *            
	     */
	    startPage = (currentPage - 1) / pageLimit * pageLimit + 1;
	    
	    // endPage : 페이지 하단에 보여질 페이징 바의 끝수
	    
	    /*
	     * startPage , pageLimit에 영향을 받음 + (maxPage에도 영향을 받음)
	     * 
	     * - 공식구하기
	     *  단, pageLimit값이 10이라는 가정하에 규칙을 구해볼것.
	     *  
	     *  startPage : 1 => endPage : 10
	     *  startPage : 11 => endPage : 20
	     *  startPage : 21 => endPage : 30
	     * 
	     * endPage = startPage + pageLimit - 1;
	     */
	    endPage = startPage + pageLimit - 1;
	    
	    // startPAge 가 11이여서 endPage는 20 으로 설정됨(근데 maxPagesms 11까지만 존재함)
	    // 12 부터 20페이지까지는 비어있는 목록만 보여지게 될것이므로 endPage를 maxPage로 덮어씌우기함.
	    if(endPage > maxPage) {
	        endPage = maxPage;
	        
	    }
	    
	    // 페이지 정보들을 하나의 공간에 담아서 보내기
	    // 페이지 정보를 담은 VO클래스를 활용
	    // => 사진게시판, 공지사항게시판에서도 사용할 수 있기 때문 에 common
	    
	    // 1. 페이징바 만들때 필요한 객체
	    PageInfo pi = new PageInfo(listCount, currentPage, pageLimit, boardLimit, maxPage, startPage, endPage);
	    
	    // 2. 현재 사용자가 요청한 페이지에 보여줄 페이징바객체 전달
	    // request.setAttribute("pi", pi);
	    
	    
	    
	    
	    
	    // -------------------------- 페이징 처리 끝 --------------------------------
	    
	    // 현재 일반게시판의 게시글들 가져오기.
	    ArrayList<Board> list = new BoardService().selectList(pi);
	    
	    request.setAttribute("list", list);
	    request.setAttribute("pi", pi);
	    
	    request.getRequestDispatcher("views/board/boardListView.jsp").forward(request, response);;
	    
	    System.out.printf("일반게시판의 %d 페이지 이동\n", currentPage);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
