package com.example.post.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.post.model.posts.Comment;
import com.example.post.model.posts.CommentResponseDto;
import com.example.post.model.posts.Post;
import com.example.post.model.users.User;
import com.example.post.service.CommentService;
import com.example.post.service.PostService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("api")
@RequiredArgsConstructor
@RestController
public class CommentController {	
	private final PostService postService;
	private final CommentService commentService;
	
	/*
	 * HTTP 요청 방식
	 * GET: 조회
	 * POST: 저장, 등록
	 * PUT: 교체
	 * PATCH: 일부교체, 수정
	 * DELETE: 삭제
	 */
	
	// 댓글 작성(POST)
	@PostMapping("posts/{postId}/comments")
	public ResponseEntity<CommentResponseDto> addComment(
			@PathVariable(name = "postId") Long postId,
			@RequestBody Comment comment,
			@SessionAttribute(name = "loginUser") User loginUser) throws JsonMappingException, JsonProcessingException {
		
		log.info("comment: {}", comment);
		/*
		 * comment: {"content": "테스트"}
		 */
//		ObjectMapper objectMapper = new ObjectMapper();
//		Comment value = objectMapper.readValue(comment, Comment.class);
//		log.info("value: {}", value);
		
		// 어떤 게시글에 대한 댓글인지
		Post post = postService.getPostById(postId);
		// 누가 썼는지를 세션에서 받아온다.
		comment.setPost(post);
		comment.setUser(loginUser);
		log.info("comment: {}", comment);
		commentService.addComment(comment);
		
		CommentResponseDto responseDto = comment.toResponseDto();
				
		return ResponseEntity.ok(responseDto);
	}
	
	// 댓글 조회(GET)
	@GetMapping("posts/{postId}/comments")
	public ResponseEntity<List<CommentResponseDto>> getComments(
			@PathVariable(name = "postId") Long postId) {
		List<Comment> comments = commentService.getCommentsByPostId(postId);
		
		List<CommentResponseDto> commentResponseDtos = new ArrayList<>();
		for (Comment comment : comments) {
			commentResponseDtos.add(comment.toResponseDto());
		}
		
		return ResponseEntity.ok(commentResponseDtos);
	}
	
	// 댓글 수정(PUT, PATCH)
	@PutMapping("posts/{postId}/comments/{commentId}")
	public ResponseEntity<String> editComment(
			@PathVariable(name = "postId") Long postId,
			@PathVariable(name = "commentId") Long commentId,
			@RequestBody Comment updateComment) {
		
		updateComment.setId(commentId);
		commentService.editComment(updateComment);
		
		return ResponseEntity.ok("update complete");
	}
	
	// 댓글 삭제(DELETE)
	@DeleteMapping("posts/{postId}/comments/{commentId}")
	public ResponseEntity<String> deleteComment(
			@PathVariable(name = "postId") Long postId,
			@PathVariable(name = "commentId") Long commentId) {
		
		commentService.deleteComment(commentId);
		
		return ResponseEntity.ok("delete complete");
	}
}
















