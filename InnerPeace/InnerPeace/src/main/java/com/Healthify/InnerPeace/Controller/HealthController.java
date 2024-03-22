package com.Healthify.InnerPeace.Controller;

import com.Healthify.InnerPeace.dto.AuthRequest;
import com.Healthify.InnerPeace.dto.HealthProduct;
import com.Healthify.InnerPeace.entity.UserInfo;
import com.Healthify.InnerPeace.repository.HealthProductRepository;
import com.Healthify.InnerPeace.service.HealthService;
import com.Healthify.InnerPeace.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/InnerPeaceFoundation")
public class HealthController {


    @Autowired
    private HealthService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private HealthProductRepository repository;

    @Autowired
    private AuthenticationManager authenticationManager;


    @GetMapping("/welcome")
    public String welcome(){
        return "Welcome to Inner Peace Foundation; This endpoint is not secure though";

    }

    @PostMapping("/newUser")
    public String addNewUser(@RequestBody UserInfo userInfo){
        return service.addUser(userInfo);

    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<HealthProduct> getAllProducts(){
        return service.getProducts();

    }

    @PostMapping("addNewHealthProducts")
    public List<HealthProduct> saveProducts(@RequestBody List<HealthProduct> products){

        return repository.saveAll(products);

    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE USER')")
    public HealthProduct getProductById(@PathVariable int id){
        return service.getProduct(id);

    }


    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest){

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword()));
            if (authentication.isAuthenticated()) {
                return jwtService.generateToken(authRequest.getUsername());
            } else {
                throw new UsernameNotFoundException("invalid user request !");
            }
        }

        @PostMapping("/authenticateToken")
    public String authenticateAndGetTokens(@RequestBody AuthRequest authRequest1){

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest1.getUsername(),authRequest1.getPassword()));
        if (authentication.isAuthenticated()){
            return jwtService.generateToken(authRequest1.getUsername());

        } else {
            throw new UsernameNotFoundException("invalid user requests Sir!!");
        }
        }
    }



