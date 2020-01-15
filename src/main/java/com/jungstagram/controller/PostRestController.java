package com.jungstagram.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jungstagram.domain.Post;
import com.jungstagram.dto.PostDto;
import com.jungstagram.service.FollowService;
import com.jungstagram.service.PostService;
import com.jungstagram.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class PostRestController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private PostService postService;
	@Autowired
	private FollowService followService;
	
	@GetMapping("/post/feed")
	public List<Object> getFollowPost(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute("userId");
		List<Object> result = new ArrayList<Object>();
		result.add(userService.findUserById(userId).getId());
		result.add(postService.findFolloweePostListByUserId(userId));
		result.add(userService.findFolloweeListByUserId(userId));
		return result;
	}

	@GetMapping("/post")
	public List<Object> getPostList(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute("userId");
		List<Object> result = new ArrayList<Object>();
		result.add(userService.findUserById(userId).getId());
		result.add(postService.findPostList(0));
		result.add(userService.findFolloweeListByUserId(userId));
		return result;
	}

	@PostMapping(value = "/post", produces = "application/json; charset=utf-8")
	public boolean savePost(@RequestBody PostDto dto, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute("userId");
		postService.savePost(dto, userId);
		return true;
	}

	@PutMapping("/post")
	public boolean updatePost(@RequestBody PostDto postDto, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute("userId");
		postService.updatePost(postDto, userId);
		return true;
	}

	@GetMapping("/post/my")
	public List<Object> getMyPost(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute("userId");
		List<Object> result = new ArrayList<Object>();
		result.add(userService.findUserById(userId).getId());
		result.add(postService.findPostListByUserId(userId));
		result.add(userService.findFolloweeListByUserId(userId));
		return result;
	}

	@GetMapping("/post/{postId}")
	public Post getPost(@PathVariable("postId") Long postId) {
		return postService.findPostById(postId);
	}

	@DeleteMapping("/post/{postId}")
	public boolean deletePost(@PathVariable("postId") Long postId) {
		postService.deletePostById(postId);
		return true;
	}

	@GetMapping("/getmorelist")
	public List<Object> getMorePostList(@RequestParam("numberOfRequests") int numberOfRequest, HttpServletRequest request){
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute("userId");
		List<Object> result = new ArrayList<Object>();
		result.add(userService.findUserById(userId).getId());
		result.add(postService.findPostList(numberOfRequest + 1));
		result.add(userService.findFolloweeListByUserId(userId));
		return result;
	}
}