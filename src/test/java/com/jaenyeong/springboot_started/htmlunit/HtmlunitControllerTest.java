package com.jaenyeong.springboot_started.htmlunit;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlHeading1;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@WebMvcTest(HtmlunitController.class)
public class HtmlunitControllerTest {

	// WebClient가 MockMvc를 사용
	@Autowired
	WebClient webClient;

	@Test
	public void hello() throws Exception {
		HtmlPage page = webClient.getPage("/htmlunit/hello");
		HtmlHeading1 h1 = page.getFirstByXPath("//h1");

		assertThat(h1.getTextContent()).isEqualToIgnoringCase("jaenyeong");
	}
}
