package com.cos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.Board;

//자동으로 bean등록이 되서 @Repository 어노테이션을 사용하지 않아도 된다.
public interface BoardRepository extends JpaRepository<Board, Integer>{

}


