package com.hibernate.demo.controller;

import com.hibernate.demo.domain.Province;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author wanli zhou
 * @created 2017-12-08 5:39 PM.
 */
@Controller
@RequestMapping(path = "/test/", consumes = "application/json", produces = "application/json")
public class TestHibernateJoinController {

    Logger log = LoggerFactory.getLogger(this.getClass());
    @PersistenceContext
    private EntityManager em;

    @GetMapping("/{id}")
    public ResponseEntity test(@PathVariable("id") Integer id){
        Province p =  em.createQuery("SELECT p FROM Province p JOIN FETCH p.cities where p.id = :id", Province.class)
                .setParameter("id", id).getSingleResult();
        return ResponseEntity.ok(p);

    }

}
