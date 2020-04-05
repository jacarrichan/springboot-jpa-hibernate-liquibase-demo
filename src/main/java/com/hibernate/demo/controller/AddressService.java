package com.hibernate.demo.controller;

import com.hibernate.demo.domain.City;
import com.hibernate.demo.domain.County;
import com.hibernate.demo.domain.Province;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.xml.bind.Marshaller;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author wanli zhou
 * @created 2017-10-09 11:11 PM.
 */
@Service
@Transactional
public class AddressService {

    @PersistenceContext
    private EntityManager em;

    public Province getProvinceById(Integer id) {

        Province p = em.createQuery("SELECT p FROM Province p WHERE p.id = :id", Province.class)
                .setParameter("id", id)
                .getResultList().stream().findFirst().orElse(null);
        return p;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public Province getProvinceByName(String name) {
        Province p  = em.createQuery("SELECT p FROM Province p WHERE p.name like :name", Province.class)
                .setParameter("name", "%" + name + "%")
                .getResultList().stream().findFirst().orElse(null);
        return p;
    }

    public City getCityByName(String name) {
        City c =  em.createQuery("SELECT c FROM City c WHERE c.name like :name", City.class)
                .setParameter("name", "%" + name + "%")
                .getResultList().stream().findFirst().orElse(null);
        return c;

    }

    public County getCountyByName(String name) {
        County c =  em.createQuery("SELECT c FROM County c WHERE c.name like :name", County.class)
                .setParameter("name", "%" + name + "%")
                .getResultList().stream().findFirst().orElse(null);
        return c;
    }
}
