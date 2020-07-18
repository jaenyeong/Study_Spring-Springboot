# Study_Spring-Springboot
### 인프런 스프링 부트 개념과 활용 (백기선)
https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8/dashboard
-----

## [Settings]
#### Project Name
* Study_Springboot-started
#### java
* zulu jdk 11
#### gradle
* IDEA gradle wrapper
#### Spring boot
* 2.3.1

### 스프링 부트 원리

#### Spring-boot-starter-parent
* 설정 (메이븐)
  * Parent 태그
    * 운영 프로젝트와 겹칠경우 운영 프로젝트의 parent로 등록하여 사용할 것
    * dependencyManagement태그에 비해 의존성 버전 관리 뿐 아니라 다양한 설정이 미리 되어 있음
      * application 설정 파일의 확장자(리소스 필터링)
      * 기본 자바 버전
      * 인코딩 타입
      * 플러그인 설정
      * 기타
  * dependencyManagement 태그
    * Parent 태그를 사용하지 못할경우 사용하지만 Parent 태그처럼 다양한 설정이 되어 있지 않음
    * 가급적 Parent 태그를 사용하는 것을 추천

#### 의존성 추가
* 스프링 부트가 버전을 관리하지 않는 모듈의 경우 버전을 직접 명시
  * ``` implementation group: 'org.modelmapper', name: 'modelmapper', version: '2.3.8' ```
* 스프링 부트가 버전을 관리하지만 직접 사용 버전을 명시하는 경우
  * Maven
    * properties 태그 선언, 모듈버전 태그 안에 모듈 버전 명시
  * Gradle
    * ```
      implementation group: 'org.springframework', name: 'spring-core', version: '5.2.6.RELEASE'
      ```

#### 자동 설정
* @EnableAutoConfiguration
  * @SpringBootApplication 안에 숨어 있음

* 빈은 2단계로 나뉘어 스캔 (빈을 2번에 걸쳐 등록함)
  * 1단계 : @ComponentScan
  * 2단계 : @EnableAutoConfiguration

* @ComponentScan (아래 어노테이션을 가진 클래스를 스캔, 빈으로 등록)
  * @Component
  * @Configuration @Repository @Service @Controller @RestController
* @EnableAutoConfiguration (Auto Configure - 메타 파일 스캔)
  * spring.factories
    * ```
      org.springframework.boot.autoconfigure.EnableAutoConfiguration\
      FQCN, \
      FQCN
      ```
      * 하위에 있는 설정(자바) 파일들을 스캔 (각 설정 파일들은 @Configuration 어노테이션이 태깅되어 있음)
    * @Configuration
    * @ConditionalOnXxxYyyZzz
      * 실제로 빈 등록시 이 어노테이션의 설정 값에 따라 등록여부가 달라짐

#### 자동 설정 생성
* Xxx-Spring-Boot-Autoconfigure
  * 자동 설정
* Xxx-Spring-Boot-Starter 모듈
  * 필요한 의존성 정의
  * 하나로 만들 때 사용

* 설정 방법
  * 의존성 추가 (기존 스프링 관련 의존성 제거)
    * spring-boot-autoconfigure
      ```
      implementation group: 'org.springframework.boot', name: 'spring-boot-autoconfigure', version: '2.3.1.RELEASE'
      ```
    * spring-boot-autoconfigure-processor
      ```
      optional group: 'org.springframework.boot', name: 'spring-boot-autoconfigure-processor', version: '2.3.1.RELEASE'
      ```
      * 아래 설정 추가
        ```
        configurations {
            optional
            compile.extendsFrom optional
        }
        ```
    * spring-boot-dependencies
      ```
      compileOnly group: 'org.springframework.boot', name: 'spring-boot-dependencies', version: '2.3.1.RELEASE', ext: 'pom'
      ```
  * @Configuration 파일 작성
  * src/main/resource/META-INF 경로에 spring.factories 파일 작성
    * 스프링 파일
    * 서비스 프로파이더 패턴과 유사함
  * spring.factories 안에 자동 설정 파일 추가
  * 로컬 저장소에 배포
    * Build.Gradle 설정
      * Maven publish 플러그인 추가
        * ``` id 'maven-publish' ```
        * 퍼블리싱 설정
          ```
          publishing {
              publications {
                  // 해당 정보를 입력하여 타 프로젝트에서 해당 라이브러리를 참조
                  maven(MavenPublication) {
                      groupId = 'com.jaenyeong.springboot_started.auto_configure' // groupId 추가
                      artifactId = 'noah-artifact'  // artifactId 추가
                      version = '1.0.0' // 버전 정보 추가
                      from components.java
                  }
              }
          }
          ``` 
      * 빌드 스크립트 추가
        * ```
          buildscript {
              repositories {
                  mavenLocal()
                  mavenCentral()
              }
          }
          ```
      * 레퍼지토리 설정
        * ```
          repositories {
              maven {
                  url '/Users/kimjaenyeong/.m2/repository'
              }
              mavenLocal()
              jcenter()
          }
          ```
      * 자바독, 소스파일 추가 생성 (안해도 상관 없음)
        * ```
          java {
              withJavadocJar()
              withSourcesJar()
          }
          ```
    * 명령어 실행 (mvn install)
      * ``` gradle publishToMavenLocal ```
      * ``` ./gradlew build publishToMavenLocal ```
    * IDEA > publishing > publishToMavenLocal, publishMavenPublicationToMavenLocal
  * 의존성 추가
    * implementation group: 'com.jaenyeong.springboot_started.auto_configure', name: 'noah-artifact', version: '1.0.0'

