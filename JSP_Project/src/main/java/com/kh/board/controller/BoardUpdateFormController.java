package com.kh.board.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Attachment;
import com.kh.board.model.vo.Board;
import com.kh.board.model.vo.Category;

/**
 * Servlet implementation class BoardUpdateFormController
 */
@WebServlet("/updateForm.bo")
public class BoardUpdateFormController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardUpdateFormController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
	    BoardService bService = new BoardService();
	    
	    int boardNo = Integer.parseInt(request.getParameter("bno"));
	    
	    // 카테고리 정보
	    ArrayList<Category> list = bService.selectCategoryList();
	    // 게시글의 정보
	    Board b = bService.selectBoard(boardNo);
	    Attachment at = bService.selectAttachment(boardNo);
	    
	    request.setAttribute("list", list);
	    request.setAttribute("b", b);
	    request.setAttribute("at", at);
	    
	    
	    
	    
	    request.getRequestDispatcher("views/board/boardUpdateForm.jsp").forward(request, response);
	    
	    System.out.println("일반게시판 수정하기");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
