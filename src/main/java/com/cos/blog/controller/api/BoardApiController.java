package com.cos.blog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.ResponseDTO;
import com.cos.blog.model.Board;
import com.cos.blog.service.BoardService;

@RestController
public class BoardApiController {
	
	@Autowired
	private BoardService boardService;
	
	@PostMapping("/api/board")
	public ResponseDTO<Integer> save(@RequestBody Board board,@AuthenticationPrincipal PrincipalDetail principal) {
		boardService.write(board,principal.getUser());
		return new ResponseDTO<Integer>(HttpStatus.OK.value(),1); //자바오브젝트를 json으로 변환해서 리턴(jackson)
	}	
	@DeleteMapping("api/board/{id}")
	public ResponseDTO<Integer> deleteById(@PathVariable int id){
		boardService.deletion(id);
		return new ResponseDTO<Integer>(HttpStatus.OK.value(),1);
	}
	
	@PutMapping("api/board/{id}")
	public ResponseDTO<Integer> update(@PathVariable int id,@RequestBody Board board){
		boardService.contentUpdate(id,board);
		return new ResponseDTO<Integer>(HttpStatus.OK.value(),1);
	}
}