* 빈 덮어써지는 것을 방지
  * 스프링 2.1 버전 이후부터 사고 방지를 위하여 기본적으로 빈 오버라이딩 비활성화가 설정되어 있음
    * application.properties(yaml) 파일에 설정 추가
      * spring.main.allow-bean-definition-overriding = true
  * 해당 빈에 @ConditionalOnMissingBean 어노테이션 태깅
* 빈 재정의
  * @ConfigurationProperties(“holoman”)
  * @EnableConfigurationProperties(HolomanProperties)
  * 프로퍼티 키값 자동 완성
  * Holoman properties 설정 파일에 어노테이션 사용을 위하여 의존성 추가
    * ``` annotationProcessor "org.springframework.boot:spring-boot-configuration-processor" ```

#### 내장 웹 서버
* 스프링 부트는 서버가 아님
  * 톰캣 객체 생성
  * 포트 설정
  * 톰캣에 컨텍스트 추가
  * 서블릿 생성
  * 톰캣에 서블릿 추가
  * 컨텍스트에 서블릿 맵핑
  * 톰캣 실행 및 대기

* spring-boot-autoconfigure 라이브러리 안에 META-INF/spring.factories 설정 파일

* 스프링 부트의 자동설정이 위 항목들을 설정, 실행
  * ServletWebServerFactoryAutoConfiguration
    * 내장 서블릿 웹 서버를 생성 및 설정
      * TomcatServletWebServerFactory 사용해 톰캣 설정 및 생성
    * TomcatServletWebServerFactoryCustomizer (서버 커스터마이징)
  * DispatcherServletAutoConfiguration
    * 디스패처 서블릿 생성, 등록
    * 서블릿 컨테이너(톰캣, 제티 등)는 달라질 수 있지만 서블릿은 변하지 않기 때문에 분리되어 있음

* 톰캣 프로세스 확인 명령어
  * ``` ps ax | grep tomcat ```

* 9버전 이상 톰캣 사용시 주의
  * 최신 임베디드 톰캣버전은 서버에 자동으로 커넥터를 연결해주지 않기 때문에 아래와 같이 커넥터를 직접 연결
    * ``` tomcat.getConnector(); ```

#### 서블릿 컨테이너, 포트
* 다른 서블릿 컨테이너로 변경
  * spring-boot-starter-web에서 톰캣 의존성 제거
    * ```
      implementation('org.springframework.boot:spring-boot-starter-web') {
          exclude group: 'org.springframework.boot', module: 'spring-boot-starter-tomcat'
      }
      ```

* 웹서버 사용하지 않기
  * application.properties(yaml) 파일에서 웹앱 타입 설정
    * spring.main.web-application-type = none
      * ``` application.setWebApplicationType(WebApplicationType.NONE); ``` 와 동일
    * 위와 같이 설정시 서블릿 컨테이너가 의존성(클래스패스 등)이 있더라도 None WebApp으로 실행되어 바로 종료됨

* 서버 포트 설정
  * 포트 변경
    * application.properties(yaml) 파일에서 포트 설정
      * server.port = 8888
  * 무작위 포트번호 사용
    * server.port = 0
  * ``` ApplicationListner<ServletWebServerInitializedEvent> ```

