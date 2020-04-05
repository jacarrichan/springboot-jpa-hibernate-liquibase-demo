package com.hibernate.springtest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hibernate.demo.domain.City;
import com.hibernate.demo.domain.Province;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wanli zhou
 * @created 2017-10-09 2:23 PM.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = H2DsConfi.class)
public class UseSpringTest {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Test
    public void createCSTest() {
        List<City> citis = new ArrayList<>();
        City c = new City();
        c.setCode("c-tc");
        c.setName("test-city");

        citis.add(c);

        Province newP = new Province();
        newP.setName("test-name");
        newP.setCode("p-tc");
        newP.setCities(citis);
        c.setProvince(newP);
        em.persist(newP);
        em.flush();
        List<Province> ps = em.createQuery("select p from Province p where p.name like :name", Province.class)
                .setParameter("name", "%test%")
                .getResultList();
        ObjectMapper objectMapper = new ObjectMapper();
        StringWriter stringEmp = new StringWriter();
        try {
            objectMapper.writeValue(stringEmp, ps);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Employee JSON is\n" + stringEmp);
        System.out.println("==");
    }

}
