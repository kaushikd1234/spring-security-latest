package com.javatechie.security.demo.controller;

import com.javatechie.security.demo.dto.AuthRequest;
import com.javatechie.security.demo.entity.Product;
import com.javatechie.security.demo.entity.UserInfo;
import com.javatechie.security.demo.service.JwtService;
import com.javatechie.security.demo.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ProductsService service;

    @Autowired
    private JwtService jwtService;


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

    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        String username = authRequest.getUsername();
        String password = authRequest.getPassword();
        Authentication authentication = authenticationManager.authenticate
                (new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(username);
        } else {
            throw new UsernameNotFoundException("user is not a valid user..");
        }

    }
}
