package com.techelevator.tenmo.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.model.*;
@RestController
@RequestMapping(path="users")
public class UserController {

		@Autowired
		AccountDAO dao;
		@Autowired
		UserDAO userDAO;
		
		@GetMapping("")
		public List<User> findAllUsers()
		{
			List<User> user = userDAO.findAllUsers();
			return user;
		}

	}

