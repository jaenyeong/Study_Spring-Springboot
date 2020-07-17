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
