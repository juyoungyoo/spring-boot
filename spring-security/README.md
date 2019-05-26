__[ 요구사항 ]__
1. 회원가입하면 Jwt token을 발급한다
2. 로그인 : 로그인 성공 시 jwt token 발급하며 token을 redis에 저장한다
이 후, 리소스 접근 시 토큰 인증을 한다.

__[ 개발 환경 ]__
- spring embedded-redis 0.7 ( test )
- spring data redis
- spring data jpa
- spring boot starter web
- spring security 
- jjwt 
- H2
- MySQL 

 __[ 참고 ]__
 - [svlada / springboot-security-jwt](https://github.com/svlada/springboot-security-jwt/blob/master/src/main/java/com/svlada/security/auth/jwt/extractor/JwtHeaderTokenExtractor.java)
 - [ heowc / SpringBootSample](https://github.com/heowc/SpringBootSample/blob/master/SpringBootSecurityJwt/src/main/java/com/tistory/heowc/auth/ajax/filter/AjaxAuthenticationFilter.java)