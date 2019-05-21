## Obtaining an Access Token
```
curl -X POST \
  http://localhost:8080/oauth/token \
  -H 'Authorization: Basic Zm9vQ2xpZW50SWRQYXNzd29yZDpzZWNyZXQ=' \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/x-www-form-urlencoded' \
  -d 'grant_type=password&password=123&username=john'
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