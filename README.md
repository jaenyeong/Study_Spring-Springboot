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

#### 독립적으로 가능한 JAR
* 배포하여 실행하기 위해 JAR 생성
  * JAR 파일 하나로 실행 가능
    * ``` java -jar springboot-started-1.0.0.jar ``` 명령어 실행 (java -jar 파일명)
  * HTTPS 적용시 생성한 keystore.p12 파일 위치 확인
    * 리소스 경로 안에 위치 시킨 후 application.properties(yaml) 설정 파일에서 경로 설정
  * spring-maven-plugin이 해줌
    * ``` mvn package ``` CLI 명령어로 JAR 생성
  * 과거 “uber” jar 를 사용
    * 모든 클래스 (의존성 및 애플리케이션)를 하나로 압축하는 방법
    * 단점
      * 의존성의 출처 등이 불분명, 파악하기 어려움
      * 각각의 다른 파일이 동일한 파일명인 경우 사용하기 어려움
  * Gradle (build/libs 경로안에 생성)
    * IDEA에서 gradle clean, build 하면 jar 파일 생성
    * ``` gradle clean build ``` 명령어 실행
  * JAR 파일 압축 해제
    * ``` unzip -q springboot-started-1.0.0.jar ``` 명령어 실행 (unzip -q 파일명)
    * 추출 데이터
      * BOOT-INF
        * 직접 작성한 자바 소스의 클래스 파일
        * 의존 라이브러리
      * META-INF
        * MANIFEST.MF
      * org/springframework/boot/loader

* 스프링 부트 전략
  * 내장 JAR
    * 기본적으로 자바에는 내장 JAR를 로딩하는 표준적인 방법이 없음
  * 애플리케이션 클래스와 라이브러리 위치 구분
  * org.springframework.boot.loader.jar.JarFile을 사용해 내장 JAR를 읽음
  * org.springframework.boot.loader.Launcher를 사용해 실행

* MANIFEST.MF
  * 모든 JAR 파일의 시작점은 매니페스트의 메인 클래스
  * JAR 파일의 META-INF 디렉토리 내에 위치
  * JAR 패키징을 할 때 같이 만들어줌

### 스프링 부트 활용
* 핵심 기능
  * SpringApplication
  * 외부 설정
  * 프로파일
  * 로깅
  * 테스트
  * Spring-Dev-Tools

* 각종 기술 연동
  * 스프링 웹 MVC
  * 스프링 데이터
  * 스프링 시큐리티
  * REST API 클라이언트
  * 그 외 기타

#### SpringApplication
* main() 메서드에서 시작된 Spring application을 부트스트랩하는 편리한 기능 제공
  * 부트스트랩은 한 번 시작하면 알아서 진행되는 일련의 과정
* ``` static SpringApplication.run ``` 메서드에게 위임
* 정적 메서드에게 위임하지 않고 커스터마이징 할 경우 객체 생성하여 사용
  * ```
    SpringApplication application = new SpringApplication(Application.class);
    application.run(args);
    ```
* SpringApplicationBuilder로 빌더 패턴 사용 가능
  * ```
    new SpringApplicationBuilder()
            .sources(Application.class)
            .run(args);
    ```

* 디버그 모드 실행 (택일)
  * VM options 설정
    * -Ddebug
  * Program arguments 설정
    * --debug

* FailureAnalyzers
  * 에러 출력시 깔끔하게 해주는 기능

* 배너
  * banner.txt 파일 생성 (gif, jpg, png 등 확장자 사용가능)
    * resources 밑에 위치
      * 다른 곳에 위치할 경우 application.properties(yaml) 파일에서 설정
        * spring.banner.location = classpath:banner.txt
    * 배너 파일에서 ${spring-boot.version} 등의 변수를 사용할 수 있음
      * 매니페스트 파일이 존재 해야만 사용 가능한 변수도 있음
        * ${application.version}
        * ${application.formatted-version}
        * 기타
  * 배너 소스 구현
    * Banner 클래스 구현하고 SpringApplication.setBanner()로 설정 가능
    * ```
      SpringApplication application = new SpringApplication(Application.class);
    
      // 배너 커스터마이징
      application.setBanner((environment, sourceClass, out) -> {
          out.println("-------------------");
          out.println("Jaenyeong");
          out.println("-------------------");
      });
      application.run(args);
      ```
  * 배너 끄는 방법
    * ```
      SpringApplication application = new SpringApplication(Application.class);
      // 배너 Off
      application.setBannerMode(Banner.Mode.OFF);
      application.run(args);
      ```
  * 배너 파일과 소스로 배너 삽입시 [배너 파일]이 출력됨

