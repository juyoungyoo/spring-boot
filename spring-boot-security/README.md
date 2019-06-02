# Spring security
### 종류
- Web security
- Method security

### 인증 방법
- LDAP
- form 
- Basic
- Oauth
...  

### Spring boot security 자동 설정
- SecurityAutoConfiguration
- UserDetailsServiceAutoConfiguration
인증 관련 각종 이벤트 발생 (DefaultAuthenticationEventPublisher 참고)

`spring-boot-starter-security`
- spring security5.x 특징
- 모든 요청에 인증이 필요하다
- 기본 사용자 생성
```
Username: user
Password: 애플리케이션을 실행할 때 마다 랜덤 값 생성 (콘솔에 출력 됨.)
```
기본 생성자 커스텀하고 싶다면? property에 설정
```
spring.security.user.name
spring.security.user.password
```

[스프링 부트 시큐리티 테스트](https://docs.spring.io/spring-security/site/docs/current/reference/html/test-method.html)

###참고 
백기선 - 스프링 부트 개념과 활용