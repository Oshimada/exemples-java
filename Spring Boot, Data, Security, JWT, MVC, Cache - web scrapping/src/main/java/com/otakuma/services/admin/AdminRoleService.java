package com.otakuma.services.admin;

import javax.validation.ValidationException;

import org.springframework.stereotype.Service;

import com.otakuma.data.admin.AdminRole;
import com.otakuma.repositories.admin.AdminRoleRepository;
import com.otakuma.services.BaseService;

@Service
public class AdminRoleService extends BaseService<AdminRole, AdminRoleRepository> {


	@Override
	protected boolean valider(AdminRole t) throws ValidationException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected Class<AdminRole> tableClass() {
		return AdminRole.class;
	}
}
