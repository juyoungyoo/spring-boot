# SpringBoot data 연동
application 설정 참고
- dev : mysql 설정
- local : h2 설정


#SpringBoot가 지원하는 in-memory
- H2
- HSQL
- Derby

Spring-JDBC가 클래스패스에 있으면, 자동으로 필요한 빈이 설정된다. (별도의 설정이 필요없다)

##H2 in-memory 설정방법
1. 의존성 추가
- spring jdbc, hikariCP
```
runtimeOnly 'com.h2database:h2'    
implementation 'org.springframework.boot:spring-boot-starter-jdbc'
```
- 가장 핵심이 되는 설정 //  autoconfigure에서 확인가능  
```
org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,
org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration
```
스프링부트는 H2, JDBC 의존성 설정하고, 별도로 DB설정을 하지 않을 경우 스프링부트는 자동으로 인메모리 데이터베이스 설정을 하여 애플리케이션이 동작한다.

## H2 콘솔 사용하는 방법
* spring-boot-devtools를 추가하거나...
* spring.h2.console.enabled=true 만 추가.
* /h2-console로 접속 (이 path도 바꿀 수 있음)

##H2 intellij client database 사용방법
1. 의존성 변경
SpringBoot에서 H2를 띄울 시 기본적으로 JVM 메모리에 띄운다. 그렇게 되면 외부에서 붙을 수 없기 때문에 우회하여 접근하기 위해 H2 library에서 제공하는 TcpServer를 사용한다.
```
compile 'com.h2database:h2'  // runtime > compile 변경
```
2. H2 TCP Server 설정
```java
@Component
public class H2ServerConfiguration {
    @Bean
    public Server h2TcpServer() throws Exception{
        return Server.createTcpServer().start();
    }
}
```
3. application.yml 설정
```yml 
datasource:
    platform: h2
    url: jdbc:h2:tcp://localhost:9092/mem:testdb;MVCC=TRUE 
    username: sa
    password:
    driver-class-name: org.h2.Driver
    // url설정을 위에처럼 안하면? 웹 콘솔로 띄울 JVM내의 H2 DB로 JPA가 접근한다
```

4. intellij database
host : localhost
database : mem
user : sa
password :
URL 접근방법 : REMOTE
URL : jdbc:h2:tcp://localhost:9092/mem:testdb 

---

#MySQL 설정
1. 의존성 추가
```
    implementation 'org.springframework.boot:spring-boot-starter-jdbc'
    compile 'org.springframework.boot:spring-boot-starter-web'
    runtimeOnly 'mysql:mysql-connector-java'
```
2. Docker로 mysql 실행
```bash
$ docker run -p 3306:3306 --name mysql -e MYSQL_ROOT_PASSWORD=pass -e MYSQL_DATABASE=colini -e MYSQL_USER=friday -e MYSQL_PASSWORD=pass -d mysql 
$ docker exec -i -t mysql_boot bash 
$ mysql -u root -p
```
3. application.yml 설정
```
spring.datasource.url=jdbc:mysql://localhost:3306/springboot
spring.datasource.username=keesun 
spring.datasource.password=pass
```

