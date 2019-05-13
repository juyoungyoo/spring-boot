//package com.security.auth.config.rest;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
//
//import javax.sql.DataSource;
//
////@Configuration
////@EnableAuthorizationServer
//public class RestAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
//
//    @Autowired
//    TokenStore tokenStore;
//    @Autowired
//    private AuthenticationManager authenticationManager;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//    @Autowired
//    private DataSource dataSource;
//
//    // OAuth2 인증서버 자체의  보안 정보를 설정하는 부분
//    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
//        oauthServer
//                .tokenKeyAccess("permitAll()")
//                .checkTokenAccess("isAuthenticated()")
//                .passwordEncoder(passwordEncoder)
//        ;
//    }
//
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients)
//            throws Exception {
//        // Client 에 대한 정보를  설정하는 부분
////        clients.jdbc(dataSource)
////                .withClient()
//        clients.inMemory()
//                .withClient("juyoung-client")
//                .secret(passwordEncoder.encode("juyoung-password"))     // todo : There is no PasswordEncoder mapped for the id "null" : encode 필수
//                .authorizedGrantTypes("password",
//                        "refresh_token")
//                .scopes("read", "write", "trust")
//                .and()
//                .withClient("clientIdPassword")
//                .secret("{noop}secret")
//                .authorizedGrantTypes(
//                        "password", "authorization_code", "refresh_token")
//                .scopes("read");
//    }
//
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
//        // OAuth2 서버가 작동하기 위한 Endpoint에 대한 정보를 설정
//        endpoints.tokenStore(tokenStore).authenticationManager(authenticationManager);
//    }
//
//}