* ApplicationEvent 등록
  * 스프링 부트에서 제공하는 수 많은 이벤트가 존재
    * 앱이 실행될 때 또는 구동이 완료 되었을 때, 실패 했을 때
    * applicationContext가 생성되거나 갱신 되었을 때
  * ApplicationContext를 만들기 전에 사용하는 리스너는 @Bean(@Component)으로 등록할 수 없음
    * applicationContext가 생성된 후 발생하는 이벤트에 대한 리스너는 실행됨
    * applicationContext가 생성되기 이전에 발생된 이벤트의 리스너는 빈으로 등록해도 실행되지 않음
  * ``` SpringApplication.addListeners(); ```

* WebApplicationType 설정 (None, Servlet, Reactive)
  * 타입 종류
    * ``` WebApplicationType.NONE ```
    * ``` WebApplicationType.SERVLET ```
    * ``` WebApplicationType.REACTIVE ```
  * Servlet, Reactive 중 존재하고 있는것이 기본값이며 아무것도 없는 경우 None
  * Servlet, Reactive 두가지 다 존재하는 경우 Servlet이 기본값
  * 따라서 원하는 타입으로 지정할 때 아래와 같이 설정
    * ```
      SpringApplication application = new SpringApplication(Application.class);
  	  application.setWebApplicationType(WebApplicationType.REACTIVE);
      application.run(args);
      ```

* Application Argument 사용
  * ``` --debug ```와 같이 --로 시작하는 것
    * -D로 시작하는 것은 VM options
  * ApplicationArguments를 빈으로 등록해 주니까 가져다 쓰면 됨
    * VM options 무시
    * 예제 (IDEA 실행 설정)
      * VM options : ``` -Dfoo ```
      * Program Arguments : ``` --bar ```
    * 예제 (Jar CLI 실행)
      * ``` java -jar springboot-started-1.0.0.jar -Dfoo --bar ```

* Runner
  * 앱 실행한 뒤 뭔가 실행하고 싶을 때
    * ApplicationRunner 구현한 runner 작성 (추천)
    * CommandLineRunner 구현한 runner 작성 
  * @Order 어노테이션으로 순서 지정 가능

#### 외부 설정
* 사용 가능한 외부 설정
  * application.properties(yaml)
  * 환경 변수
  * 커맨드 라인 아규먼트

* 프로퍼티 우선 순위 확인 예제 설정
  * 테스트 시 설정
    * ``` import org.springframework.core.env.Environment; ``` 스프링 패키지 참조해야 함
    * 참조
      * src/main 아래에 있는 파일들이 클래스패스에 들어간 후 테스트에 있는 파일들이 클래스패스에 들어감 (덮어써짐)
        * 프로덕션과 테스트 코드의 application.properties(yaml) 파일이 다른 경우
          * 내용 유무나 값에 따라 실패할 수 있음
          * 테스트만 실행시 테스트 경로의 application.properties(yaml) 파일로 덮어써지기 때문
          
* 프로퍼티 우선 순위
  1. 유저 홈 디렉토리에 있는 spring-boot-dev-tools.properties(yaml)
  2. 테스트에 있는 @TestPropertySource
     * ``` @TestPropertySource(properties = "jaenyeong.name=jaenyeong2") ```
     * @TestPropertySource 어노테이션의 locations 속성은 yaml 확장자 파일을 지원하지 않음
       * ``` @TestPropertySource(locations = "classpath:/addJaenyeong.properties") ```
       * ``` @TestPropertySource(properties = {"spring.config.location = classpath:/addJaenyeong2.yaml"}) ```
         * yaml properties 속성 사용
       * locations 속성으로 yaml 파일 지정시 별도 설정 필요
  3. @SpringBootTest 어노테이션의 properties 애트리뷰트
     * ``` @SpringBootTest(properties = "jaenyeong.name=jaenyeong3") ```
  4. CLI Arguments (커맨드 라인 아규먼트)
     * ``` java -jar springboot-started-1.0.0.jar --jaenyeong.name=jaenyeong ``` 
  5. SPRING_APPLICATION_JSON (환경 변수 또는 시스템 프로퍼티)에 들어있는 프로퍼티
     * ``` java -Dspring.application.json='{"name" : "jaenyeong"}' -jar springboot-started-1.0.0.jar ```
     * ``` java -jar springboot-started-1.0.0.jar --spring.application.json='{"name" : "jaenyeong"}' ```
     * java:comp/env/spring.application.json
  6. ServletConfig 파라미터
  7. ServletContext 파라미터
  8. java:comp/env JNDI 애트리뷰트
  9. System.getProperties() 자바 시스템 프로퍼티
  10. OS 환경 변수
  11. RandomValuePropertySource
  12. JAR 밖에 있는 특정 프로파일용 application properties(yaml)
  13. JAR 안에 있는 특정 프로파일용 application properties(yaml)
  14. JAR 밖에 있는 application properties(yaml)
  15. JAR 안에 있는 application properties(yaml)
      * ``` jaenyeong.name = jaenyeong ```
  16. @PropertySource
  17. 기본 프로퍼티 (SpringApplication.setDefaultProperties)