* 네티를 이용해 만든 언더토어(Undertow)
  * ```
    implementation group: 'org.springframework.boot', name: 'spring-boot-starter-undertow', version: '2.3.1.RELEASE'
    ```

#### HTTPS & HTTP2
* 대칭키 방식
  * 암호화와 복호화를 하나의 키로 처리
  * 키 노출시 위험 (대칭키 전송 방법이 어려움)
* 비대칭키 방식 (공개키 개인키)
  * 쌍을 이루는 키를 생성하여 암호화를 한 키로는 복호화할 수 없음
  * 일반적으로 공개키를 전송하여 공개키로 암호화된 데이터를 수신하여 개인키로 복호화
  * 반대로 개인키로 암호화하여 공개키와 암호화된 데이터를 전송하는 경우
    * 데이터 전송자의 신원보증을 의미 (두 키가 쌍을 이루기 때문에)

* CA (Certificate Authority) 인증 기관
  * 해당 회사(서비스 등)을 확인하여 인증(보증)해주는 기관
  * 암호학에서 인증 기관은 다른 곳에서 사용하기 위한 디지털 인증서를 발급하는 하나의 단위
  * 브라우저들은 CA 목록을 미리 알고 있음

* SSL 인증서
  * 기능(목적)
    * 클라이언트가 접속한 서버가 신뢰할 수 있는 서버인지 보장 (신원보증)
    * SSL 통신에 사용할 공개키를 클라이언트에게 제공
  * 포함된 정보
    * 서비스의 정보(인증서를 발급한 CA, 서비스의 도메인 등과 같은 정보)
    * 서버사이드 공개키 (공개키의 내용과 암호화 방법)
  * 인증서 발급
    * CA로부터 구입
      * 기업 정보(서비스 도메인 등)에 대한 서류 등을 제출하며 사용할 공개키를 같이 전달
    * CA는 자신의 개인키(비공개키)를 사용해 발급할 인증서를 암호화하여 제공
  * 서비스를 보증하는 방법
    1) 웹 브라우저(클라이언트)가 해당 서버 서비스에 접속할 때 서버는 자신의 인증서를 제공
       * 이때 인증서는 CA가 개인키(비공개키)로 암호화
    2) 웹 브라우저는 인증서의 CA 정보가 자신이 가진 CA 목록에 있는지 확인
    3) 인증서의 CA가 목록에 있는 경우 CA의 공개키를 사용하여 서버의 인증서를 복호화
       * 복호화할 수 있다는 것은 서버의 인증서가 CA의 개인키로 암호화되어 발급됐다는 것을 의미 (서버의 서비스를 신뢰할 수 있음)

