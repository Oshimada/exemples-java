package com.otakuma.services.admin;

import javax.validation.ValidationException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.otakuma.data.admin.AdminLogin;
import com.otakuma.repositories.admin.AdminLoginRepository;
import com.otakuma.services.BaseService;

@Service
public class AdminLoginService extends BaseService<AdminLogin, AdminLoginRepository>{

	@Override
	protected boolean valider(AdminLogin t) throws ValidationException {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void encryptPasswords() {
		Iterable<AdminLogin> logins = repo.findAll();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		for(AdminLogin login : logins) {
			String encoded = encoder.encode(login.getPassword());
			login.setPassword(encoded);
			repo.save(login);
		}
	}

	@Override
	protected Class<AdminLogin> tableClass() {
		return AdminLogin.class;
	}
}
