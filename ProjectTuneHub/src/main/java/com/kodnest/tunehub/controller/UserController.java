	package com.kodnest.tunehub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kodnest.tunehub.entity.Song;
import com.kodnest.tunehub.entity.User;
import com.kodnest.tunehub.service.SongService;
import com.kodnest.tunehub.serviceimpl.UserServiceImpl;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

	@Autowired
	UserServiceImpl userServiceImpl;
	
	@Autowired
	SongService songService;

	@PostMapping("/register")
	public String addUser(@ModelAttribute User user) {

		//Email taken fron registration form
		String email = user.getEmail();
		
		//Fetching email from database
		boolean status = userServiceImpl.emailExists(email);

		if(status == false) {
			userServiceImpl.addUser(user);
			System.out.println("User is added");																																																																																																																																																																																
		}else {
			System.out.println("User already exists");
		}
		return "login";
	}

	@PostMapping("/validate")
	public String validate(@RequestParam("email") String email,
			@RequestParam("password") String password, HttpSession session, Model model) {
		if(userServiceImpl.validateUser(email, password) ==true) {
			String role = userServiceImpl.getRole(email);
			session.setAttribute("email", email);
			if(role.equals("admin")) {

				return "adminhome";
			}else {
				User user = userServiceImpl.getUser(email);
				boolean userstatus = user.isPremium();
				List<Song> fetchAllSongs = songService.fetchAllSongs();
				model.addAttribute("songs", fetchAllSongs);
				model.addAttribute("ispremium", userstatus);
				return "customerhome";
			}				
		}else {
			return "login";
			
		}
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "login";
		
	}
	
}
	
