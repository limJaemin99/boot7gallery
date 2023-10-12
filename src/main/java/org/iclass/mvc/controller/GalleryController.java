package org.iclass.mvc.controller;

import javax.servlet.http.HttpSession;

import org.iclass.mvc.dto.Gallery;
import org.iclass.mvc.service.GalleryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class GalleryController {

	private GalleryService service;
	
	public GalleryController(GalleryService service) {
		this.service=service;
	}
	
	@GetMapping("/gallery")
	public void gallery(Model model,HttpSession session) {		//화면 : 파일명은 gallery.html
		String userid = (String)session.getAttribute("userid");
		model.addAttribute("list", service.getList());
		if(userid != null)
			model.addAttribute("myhearts",service.myHearts(userid));
		else
			model.addAttribute("myhearts", List.of(0));
	} //3-g. 로그인 사용자의 좋아요 글목록을 저장하기
	
	@PostMapping("/gallery")
	public String save(Gallery vo) {
		int count = service.save(vo);
		return "redirect:gallery";
	}
}
