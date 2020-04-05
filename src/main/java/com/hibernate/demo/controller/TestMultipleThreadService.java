package com.hibernate.demo.controller;

import com.hibernate.demo.domain.Province;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author wanli zhou
 * @created 2017-12-06 10:33 AM.
 */
@Component
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class TestMultipleThreadService {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @PersistenceContext
    private EntityManager em;


    public void save(String name){
        log.info("===> thread id is : {}", Thread.currentThread().getId());

        Province province = new Province();
        province.setName(name);
        province.setCode("C-" + name);
        em.persist(province);
        em.flush();
        log.info("===> have save province: {}", province.getId());
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("Sleep stop===>");
    }
}
