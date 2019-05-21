package com.juyoung.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class FooClientController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/foos/{id}")
    public String getFooResource(@PathVariable long id, Model model) {
        Foo foo = restTemplate.getForEntity("http://localhost:8082/account/foos/" + id, Foo.class).getBody();
        model.addAttribute("foo", foo);
        return "foo";
    }

    @PostMapping("/foos")
    public String addNewFoo(Foo foo, Model model) {
        Foo created = restTemplate.postForEntity("http://localhost:8082/account/foos/", foo, Foo.class).getBody();
        model.addAttribute("foo", created);
        return "foo";
    }

}