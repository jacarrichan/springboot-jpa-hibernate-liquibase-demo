package com.hibernate.demo.controller;

import com.hibernate.demo.domain.Province;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author wanli zhou
 * @created 2017-12-08 7:03 PM.
 */
@Component
public class TestMultipleThreadSaveService2 {

    @PersistenceContext
    private EntityManager em;

    private Logger log = LoggerFactory.getLogger(this.getClass());

    public Province save(String name){
        Province province = new Province();
        province.setName(name);
        province.setCode("C-" + name);
        em.persist(province);
        em.flush();
        log.info("1 Have save successfully, ==>{}", province.getId());
        return province;
    }
}
