package com.springsecurity.springsecurity.security.jwt;


import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

public class SkipPathRequestMatcher implements RequestMatcher {

    private OrRequestMatcher skipRequestMatcher;

    public SkipPathRequestMatcher(List<String> skipPathList) {
        skipRequestMatcher = pathsToOrRequestMatcher(skipPathList);
    }

    private OrRequestMatcher pathsToOrRequestMatcher(List<String> paths) {
        List<RequestMatcher> requestMatcherList = paths.stream()
                .map(AntPathRequestMatcher::new)
                .collect(Collectors.toList());
        return new OrRequestMatcher(requestMatcherList);
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        return !skipRequestMatcher.matches(request);
    }
}