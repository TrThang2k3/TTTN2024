package com.poly.util.service.implement;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.poly.entity.Account;
import com.poly.service.AccountService;
import com.poly.service.AuthorityService;

import jakarta.servlet.http.HttpSession;

@Service
public class UserServiceImplement implements UserDetailsService {
	@Autowired
	AccountService accountService;
	@Autowired
	AuthorityService authorityService;
	@Autowired
	HttpSession session;

	@Autowired
	BCryptPasswordEncoder pe;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		try {
			Account account = accountService.findByEmail(email);
			// Tạo UserDetails từ Account
			String password = account.getPassword();
			String[] roles = authorityService.findByAccount(account).stream().map(au -> au.getRole().getName())
					.collect(Collectors.toList()).toArray(new String[0]);
			//
			Map<String, Object> authentication = new HashMap<>();
			authentication.put("account", account);
			byte[] token = (email + ":" + account.getPassword()).getBytes();
			authentication.put("token", "Basic " + Base64.getEncoder().encodeToString(token));
			session.setAttribute("authentication", authentication);
			return User.withUsername(email).password(password).roles(roles).build();
		} catch (Exception e) {
			throw new UsernameNotFoundException(email + " not found.");
		}
	}

}
