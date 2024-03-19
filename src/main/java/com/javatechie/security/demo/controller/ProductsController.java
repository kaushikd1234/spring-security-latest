package com.javatechie.security.demo.controller;

import com.javatechie.security.demo.entity.Product;
import com.javatechie.security.demo.entity.UserInfo;
import com.javatechie.security.demo.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductsService service;

    @PostMapping("/newUser")
    public String storeNewUser(@RequestBody UserInfo userInfo) {

        return service.storeNewUser(userInfo);
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "welcome to this end point which is not sure!!";
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<Product> getAllProducts() {
        return service.getAllProducts();

    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public Product getProductById(@PathVariable int id) {
        return service.getProductById(id);
    }
}
