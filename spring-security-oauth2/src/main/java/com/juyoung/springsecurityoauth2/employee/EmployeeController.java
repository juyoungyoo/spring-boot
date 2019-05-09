package com.juyoung.springsecurityoauth2.employee;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class EmployeeController {

    private List<Employee> employees = new ArrayList<>();

    @GetMapping("/employee")
    @ResponseBody
    public Optional<Employee> getEmployee(@RequestParam String email) {
        return employees.stream()
                .filter(x -> x.getEmail().equals(email)).findAny();
    }

    @PostMapping("/employee")
    @ResponseStatus(HttpStatus.CREATED)
    public void postMessage(@RequestBody Employee employee) {
        employees.add(employee);
    }
}