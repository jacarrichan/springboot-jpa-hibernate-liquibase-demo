package com.hibernate.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author wanli zhou
 * @created 2017-12-06 10:32 AM.
 */
@Controller
@RequestMapping(path = "/multipleThread1/", consumes = "application/json", produces = "application/json")
public class TestMultipleThreadController1 {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TestMultipleThreadService1 testMultipleThreadService1;

    @GetMapping("/{name}")
    public ResponseEntity multipleThread(@PathVariable("name") String name){
        log.info("===>{}" , name);

        testMultipleThreadService1.save(name);
        return ResponseEntity.ok("");
    }

}
