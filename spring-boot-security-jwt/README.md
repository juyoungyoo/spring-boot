# Spring security5 + OAuth2 + Spring zuul gateway + JWT 
- zuul-gateway : api gateway (port: 8080)
- account-auth : authentication server (port: 8081)
- account-resource : resource server (port: 8082)
- web : front server (port: 8083)       // todo : 추후
 
## 요구사항 및 TODO
- [x] authorization_code grant 토큰 발급
- [x] userDetailService 연결
- [x] jwtTokenStore 연동 
- [x] create, updateDate 추가 ( Auditing )

__Auth server__
- [x] 회원가입 
- [x] 로그인 시 토큰 발급

__Account resource server__
- [x] token 확인은 auth server로 remote하여 확인
- [x] 계정 조회, 수정, 탈퇴 
- [x] 계정정보 조회
- [x] 본인 계정정보 조회

__API GateWay__
- [x] token 검증 및 필터 
- [x] resource routing 하는 라우터 역할

---
### generate JWT public key
[JWT 설정 및 키 생성방법](https://www.baeldung.com/spring-security-oauth-jwt)
Generate JKS Java KeyStore File
```
keytool -genkeypair \ 
        -alias friday \
        -keyalg RSA \ 
        -keypass colini \
        -keystore friday-store.jks \
        -storepass colini_store
```
Export Public Key
```
keytool -list -rfc --keystore friday-store.jks | openssl x509 -inform pem -pubkey
```
---


## Obtaining an Access Token
```
curl -X POST \
  http://localhost:8080/oauth/token \
  -H 'Authorization: Basic Zm9vQ2xpZW50SWRQYXNzd29yZDpzZWNyZXQ=' \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/x-www-form-urlencoded' \
  -d 'grant_type=password&password=123&username=user@gmail.com'
```
__result__
```json
{
    "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJqb2huIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sIm9yZ2FuaXphdGlvbiI6ImpvaG5MYXdLIiwiZXhwIjoxNTU4NDM5NzU4LCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiMjc0MWNiYmMtZDJjOC00MDg1LWExOTUtZjdlZmRiNDFkZWI3IiwiY2xpZW50X2lkIjoibXlBcHAifQ.MTY5cfq-9bDEWTJBpiFqoPm6xhB-uQWCPS9xANREAT8",
    "token_type": "bearer",
    "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJqb2huIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sIm9yZ2FuaXphdGlvbiI6ImpvaG5MYXdLIiwiYXRpIjoiMjc0MWNiYmMtZDJjOC00MDg1LWExOTUtZjdlZmRiNDFkZWI3IiwiZXhwIjoxNTU4NDQyNzU4LCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiZGFhMWQ1ZWItZDJiYi00ODA0LTk0MGYtMGYzZWJhY2VjYTNhIiwiY2xpZW50X2lkIjoibXlBcHAifQ.YQHauzqPcb2ufc5veoIc52L_lUNKKk7CFEMf0EHBPRw",
    "expires_in": 599,
    "scope": "read write",
    "organization": "johnLawK",
    "jti": "2741cbbc-d2c8-4085-a195-f7efdb41deb7"
}
```

## Resource server request
```
curl -X GET \
  http:/localhost:8080/account/users/extra \
  -H 'Accept: application/json, text/plain, */*' \
  -H 'Accept-Encoding: gzip, deflate' \
  -H 'Accept-Language: en-US,en;q=0.9' \
  -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXV...' \
  -H 'Cache-Control: no-cache' \
```
_response_
```
{
    "user_name": "john",
    "scope": [
        "read",
        "write"
    ],
    "organization": "johnsvzB",
    "exp": 1558439214,
    "authorities": [
        "ROLE_USER"
    ],
    "jti": "2141ce95-cc39-4cf9-9121-bdf453d68db5",
    "client_id": "myApp"
}
```