* SSL & TLS 동작 방법
  * 클라이언트와 서버는 서로 자신의 공개키를 전송하여 데이터 전송시 상대에 공개키를 통해 암호화하여 전송
    * 이 방식은 암, 복호화 과정에서 많은 리소스를 잡아 먹기 때문에(성능 이슈) 사용하고 있지 않음
  * 암호화된 데이터를 전송하기 위해 공개키 타입과 대칭키 타입을 혼합해 사용
    * 실제 데이터는 대칭키를 통해 암호화
    * 위에서 사용된 대칭키를 상대에 공개키로 암호화
  * 컴퓨터(호스트) 간 네트워크 통신시 내부 절차
    1) 핸드셰이크
       * 데이터 전송 전 상대에 대한 정보 획득
       * 인증서(공개키) 송수신
       * 절차
         1) Client Hello 단계 (클라이언트가 서버에 접속)
            * 이 단계에서 주고 받는 정보
              * 클라이언트에서 생성한 랜덤 데이터
                * 서버의 랜덤 데이터와 조합하여 pre master secret 키를 생성하는데 사용
              * 클라이언트가 지원하는 암호화 방식
                * 사용 가능한 암호화 방식이 다를 수 있기 때문
              * 세션 아이디
                * 기존에 핸드셰이킹을 했다면 해당 세션 아이디를 재사용할 수 있게됨 (핸드셰이킹 비용, 시간 절감)
                * 이 때 사용되는 세션 아이디 (식별자)를 서버로 전송
         2) Server Hello 단계 (클라이언트 Client Hello에 대한 응답)
            * 이 단계에서 주고 받는 정보
              * 서버에서 생성한 랜덤 데이터
                * 클라이언트의 랜덤 데이터와 조합하여 pre master secret 키를 생성하는데 사용
              * 클라이언트가 보낸 암호화 방식 중에서 선택한 암호화 방식
              * 인증서
         3) 클라이언트가 서버의 인증서 확인
            * 클라이언트는 받은 인증서의 CA가 자신이 알고 있는 CA 목록에 있는지 확인
              * 목록에 없다면 경고 메세지 출력
            * 클라이언트는 내장된 CA 공개키를 통해 서버의 인증서를 복호화
            * 클라이언트가 만든 랜덤 데이터와 서버가 보낸 랜덤 데이터를 조합하여 pre master secret(대칭키) 이라는 키를 생성
              * pre master secret은 노출되면 안됨
            * 서버에서 제공한 인증서의 공개키를 이용하여 pre master secret를 암호화 후 서버에게 전송
         4) 서버는 클라이언트가 보낸 pre master secret을 자신의 개인키(비공개키)로 복호화
            * 서버와 클라이언트는 모든 일련의 과정을 통해 pre master secret 값으로 master secret 값을 생성
            * master secret은 세션키를 생성
            * 서버와 클라이언트는 이 세션키를 이용해 데이터를 대칭키 방식으로 암호화하여 통신
         5) 핸드셰이킹 종료
    2) 세션
       * 실제로 서버와 클라이언트가 데이터를 송수신하는 단계
       * 세션키를 통해 대칭키 방식으로 주고 받을 데이터를 암호화
    3) 세션 종료
       * 데이터 전송이 끝나면 SSL 통신 종료를 서로에게 알림
       * 통신에 사용한 세션키(대칭키)를 폐기

* HTTP (HyperText Transfer Protocol)
  * www(웹)에서 정보를 주고 받는 프로토콜
  * 일반적으로 HTML(hypertext)을 송수신하는데 사용
  * TCP, UDP를 사용
* HTTPS 
  * HTTP + Over Secure Socket Layer
* HTTP2
  * HTTP의 2번째 버전 (HTTP 1.1의 차기 버전)
  * 개선
    * Head of line blocking(HOL)
      * 여러 파일을 한꺼번에 병렬로 전송하여 로딩 시간 줄임
      * HTTP 1.1전까지는 한 번에 한 파일만 전송 가능
    * 중복헤더 제거
      * 같은 내용의 헤더를 보낼경우 생략처리하여 속도 개선
    * 헤더 압축 (Header compression)
      * 평문이었던 HTTP 헤더와 달리 일종의 컴파일을 거쳐 용량 대비 처리 효율성 개선
      * 컴파일을 거치기 때문에 헤더 크기 자체도 줄어듦
    * 서버 푸시 (Server push)
      * 특정 파일을 서버에 지정, HTTP 전송시 같이 밀어 넣는 방식 (JS, CSS, 이미지 등)
    * 우선순위 (Prioritization)
      * 웹 페이지를 구성하는 파일 요소의 우선순위 부여
  * SPDY 기반
    * 구글이 개발한 비표준 개방형 네트워크 프로토콜
    * 압축, 다중화, 우선순위 설정을 통한 레이턴시 감소를 달성
    * 웹 페이지 부하 레이턴시를 줄이고 웹 보안을 개선하는 점에서 HTTP와 비슷

