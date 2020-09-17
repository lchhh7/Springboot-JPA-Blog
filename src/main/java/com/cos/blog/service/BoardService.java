package com.cos.blog.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.Board;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;

//스프링이 컴포넌트 스캔을 통해서 bean에 등록을 해준다. ->IoC를 해준다
@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;
	
	@Transactional
	public void write(Board board,User user) {
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}
	
	public Page<Board> writeList(Pageable pageable){
		return boardRepository.findAll(pageable);
	}
	
	@Transactional(readOnly = true)
	public Board detail(int id) {
		return boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("상세보기 실패 : 아이디를 찾을수 없습니다");
				});
	}
	
	@Transactional
	public void deletion(int id) {
		boardRepository.deleteById(id);
	}
	
	@Transactional
	public void contentUpdate(int id,Board requestBoard) {
		Board board = boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 찾기 실패 : 아이디를 찾을수 없습니다");
				}); //영속화 완료
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		//해당 함수로 종료시에(service 종료) 트랜잭션 종료 이때 더티체킹이 일어나먼저 자동업데이트가 flush
	}
}
