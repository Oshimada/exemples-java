package com.otakuma.controllers;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import com.otakuma.Application;
import com.otakuma.controllers.produit.ProduitsController;


@RunWith(SpringRunner.class)
@SpringBootTest(classes=Application.class)
public class ProduitsControllerTest {

	@Autowired
	ProduitsController controller;
	
	@Test
	@WithMockUser(username = "limouna@gmail.com", authorities = { "CLT_SELECT" })
	public void testDebug() {
		controller.debug();
	}

	@Test
	public void testList() {
		fail("Not yet implemented");
	}

	@Test
	public void testPing() {
		fail("Not yet implemented");
	}

	@Test
	public void testInfos() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetByCode() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetById() {
		fail("Not yet implemented");
	}

	@Test
	public void testAdd() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdate() {
		fail("Not yet implemented");
	}

	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}

}
