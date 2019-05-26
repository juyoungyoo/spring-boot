package com.springsecurity.springsecurity.security;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

@RedisHash("accessToken")
@EqualsAndHashCode
public final class AccessToken implements Serializable {

    @Id
    private long id;
    private String rawToken;
    private LocalDateTime refreshTime;

    @Builder
    public AccessToken(long id,
                       String rawToken,
                       LocalDateTime refreshTime) {
        this.id = id;
        this.rawToken = rawToken;
        this.refreshTime = refreshTime;
    }

    public void refresh(String rawToken, LocalDateTime refreshTime){
        if(refreshTime.isAfter(this.refreshTime)){
            this.rawToken = rawToken;
            this.refreshTime = refreshTime;
        }
    }

    public boolean verify(String inputToken){
        return rawToken.equals(inputToken);
    }

    public long getId() {
        return id;
    }

    public String getRawToken() {
        return rawToken;
    }

    public LocalDateTime getRefreshTime() {
        return refreshTime;
    }
}
