package com.hibernate.demo.controller;

import com.hibernate.demo.domain.City;
import com.hibernate.demo.domain.County;
import com.hibernate.demo.domain.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author wanli zhou
 * @created 2017-10-09 11:00 PM.
 */
@Controller
@RequestMapping(path = "/address/", consumes = "application/json", produces = "application/json")
public class AddressController {


    private final AddressService addressService;

    @Value("${spring.datasource.password}")
    private String pwd;

    @Autowired
    public AddressController(AddressService addressService){
        this.addressService = addressService;
    }

    @GetMapping("pwd")
    public ResponseEntity getPwd(){
        return ResponseEntity.ok(pwd);
    }

    @GetMapping("province/id/{id}")
    public ResponseEntity getProvinceById(@PathVariable("id") Integer id){
        Province pi = addressService.getProvinceById(id);
        System.out.println(pi);
        return ResponseEntity.ok(pi);
    }
    @GetMapping("province/{name}")
    public ResponseEntity getProvinceByName(@PathVariable("name") String name){
        Province pi = addressService.getProvinceByName(name);
        System.out.println(pi);
        return ResponseEntity.ok(pi);
    }

    @GetMapping("city/{name}")
    public ResponseEntity getCityByName(@PathVariable("name") String name){
        City ci = addressService.getCityByName(name);
        System.out.println(ci);
        return ResponseEntity.ok(ci);
    }

    @GetMapping("county/{name}")
    public ResponseEntity getCountyByName(@PathVariable("name") String name){
        County ci = addressService.getCountyByName(name);
        System.out.println(ci);
        return ResponseEntity.ok(ci);
    }
}
