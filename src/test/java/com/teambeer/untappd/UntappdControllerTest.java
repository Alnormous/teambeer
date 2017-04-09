package com.teambeer.untappd;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.teambeer.untappd.UntappdController;
import com.teambeer.untappd.UserNotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UntappdControllerTest {

	@Autowired
	private UntappdController untappd;
	
	@Test
	public void testUntappdApi() throws UserNotFoundException {
		Assert.assertEquals(25, untappd.getCheckinsByUser("ipal").size());
	}
}
