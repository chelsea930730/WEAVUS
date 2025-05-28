package com.example.post.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.post.model.posts.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	List<Comment> findAllByPostId(Long postId);
}
