package com.otakuma.repositories;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.otakuma.Application;
import com.otakuma.data.admin.AdminRole;
import com.otakuma.repositories.admin.AdminRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=Application.class)
public class AdminRepositoryTest {

	@Autowired
	AdminRepository repo;
	
	@Test
	public void shouldGetAdminRepository() {
		assertNotNull(repo);
	}
	@Test
	public void testFindRoleById() {
		AdminRole role = repo.getRoleById(3l);
		assertNotNull(role);
	}

}
