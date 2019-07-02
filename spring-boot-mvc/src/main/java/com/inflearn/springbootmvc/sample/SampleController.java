package com.inflearn.springbootmvc.sample;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SampleController {

    @GetMapping("/sample")
    public void error() {
        throw new SampleException();
    }

    @ExceptionHandler(SampleException.class)
    public @ResponseBody
    AppError sampleError(SampleException e) {
        AppError appError = new AppError();
        appError.setMessage("error.app.key");
        appError.setReason("REASON!!!");
        return appError;
    }


    @GetMapping("/sample/success")
    public String sample(Model model) {
        model.addAttribute("name", "juju");
        return "sample";
    }
}
