package com.example.post.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.post.model.users.User;

public interface UserRepository extends JpaRepository<User, Long> {
	// username 으로 회원정보 조회 -> 쿼리 메소드
	User findByUsername(String username);
	
}











