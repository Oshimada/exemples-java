/**
 * 
 */
package com.otakuma.config;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.otakuma.utils.Parse;
import com.otakuma.utils.Validateur;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = UtilsConfig.class)
public class UtilsConfigTest {

    @Autowired
    private ApplicationContext context;
	/**
	 * Test method for {@link com.otakuma.config.UtilsConfig#createValidateur()}.
	 */
	@Test
	public void testCreateValidateur() {
        Validateur validateur = context.getBean(Validateur.class);
        assertNotNull(validateur);
	}

	/**
	 * Test method for {@link com.otakuma.config.UtilsConfig#createParse()}.
	 */
	@Test
	public void testCreateParse() {
        Parse parse = context.getBean(Parse.class);
        assertNotNull(parse);
	}

}
