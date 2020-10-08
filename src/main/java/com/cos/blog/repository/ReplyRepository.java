package com.cos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.cos.blog.model.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Integer>{
	
	@Modifying
	@Query(value="INSERT INTO REPLY(userId,boardId,content,createDate) VALUES(?,?,?,now())",nativeQuery = true)
	public int mSave(int userId,int boardId,String content); //업데이트된 행이 갯수 리턴해줌
}
