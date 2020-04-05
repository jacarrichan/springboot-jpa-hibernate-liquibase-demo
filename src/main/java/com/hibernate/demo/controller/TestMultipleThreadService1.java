package com.hibernate.demo.controller;

import com.hibernate.demo.domain.Province;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

/**
 * @author wanli zhou
 * @created 2017-12-06 10:33 AM.
 */
@Component
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class TestMultipleThreadService1 {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private TestMultipleThreadSaveService1 testMultipleThreadSaveService1;
    public void save(String name){
        log.info("===> thread id is : {}", Thread.currentThread().getId());

        Province province = null;
        try {
            province = testMultipleThreadSaveService1.save(name);
            log.info("=||||=====>>>>>{}", em.createQuery("select p FROM Province p WHERE p.name = :name", Province.class).setParameter("name", name).getSingleResult().getName());
        }catch (PersistenceException e){
            if(e.getCause() instanceof ConstraintViolationException){
                log.error("重复--->{}", e);
                log.info("======>>>>>{}", em.createQuery("select p FROM Province p WHERE p.name = :name", Province.class).setParameter("name", name).getSingleResult());
                province = testMultipleThreadSaveService1.save(name + "-");
            }
        }

        log.info("2===> have save province: {}", province.getId());
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("3Sleep stop===>");
    }





}
