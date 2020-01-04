package com.otakuma.utils;

import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.otakuma.config.UtilsConfig;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = UtilsConfig.class)
public class ParseTest {

	@Autowired
	Parse parse;

	@Test
	public void testStringToDouble() {
		assertEquals(parse.stringToDouble(null), new Double(0));
		assertEquals(parse.stringToDouble(""), new Double(0));
		assertEquals(parse.stringToDouble("-.55"), new Double(-0.55));
		assertEquals(parse.stringToDouble(null, null), null);
		assertEquals(parse.stringToDouble("Diamond Square", -1.1111d), new Double(-1.1111d));
	}

	@Test
	public void testParseIntegerString() {
		assertEquals(parse.stringToInteger(null), new Integer(0));
		assertEquals(parse.stringToInteger("Voronoi Map"), new Integer(0));
		assertEquals(parse.stringToInteger("-911"), new Integer(-911));
		assertEquals(parse.stringToInteger(null, null), null);
		assertEquals(parse.stringToInteger("Yes Roundabout", -1), new Integer(-1));
	}

	@Test
	public void testStringToDate() {

		assertEquals(parse.stringToDate("♦♣♠♥◘•◘•◘•◘♦♣♠♥"), null);

		Calendar cal = Calendar.getInstance();
		cal.set(1520, 11, 30);
		assertEquals(parse.stringToDate("30/12/1520").toString(), new Date(cal.getTime().getTime()).toString());
		cal.set(2000, 0, 1);
		assertEquals(parse.stringToDate("1/1/2000").toString(), new Date(cal.getTime().getTime()).toString());
	}

	@Test
	public void testDateToStringDate() {
		Date d = null;
		assertEquals(parse.dateToString(d), null);
		Calendar cal = Calendar.getInstance();
		cal.set(1520, 11, 30, 5, 59, 59);
		assertEquals(parse.dateToString(new Date(cal.getTime().getTime())).toString(), "30/12/1520 05:59:59");
	}

	@Test
	public void testDateToStringTimestamp() {
		Timestamp d = null;
		assertEquals(parse.timeStampToString(d), null);
		Calendar cal = Calendar.getInstance();
		cal.set(1520, 11, 30, 5, 59, 59);
		assertEquals(parse.timeStampToString(new Timestamp(cal.getTime().getTime())).toString(), "30/12/1520 05:59:59");
	}

	@Test
	public void testImageToThumbnail() {
		//null
		assertEquals(parse.imageToThumbnail(null), null);
		assertEquals(parse.imageToThumbnail("https://images2.imgbox.com/77/99/PAxv9jsE_o.jpg"),
			"https://thumbs2.imgbox.com/77/99/PAxv9jsE_t.jpg");
	}

	@Test
	public void testThumbnailToImage() {
		assertEquals(parse.thumbnailToImage(null), null);
		assertEquals(parse.thumbnailToImage("https://thumbs2.imgbox.com/77/99/PAxv9jsE_t.jpg"),
				"https://images2.imgbox.com/77/99/PAxv9jsE_o.jpg");
	}

}
