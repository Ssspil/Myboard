package com.board.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.board.dao.BoardDAO;
import com.board.domain.BoardVO;
import com.board.service.BoardService;

@Controller
@RequestMapping("/board/*")
public class BoardController {
	
	@Inject
	private BoardService service;
	
	// 게시글 목록
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public void getList(Model model) throws Exception{
		
		List<BoardVO> list = null ;
		list = service.list();
		model.addAttribute("list", list);
	}
	
	// 게시물 목록 + 페이징
	@RequestMapping(value="/listPage", method=RequestMethod.GET)
	public void getListpage(Model model, @RequestParam("num") int num) throws Exception{
		
		// 게시물 총 갯수
		int count = service.count();
		// 한 페이지에 출력할 게시물 개수
		int postNum = 10;
		// 하단 페이징 번호
		int pageNum = (int)Math.ceil((double)count/postNum);
		// 출력할 게시물
		int displayPost = (num -1) * postNum;
		
		List<BoardVO> list = null ;
		list = service.listPage(displayPost, postNum);
		model.addAttribute("list", list);
		model.addAttribute("pageNum", pageNum);
	}
	
	// 게시글 작성 1 (작성 폼가져오기)
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public void getWriter() throws Exception {
	}
	// 게시물 작성 2 (리스트에 넣기)
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String postWrite(BoardVO vo) throws Exception {
		service.write(vo);
		
		return "redirect:/board/list";
	}
	
	// 게시물 조회
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public void getView(@RequestParam("no") int no, Model model) throws Exception {
		BoardVO vo = service.view(no);
		model.addAttribute("view",vo);
	}
	
	// 게시물 수정 1 (수정폼 가져오기)
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public void getModify(@RequestParam("no") int no, Model model) throws Exception {
		BoardVO vo = service.view(no);
		model.addAttribute("view",vo);
	}
	// 게시물 수정 2 (리스트에 수정된거 넣기)
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String postModify(BoardVO vo) throws Exception {
		service.modify(vo);
		
		return "redirect:/board/view?no=" + vo.getno();  //수정완료하면 그 해당 게시물 조회 된곳으로 돌라가게함
	}
	
	// 게시물 삭제
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String getDelete(@RequestParam("no") int no) throws Exception {
		service.delete(no);
		
		return "redirect:/board/list";
	}
}





















