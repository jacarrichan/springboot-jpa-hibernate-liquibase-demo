package com.hibernate.demo.controller;

import org.hibernate.exception.ConstraintViolationException;
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
@RequestMapping(path = "/multipleThread/", consumes = "application/json", produces = "application/json")
public class TestMultipleThreadController {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TestMultipleThreadService testMultipleThreadService;

    @GetMapping("/{name}")
    public ResponseEntity multipleThread(@PathVariable("name") String name){
        log.info("===>{}" , name);
        try {
            testMultipleThreadService.save(name);
        }catch (ConstraintViolationException e){
            log.error("{} 重复索引异常, {}", name, e);
            log.info("====>{}", e.getCause() instanceof ConstraintViolationException);
            if(e.getCause() instanceof ConstraintViolationException){
                testMultipleThreadService.save(name + "-");
            }


        }
        return ResponseEntity.ok("");
    }

}
