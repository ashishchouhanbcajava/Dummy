package com.Dummy.Dummy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Dummy.Dummy.Repository.UsersRepository;
import com.Dummy.Dummy.beans.Users;

@Service
public class UsersService implements UserDetailsService {

	@Autowired
	private UsersRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		System.out.println("i m in userDetails Service .......");
		return repository.findByUsername(username);
	}

	public Users save(Users users) {
		return repository.save(users);
	}

	public List<Users> getAll() {
		return repository.findAll();
	}
}