* application.properties 우선 순위 (높은 순위가 낮은 순위를 덮어씀)
  1. file:./config/
     * 우선순위 제일 높음
     * 프로젝트 루트(또는 JAR 파일 실행 위치)에 config 디렉토리 생성, 그 안에 설정 파일을 위치 시킨 경우
  2. file:./
     * 프로젝트 루트(또는 JAR 파일 실행 위치)에 설정 파일을 위치 시킨 경우
  3. classpath:/config/
     * 클래스패스에 config 디렉토리 생성, 그 안에 설정 파일을 위치 시킨 경우
  4. classpath:/
     * 우선순위 제일 낮음
     * 클래스패스에 설정 파일을 위치 시킨 경우

* CLI 프로퍼티 disable 설정
  * ``` SpringApplication.setAddCommandLineProperties(false);```

* 랜덤값 설정
  * 랜덤 변수 사용시 파라미터 사이에 공백 주의
  * ${random.int(1,100)}
  * ${random.*}

* 플레이스 홀더 (application.properties(yaml) 파일 내 변수 재사용)
  * name = jaenyeong
  * fullName = ${name} kim

* Type-Safe Property (타입-세이프 프로퍼티)
  * 빈으로 등록하여 사용하는 방법
    * 빈으로 등록해서 다른 빈에 주입할 수 있음
      * @EnableConfigurationProperties
      * @Component
      * @Bean
    * @ConfigurationProperties
      * 프로퍼티로 사용할 클래스 선언 후 어노테이션 태깅 (값을 바인딩 받을 수 있는 상태)
      * 사용할 수는 없는 상태 (빈 등록 필요)
        * 메인 클래스에 태깅
          * ``` @EnableConfigurationProperties(JaenyeongProperties.class) ```
        * 프로퍼티용 클래스에 ``` @Component ``` 어노테이션 태깅하여 빈으로 등록
  * 여러 프로퍼티를 묶어서 읽어올 수 있음
  * Relaxed Binding (간편한 바인딩)
    * context-path (케밥)
    * context_path (언드스코어)
    * contextPath (카멜)
    * CONTEXTPATH
  * 프로퍼티 타입 컨버전
    * @DurationUnit
      * ```
        @DurationUnit(ChronoUnit.SECONDS)
        private Duration sessionTimeout = Duration.ofSeconds(30);
        ```
      * @DurationUnit 어노테이션을 사용하지 않아도 프로퍼티 값 suffix가 s라면 바인딩 됨
  * 프로퍼티 값 검증
    * @Validated (프로퍼티 클래스에 태깅)
      * @NotEmtpy (필드에 태깅)
      * hibernate-validator 의존성 추가
        * ``` implementation group: 'org.hibernate.validator', name: 'hibernate-validator' ```
    * JSR-303 (@NotNull, ...)
  * 메타 정보 생성
  * @Value
    * SpEL 을 사용할 수 있음
    * 위에 있는 기능들은 전부 사용 못함

#### 프로파일
* 프로파일 어노테이션
  * @Configuration
  * @Component

* 프로파일 활성화
  * spring.profiles.active=test
  * CLI
    * CLI 프로퍼티가 우선 순위가 높기 때문에 프로퍼티 파일에 test로 설정되어 있어도 prod로 실행됨
    * ``` java -jar springboot-started-1.0.0.jar --spring.profiles.active=prod ```
  * IDEA
    * Run/Debug Configurations에 Program arguments 옵션에 ``` --spring.profiles.active=prod ``` 설정
