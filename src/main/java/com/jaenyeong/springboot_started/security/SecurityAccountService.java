package com.jaenyeong.springboot_started.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
public class SecurityAccountService implements UserDetailsService {

	@Autowired
	private SecurityAccountRepository accountRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public SecurityAccount createAccount(String userName, String password) {
		SecurityAccount account = new SecurityAccount();
		account.setUserName(userName);
//		account.setPassword(password);
		account.setPassword(passwordEncoder.encode(password));
		return accountRepository.save(account);
	}

	// UserDetailsService의 메서드 오버라이딩
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<SecurityAccount> byUserName = accountRepository.findByUserName(username);
		SecurityAccount securityAccount = byUserName.orElseThrow(() -> new UsernameNotFoundException(username));
		return new User(securityAccount.getUserName(), securityAccount.getPassword(), authorities());
	}

	private Collection<? extends GrantedAuthority> authorities() {
		return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
	}
}
