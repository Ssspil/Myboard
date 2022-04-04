package com.world.therapy.user.board;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.world.therapy.user.board.*;

@Controller
@RequestMapping("/board/*")
public class BoardController {
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public void getList() {
		
	}

}