* 프로파일 추가
  * spring.profiles.include=proddb
    * application-proddb.properties 파일 생성
    * 추가 프로파일 프로퍼티 설정
* 프로파일용 프로퍼티 생성
  * application-{profile}.properties
    * application-prod.properties, application-test.properties 파일 생성
  * 프로파일용 프로퍼티 파일이 기본 프로퍼티 파일보다 우선 순위가 높음

#### 로깅
* 스프링 부트 기본 로거 설정
  * 로깅 퍼사드 VS 로거
    * 로깅 퍼사드
      * Commons Logging, SLF4j(Simple Logging Facade for Java)
      * 실제 로거 구현체가 아닌 로깅 기능에 대해서 추상화한 인터페이스
      * Commons Logging 문제가 많았음
        * 런타임시 클래스 로딩과 관련된 이슈
        * 메모리 릭 등
        * 대체제로 SLF4j 등장
        * 하지만 스프링 프레임워크 초창기부터 Commons Logging을 사용하였기 때문에 현재까지도 사용 중
        * 이를 해결하기 위해 스프링에 Commons Logging 의존성을 제거하고 SLF4j를 사용하는 등 설정이 복잡했음
        * 스프링 5부터는 이러한 설정을 하지 않아도 내부적으로 JCL이라는 모듈을 사용하여 SLF4j를 사용
          * 궁극적으로 Logback을 사용하게 됨
    * 실제 로거 구현체
      * JUL (Java Utility Logging), Log4J2, Logback
        * Logback은 SLF4j의 구현체
        * JUL을 사용하는 부분은 SLF4j로 위임
  * Spring-JCL (Jakarta)
    * Commons Logging -> SLF4j or Log4J2
    * pom.xml에 exclusion 안해도 됨 (의존성 제거를 직접 하지 않아도 된다는 것을 의미)
  * 스프링 부트 로깅
    * 기본 포맷
    * --debug
      * 일부 핵심 라이브러리만 디버깅 모드로
    * --trace
      * 전부 다 디버깅 모드로
    * 컬러 출력 (application.properties(yaml) 파일 설정)
      * spring.output.ansi.enabled=true
    * 파일 출력 (application.properties(yaml) 파일 설정) (spring.log 파일)
      * logging.file 설정
        * 파일 지정
      * logging.path (Deprecated)
        * 디렉토리 설정
        * logging.file.path으로 대체
      * 둘다 지정시 logging.file 설정 사용
    * 로그 레벨 조정 (application.properties(yaml) 파일 설정)
      * logging.level.패키지 = 로그 레벨
      
* 로깅 파일
  * 기본적으로 10MB마다 롤링
    * Logging.file.max-size 속성으로 크기제한 변경 가능
    * logging.file.max-history 속성 설정
      * 설정하지 않으면 기본적으로 지난 7일까지 롤링 파일 유지
      * max-history 설정값 이후 데이터 아카이빙

* 백업
  * 원본 데이터 손실을 대비하기 위해 데이터의 사본을 저장하는 것 또는 그 사본 데이터
* 아카이브
  * 참고용으로 사본 데이터를 생성하는 것 또는 그 사본 데이터

* 커스터마이징
  * 커스텀 로그 설정 파일 사용
    * Logback
      * logback.xml
      * logback-spring.xml (사용 권장)
        * 스프링 부트가 추가 기능 제공
        * profile, environment variable 등 사용 가능
        * logback.xml은 너무 일찍 로딩되기 때문에 위와 같은 기능들을 사용 못함
        * ```
          <?xml version="1.0" encoding="UTF-8"?>
          <configuration>
              <include resource="org/springframework/boot/logging/logback/defaults.xml"/>
              <include resource="org/springframework/boot/logging/logback/console-appender.xml" />
              <root level="INFO">
                  <appender-ref ref="CONSOLE" />
              </root>
          <!--    <logger name="org.springframework.web" level="DEBUG"/>-->
              <logger name="com.jaenyeong" level="DEBUG"/>
          </configuration>
          ```
    * Log4J2
      * log4j2-spring.xml
      * log4j2.xml
      * log42j-spring.yaml (properties)
      * log42j.yaml (properties)
    * JUL (권장하지 않음)
      * logging.properties
    * Logback extension
      * 프로파일 ``` <springProfile name="프로파일"> ```
      * Environment 프로퍼티 ``` <springProperty> ```

