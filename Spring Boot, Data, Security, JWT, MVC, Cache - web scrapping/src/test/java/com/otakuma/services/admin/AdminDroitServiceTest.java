package com.otakuma.services.admin;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.otakuma.Application;
import com.otakuma.data.admin.AdminDroit;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=Application.class)
public class AdminDroitServiceTest {

    @Autowired
    AdminDroitService service;

    @Test
    public void shouldGetAdminDroitService() {
    	assertNotNull(service);
    	assertTrue(service.check());
    }
    
	@Test
	public void testGetById() {
		
		AdminDroit addr = service.getById(25l);
		assertNotNull(addr);
		System.out.println(addr);
	}

	@Test
	public void testValiderAdminDroit() {
		fail("Not yet implemented");
	}

}