* HTTPS 설정
  * keytool
    * Manages a keystore (database) of cryptographic keys, X.509 certificate chains, and trusted certificates
    * 공개키/개인키 쌍을 생성하여 keystore(일종의 데이터베이스)에 저장하는 자바 CLI 툴 (Java SDK와 같이 배포됨)
    * 공개키/개인키 쌍과 인증서, keystore를 관리하는 보안 관련 유틸리티 프로그램
  * keystore
    * 암호화된 키, 인증서를 저장하는 스토리지
    * 키 엔트리 (key entry)
      * 비밀키와 공개키와 관련된 인증서 체인으로 구성
    * 공인인증서 엔트리 (trusted certificate entry)
      * 신뢰할 수 있는 기관을 나타내는 공개키 인증서
  * keytool 명령어 실행
    * ```
      keytool -genkey -alias spring -storetype PKCS12 -keyalg RSA \
      -keysize 2048 -keystore keystore.p12 -validity 4000
      ```
      * -genkey
        * 공개키 개인키 쌍 생성
        * 지정한 키스토어가 없는 파일이라면 파일을 새로 생성
      * -alias
        * keystore 별칭
      * -storetype
        * keystore가 저장되는 파일 형식 (기본값은 JKS)
      * -keyalg
        * 키를 생성하는데 사용되는 알고리즘
      * -keysize
        * 생성할 키의 비트단위 크기
      * -keystore
        * 생성된 키쌍을 저장할 keystore 파일 (없는 경우 새로 생성됨)
      * -validity
        * 유효기간
    * 저장소 비밀번호 및 기타 정보 설정
      * 비밀번호 : 123456
      * 기타 단체 및 지역, 국가코드 : None, Seoul, ko
  * application.properties(yaml) 설정
    * server.ssl.key-store: keystore.p12
    * server.ssl.key-store-password: 123456
    * server.ssl.keyStoreType: PKCS12
    * server.ssl.keyAlias: spring
  * 설정시 톰캣 사용
    * 언더토우 사용하면 실행되지 않음 (다른 설정이 더 필요한듯 보임)
  * 현재 브라우저가 모르는 인증서(CA를 모름)이기 때문에 접속시 경고 출력
  * 터미널 명령 실행
    * ``` curl -I --http2 http://localhost:8080/hello ```
      * 400 반환
        * ```
          HTTP/1.1 400
          Content-Type: text/plain;charset=UTF-8
          Connection: close
          ```
    * ``` curl -I -k --http2 https://localhost:8080/hello ```
      * -k 옵션 추가전 인증서 문제 경고 반환
        * ```
          curl: (60) SSL certificate problem: self signed certificate
          More details here: https://curl.haxx.se/docs/sslcerts.html
          
          curl failed to verify the legitimacy of the server and therefore could not
          establish a secure connection to it. To learn more about this situation and
          how to fix it, please visit the web page mentioned above.
          ```
      * -k 옵션 추가 후 200 반환
        * ```
          HTTP/1.1 200
          Content-Type: text/plain;charset=UTF-8
          Content-Length: 12
          Date: Sat, 18 Jul 2020 12:48:32 GMT
          ```
    * HTTP/2 요청임에도 불구하고 HTTP/1.1 반환

* HTTP 커넥터
  * HTTPS를 적용하고 나면 더이상 HTTP를 사용하지 못함
    * HTTP 커넥터는 1개이기 때문
  * 코딩 설정
    * 스프링 부트는 application.properties(yaml) 파일을 통한 HTTP(S) 커넥터 설정을 지원하지 않기 때문에 코딩으로 설정해야 함
    * ```
      @Bean
      public ServletWebServerFactory servletContainer() {
          TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
          tomcat.addAdditionalTomcatConnectors(createStandardConnector());
          return tomcat;
      }
     
      private Connector createStandardConnector() {
          Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
          // 커넥터가 사용할 포트 설정
          connector.setPort(8080);
          return connector;
      }
      ```
    * http
      * ``` curl -I --http2 http://localhost:8080/hello ```
      * 200 반환
        * ```
          HTTP/1.1 200
          Content-Type: text/plain;charset=UTF-8
          Content-Length: 12
          Date: Sat, 18 Jul 2020 13:07:52 GMT
          ```
    * https
      * ``` curl -I -k --http2 https://localhost:8443/hello ```
      * 200 반환
        * ```
          HTTP/1.1 200
          Content-Type: text/plain;charset=UTF-8
          Content-Length: 12
          Date: Sat, 18 Jul 2020 13:07:52 GMT
          ```

* HTTP2 설정
  * HTTP2는 HTTPS가 적용되어 있어야 함
  * application.properties(yaml) 파일 설정
    * server.http2.enable = true
  * 사용하는 서블릿 컨테이너마다 제약사항(조건) 다름
    * undertow
      * server.http2.enabled = true (HTTPS만 적용되어 있으면 아무런 추가 설정 필요 없음)
    * tomcat
      * 8버전은 설정이 복잡하여 생략
        * libtcnative 라이브러리 필요
      * 9버전(Tomcat 9.0x + JDK 9)
        * server.http2.enabled = true (HTTPS만 적용되어 있으면 아무런 추가 설정 필요 없음)
      * HTTP2로 200 반환
       * ```
         HTTP/2 200
         content-type: text/plain;charset=UTF-8
         content-length: 12
         date: Sat, 18 Jul 2020 13:22:20 GMT
         ```
