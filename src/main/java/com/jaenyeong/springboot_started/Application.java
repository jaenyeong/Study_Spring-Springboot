package com.jaenyeong.springboot_started;

//import com.jaenyeong.springboot_started.auto_configure.Holoman;

//import org.apache.catalina.connector.Connector;

//import org.springframework.boot.Banner;

import com.jaenyeong.springboot_started.auto_configure.HolomanProperties;
import com.jaenyeong.springboot_started.mongo_db.Account;
import com.jaenyeong.springboot_started.mongo_db.MongoAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

// 톰캣 사용시
//import org.apache.catalina.connector.Connector;
//import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
//import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
//import org.springframework.context.annotation.Bean;

//import org.springframework.boot.builder.SpringApplicationBuilder;

//import static org.springframework.boot.Banner.Mode.*;

//import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
//import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
//import org.springframework.context.annotation.Bean;

// @Configuration + @ComponentScan + @EnableAutoConfiguration 과 같음
// JPA 의존성으로 인해 실행 에러 (database setting 문제)
@SpringBootApplication

//@Configuration
//@ComponentScan
//@EnableAutoConfiguration

// 프로퍼티 설정 (빈 등록, @ConfigurationProperties 어노테이션 처리)
// 아래 어노테이션은 자동으로 되어 있음
//@EnableConfigurationProperties(JaenyeongProperties.class)
@EnableConfigurationProperties(HolomanProperties.class)
public class Application {
	static final String SERVLET_NAME = "helloServlet";

	@Autowired
	MongoTemplate mongoTemplate;

	@Autowired
	MongoAccountRepository mongoAccountRepository;

	@Bean
	public ApplicationRunner applicationRunner() {
		return args -> {
			Account account = new Account();
//			account.setEmail("jaenyeong.dev@gmail.com");
//			account.setUserName("jaenyeong");
//
//			mongoTemplate.insert(account);

			account.setEmail("test@gmail.com");
			account.setUserName("TEST");

			mongoAccountRepository.insert(account);

			// 성공여부 확인
			System.out.println("MongoDB finished");
		};
	}

	// 외부, 서드파티에 프로퍼티 설정이 있는 경우
//	@ConfigurationProperties("server")
//	@Bean
//	public ServerProperties serverProperties() {
//		return new ServerProperties();
//	}

	public static void main(String[] args) {
		// @SpringBootApplication 주석처리
		// @Configuration, @ComponentScan 어노테이션 태깅시 실행하면 아래와 같은 에러 발생
		// Unable to start ServletWebServerApplicationContext due to missing ServletWebServerFactory bean.
//		SpringApplication.run(Application.class, args);

		// 따라서 WebApplicationType 타입이 아닌 타입으로 실행
//		SpringApplication application = new SpringApplication(Application.class);
//		application.setWebApplicationType(WebApplicationType.NONE);
//		application.run(args);

		// 톰캣
//		tomcat();

		// SpringApplication.run(Application.class, args) 메서드를 사용하는 것보다 커스터마이징 할 수 있음
//		SpringApplication application = new SpringApplication(Application.class);
//
//		// 배너 설정
////		bannerSet(application);
//		application.run(args);

		// SpringApplicationBuilder 사용
//		new SpringApplicationBuilder()
//				.sources(Application.class)
//				.run(args);

		// ApplicationContext 생성되기 전에 실행되는 이벤트의 리스너 설정
//		setEventListenerBeforeApplicationContext();

		// App 타입 설정 None, Servlet, Reactive
		// Servlet, Reactive 중 존재하고 있는것이 기본값이며 아무것도 없는 경우 None
		// Servlet, Reactive 두가지 다 존재하는 경우 Servlet이 기본값
		// 따라서 원하는 타입으로 지정할 때 아래와 같이 설정
		setWebApplicationType(args);
	}

	// 앱 타입 설정
	private static void setWebApplicationType(String[] args) {
		SpringApplication application = new SpringApplication(Application.class);
		application.setWebApplicationType(WebApplicationType.SERVLET);
//		application.setWebApplicationType(WebApplicationType.NONE);
//		application.setWebApplicationType(WebApplicationType.REACTIVE);
		application.run(args);
	}

	// ApplicationContext 생성되기 전에 실행되는 이벤트의 리스너 설정
//	private static void setEventListenerBeforeApplicationContext() {
//		SpringApplication application = new SpringApplication(Application.class);
//		application.addListeners(new SampleListener());
//		application.run(args);
//	}

	// 배너 설정
//	private static void bannerSet(SpringApplication application) {
//		// 배너 커스터마이징
//		application.setBanner((environment, sourceClass, out) -> {
//			out.println("-------------------");
//			out.println("Jaenyeong");
//			out.println("-------------------");
//		});
//
//		// 배너 Off
//		application.setBannerMode(Banner.Mode.OFF);
//	}

	// 톰캣 사용시 HTTP 사용할 포트 설정
//	@Bean
//	public ServletWebServerFactory servletContainer() {
//		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
//		tomcat.addAdditionalTomcatConnectors(createStandardConnector());
//		return tomcat;
//	}
//
//	private Connector createStandardConnector() {
//		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
//		// 커넥터가 사용할 포트 설정
//		connector.setPort(8080);
//		return connector;
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

	// Runner에 빈 설정과 충돌
	// 스프링 2.1 버전 이후부터 사고 방지를 위하여 기본적으로 빈 오버라이딩 비활성화가 설정되어 있음
//	@Bean
//	public Holoman holoman() {
//		Holoman holoman = new Holoman();
//		holoman.setName("Noah");
//		holoman.setHowLong(60);
//		return holoman;
//	}
}
