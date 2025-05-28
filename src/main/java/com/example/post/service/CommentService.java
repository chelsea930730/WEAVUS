package com.example.post.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.post.model.posts.Comment;
import com.example.post.repository.CommentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CommentService {
	private final CommentRepository commentRepository;
	
	// 댓글 등록
	@Transactional
	public void addComment(Comment comment) {
		commentRepository.save(comment);
	}
	
	// 댓글 전체 조회
	public List<Comment> getCommentsByPostId(Long postId) {
		// 게시글 ID에 대한 모든 댓글 목록을 조회
		return commentRepository.findAllByPostId(postId);
	}
	
	// 댓글 조회
	public Comment getComment(Long commentId) {
		// 댓글 ID에 대한 내용을 조회
		return commentRepository.findById(commentId)
				.orElseThrow(() -> new NoSuchElementException("댓글이 존재하지 않습니다."));
	}
	
	// 댓글 수정
	@Transactional
	public void editComment(Comment updateCommnet) {
		Comment comment = getComment(updateCommnet.getId());
		comment.setContent(updateCommnet.getContent());
	}
	
	// 댓글 삭제
	@Transactional
	public void deleteComment(Long commentId) {
		commentRepository.deleteById(commentId);
	}
}















