package com.jaenyeong.springboot_started;

//import com.jaenyeong.springboot_started.auto_configure.Holoman;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @Configuration + @ComponentScan + @EnableAutoConfiguration 과 같음
// JPA 의존성으로 인해 실행 에러 (database setting 문제)
@SpringBootApplication

//@Configuration
//@ComponentScan
//@EnableAutoConfiguration
public class Application {
	static final String SERVLET_NAME = "helloServlet";

	public static void main(String[] args) {
		// @SpringBootApplication 주석처리
		// @Configuration, @ComponentScan 어노테이션 태깅시 실행하면 아래와 같은 에러 발생
		// Unable to start ServletWebServerApplicationContext due to missing ServletWebServerFactory bean.
		SpringApplication.run(Application.class, args);

		// 따라서 WebApplicationType 타입이 아닌 타입으로 실행
//		SpringApplication application = new SpringApplication(Application.class);
//		application.setWebApplicationType(WebApplicationType.NONE);
//		application.run(args);

		// 톰캣
//		tomcat();
	}

	// Runner에 빈 설정과 충돌
	// 스프링 2.1 버전 이후부터 사고 방지를 위하여 기본적으로 빈 오버라이딩 비활성화가 설정되어 있음
//	@Bean
//	public Holoman holoman() {
//		Holoman holoman = new Holoman();
//		holoman.setName("Noah");
//		holoman.setHowLong(60);
//		return holoman;
//	}

	// 톰캣 의존성 삭제시 에러
//	private static void tomcat() throws LifecycleException {
//		Tomcat tomcat = new Tomcat();
//		tomcat.setPort(8888);
//		// 최신 임베디드 톰캣버전은 서버에 자동으로 커넥터를 연결해주지 않는 것으로 보임
//		tomcat.getConnector();
//
////		tomcat.addContext("/", new File());
//		Context context = tomcat.addContext("/", "/");
//
//		HttpServlet servlet = new HttpServlet() {
//			@Override
//			protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
////				super.doGet(req, resp);
//				PrintWriter printWriter = resp.getWriter();
//				printWriter.println("<html><head><title>");
//				printWriter.println("Hey, Tomcat");
//				printWriter.println("</title></head>");
//				printWriter.println("<body><h1>Hello Tomcat</h1></body>");
//				printWriter.println("</html>");
//			}
//		};
//
//		tomcat.addServlet("/", SERVLET_NAME, servlet);
//		context.addServletMappingDecoded("/hello", SERVLET_NAME);
//
//		tomcat.start();
//		tomcat.getServer().await();
//	}
}
