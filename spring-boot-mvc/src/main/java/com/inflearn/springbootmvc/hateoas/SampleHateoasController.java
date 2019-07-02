package com.inflearn.springbootmvc.hateoas;

import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/hateoas")
public class SampleHateoasController {

    @CrossOrigin(origins = {"http://localhost:8081"})
    @GetMapping
    public Resource<Hello> sample() {
        Hello hello = new Hello();
        hello.setPrefix("Hey, ");
        hello.setName("juju");

        Resource<Hello> helloResource = new Resource<>(hello);
        helloResource.add(linkTo(methodOn(SampleHateoasController.class).sample()).withSelfRel());

        return helloResource;
    }
}
