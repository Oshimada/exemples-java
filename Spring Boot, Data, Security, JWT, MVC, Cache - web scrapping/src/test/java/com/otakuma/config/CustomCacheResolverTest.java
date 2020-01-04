/**
 * 
 */
package com.otakuma.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CustomCacheResolver.class)
public class CustomCacheResolverTest {

	/**
	 * Test method for {@link com.otakuma.config.CustomCacheResolver#CustomCacheResolver(org.springframework.cache.CacheManager)}.
	 */
	@Test
	public void testCustomCacheResolver() {
		// fail("Not yet implemented");
	}
}
