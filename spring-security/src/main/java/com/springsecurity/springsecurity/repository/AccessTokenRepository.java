package com.springsecurity.springsecurity.repository;

import com.springsecurity.springsecurity.security.AccessToken;
import org.springframework.data.repository.CrudRepository;


public interface AccessTokenRepository extends CrudRepository<AccessToken, Long> {
}
