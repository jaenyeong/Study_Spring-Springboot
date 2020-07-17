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
    * org.springframework.boot.autoconfigure.EnableAutoConfiguration
      * 하위에 있는 설정(자바) 파일들을 스캔 (각 설정 파일들은 @Configuration 어노테이션이 태깅되어 있음)
    * @Configuration
    * @ConditionalOnXxxYyyZzz
      * 실제로 빈 등록시 이 어노테이션의 설정 값에 따라 등록여부가 달라짐