* log4j2 설정
  * 의존성 설정
    * ```
      implementation('org.springframework.boot:spring-boot-starter-web') {
          // loggig 제거
          exclude module: "spring-boot-starter-logging"
      }
      // log4j2으로 변경
      implementation group: 'org.springframework.boot', name: 'spring-boot-starter-log4j2', version: '2.3.1.RELEASE'
      ```
  * log4j2-spring.yaml 파일 생성
    * 로그파일 설정 내용 작성
  * application.properties(yaml) 파일 설정
    * logging.config = classpath:log4j2-spring.yaml

#### 테스트
* spring-boot-starter-test 추가
  * log4j2 사용시 spring-boot-starter-test에 SLF4j 의존성 제거
    * ``` exclude module: "spring-boot-starter-logging" ```

* @SpringBootTest
  * @RunWith(SpringRunner.class)랑 같이 써야 함
  * 서블릿 컨테이너를 mock up 해서 mocking된 디스패처 서블릿이 로딩  
    진짜 디스패처 서블릿에게 요청을 보내는 것을 흉내냄 (mocking된 디스패처 서블릿에게 요청)  
    이 때 반드시 MockMvc 클라이언트를 사용해야 함 이를 위해 @AutoConfigureMockMvc 어노테이션 태깅  
    ``` @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK) ```  
    ``` @AutoConfigureMockMvc ```  
  * 빈 설정 파일은 자동으로 찾아줌 (@SpringBootApplication)
    *  @SpringBootApplication을 찾아가 하위 디렉토리까지 존재하는 모든 빈 스캔, 등록
    * @MockBean 어노테이션 태깅한 객체만 목으로 교체ㄴ
  * WebEnvironment
    * MOCK
      * mock servlet environment. 내장 톰캣 구동 안 함
      * ``` @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK ```
    * RANDON_PORT, DEFINED_PORT
      * 실제 내장 톰캣 사용
      * RestTemplate, TestRestTemplate, WebClient, WebTestClient 등을 사용해야 함
      * 랜덤 포트로 테스트시 기존 application.properties(yaml) 파일(테스트 경로)에 서버 설정 주석처리 필요
      * ``` @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) ```
    * NONE
      * 서블릿 환경 제공 안 함
  * 웹 요청 객체
    * MockMvc (Server side test)
      * 테스트 클래스에 ``` @AutoConfigureMockMvc ``` 어노테이션 태깅
      * ```
        @Autowired
        MockMvc mockMvc;
        ```
    * TestRestTemplate (Rest Client side test)
      * ```
        @Autowired
        TestRestTemplate testRestTemplate;
        ```
    * WebTestClient (스프링5 웹플럭스에 추가됨)
      * 비동기 요청으로 테스트
      * WebFlux 의존성 추가
        * ```
          implementation('org.springframework.boot:spring-boot-starter-webflux') {
              exclude module: 'spring-boot-starter-logging'
          }
          ```

* @MockBean
  * ApplicationContext에 들어있는 빈을 Mock으로 만든 객체로 교체
  * 모든 @Test 마다 자동으로 리셋
  * ```
    @MockBean
    SampleService mockSampleService;
    ```

* 슬라이스 테스트
  * 레이어 별로 잘라서 테스트할 때 
  * @JsonTest
    * Json 테스트 용도
  * @WebMvcTest
    * 컨트롤러 관련 빈만 등록됨
      * @Controller
      * @ControllerAdvice
      * @JsonComponent
      * Converter, GenericConverter, Filter, WebMvcConfigurer, HandlerMethodArgumentResolver
    * 해당 어노테이션은 내부 객체조차 주입되지 않기 때문에 내부 객체 사용시 주입 필요
      * @Service, @Repository와 같은 일반적인 빈은 등록되지 않음
  * @WebFluxTest
  * @DataJpaTest
    * @Repository만 빈 등록
  * 기타

* 테스트 유틸
  * OutputCaptureRule
    * 로그 포함 콘솔에 찍힌 모든 데이터를 캡처
    * public으로 선언할 것
    * ```
      @Rule
      public OutputCaptureRule outputCaptureRule;
      ```
  * TestPropertyValues
  * TestRestTemplate
  * ConfigFileApplicationContextInitializer
