package com.javatechie.security.demo.service;

import com.javatechie.security.demo.entity.Product;
import com.javatechie.security.demo.entity.UserInfo;
import com.javatechie.security.demo.repository.UserInfoRepository;
import jakarta.annotation.PostConstruct;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ProductsService {

    private List<Product> productList = null;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    UserInfoRepository repository;


    @PostConstruct
    public List<Product> loadProductsFromDB() {

        productList = IntStream.rangeClosed(1, 100)
                .mapToObj(i -> Product.builder()
                        .productId(i)
                        .name("product " + i)
                        .description("product dscr " + i)
                        .price(new Random().nextInt(2000)).build())
                .collect(Collectors.toList());

        return productList;
    }

    public String storeNewUser(UserInfo userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        repository.save(userInfo);
        return "User added in the database";
    }

    public List<Product> getAllProducts() {
        return productList;

    }

    public Product getProductById(int id) {
        return productList.stream()
                .filter(product -> product.getProductId() == id)
                .findAny()
                .orElseThrow(() -> new RuntimeException("Product " + id + " not found in DB"));
    }
}
