package com.otakuma.services.admin;

import javax.validation.ValidationException;

import org.springframework.stereotype.Service;

import com.otakuma.data.admin.AdminDroit;
import com.otakuma.repositories.admin.AdminDroitRepository;
import com.otakuma.services.BaseService;

@Service
public class AdminDroitService extends BaseService<AdminDroit, AdminDroitRepository> {

	public AdminDroit getById(Long id) {
		try {
			return repo.findById(id).get();
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	@Override
	protected boolean valider(AdminDroit t) throws ValidationException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected Class<AdminDroit> tableClass() {
		return AdminDroit.class;
	}

}
