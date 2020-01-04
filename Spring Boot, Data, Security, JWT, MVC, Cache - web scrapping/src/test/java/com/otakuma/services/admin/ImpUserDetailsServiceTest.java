package com.otakuma.services.admin;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.otakuma.Application;
import com.otakuma.data.admin.ImpUserDetails;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=Application.class)
public class ImpUserDetailsServiceTest {


    @Autowired
    ImpUserDetailsService service;

    @Test
    public void shouldGetImpUserDetailsServiceService() {
    	assertNotNull(service);
    }
    
	@Test
	public void testLoadUserByUsername() {
		ImpUserDetails details = (ImpUserDetails) service.loadUserByUsername("limouna@gmail.com");
		System.err.println(details);
	}

}
