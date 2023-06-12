package com.example.block6personcontrollers;

import com.example.block6personcontrollers.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/controlador")
public class BeanController {
    @Autowired
    @Qualifier("bean1")
    Person personBean1;

    @Autowired
    @Qualifier("bean2")
    Person personBean2;

    @Autowired
    @Qualifier("bean3")
    Person personBean3;

    @GetMapping(value="/bean/{bean}")
    public Person getBean(@PathVariable String bean) {
        if (bean.equals("bean1")) {
            return personBean1;
        } else if (bean.equals("bean2")) {
            return personBean2;
        } else if (bean.equals("bean3")) {
            return personBean3;
        } else {
            return null;
        }
    }
}