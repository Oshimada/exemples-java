package com.otakuma.services.admin;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.otakuma.Application;
import com.otakuma.data.admin.Token;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=Application.class)
public class TokenServiceTest {

	
	@Autowired
	TokenService service;
	
	@Test
	public void testValiderToken() {
		fail("Not yet implemented");
	}

	@Test
	public void getTokenTest() {
		Token token = service.getToken("6HJp7sxk85u6l96w5pOiKg58vsoumR0n2AweNf2x0Ejx4p06PryHV1oHbrSGFlvW");
	
		assertNotNull(token);
	}
}
