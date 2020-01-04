package com.otakuma.services.admin;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ValidationException;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.otakuma.data.admin.Admin;
import com.otakuma.data.admin.AdminDroit;
import com.otakuma.data.admin.AdminRole;
import com.otakuma.repositories.admin.AdminRepository;
import com.otakuma.services.BaseService;

@Service
public class AdminService extends BaseService<Admin, AdminRepository> {

	
	@Override
	protected boolean valider(Admin t) throws ValidationException {
		// TODO Auto-generated method stub
		return false;
	}

	@Cacheable(value = "roles", key = "#id", condition = "#id!=null")
	public HashSet<SimpleGrantedAuthority> getAuthoritiesById(Long id) {
		AdminRole role = repo.getRoleById(id);
		if(role == null || v.isNullOrEmpty(role.getDroits()))
			return null;
		HashSet<Long> dump = new HashSet<>();
		HashSet<SimpleGrantedAuthority> set = new HashSet<>();

		/** filtrer les branches de droits pour avoir des valeurs distinctes */
		filterDroits(set,dump,role.getDroits());
		
		return set;
	
	}

		

	private void filterDroits(HashSet<SimpleGrantedAuthority> set, HashSet<Long> dump,
			Set<AdminDroit> droitsfils) {
		
		for(AdminDroit droit : droitsfils)
		{
			if(dump.contains(droit.getId()))
				continue;
			dump.add(droit.getId());
			set.add(new SimpleGrantedAuthority(droit.getCode()));
			filterDroits(set, dump, droit.getDroitsfils());
		}
	}

	@Override
	protected Class<Admin> tableClass() {
		return Admin.class;
	}

}
