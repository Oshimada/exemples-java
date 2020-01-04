package com.otakuma.services.admin;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit4.SpringRunner;

import com.otakuma.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=Application.class)
public class AdminServiceTest {


    @Autowired
    AdminService service;

    @Test
    public void shouldGetAdminService() {
    	assertNotNull(service);
    	assertTrue(service.check());
    }
    
	@Test
	public void testValiderAdmin() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAuthoritiesById() {
		HashSet<SimpleGrantedAuthority> auths = service.getAuthoritiesById(1l);
		System.err.println(auths.size());
		System.err.println(auths);
	}

}
