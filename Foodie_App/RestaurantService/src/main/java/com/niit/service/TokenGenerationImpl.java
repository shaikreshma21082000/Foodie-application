//package com.niit.service;
//
//import com.niit.model.Restaurant;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//@Service
//public class TokenGenerationImpl implements TokenGeneration{
//    @Override
//    public Map<String, String> generateToken(Restaurant restaurant) {
//        String jwt_token=null;
//        jwt_token= Jwts.builder().setSubject(restaurant.getEmail()).setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256,"mysecret").compact();
//        Map<String,String> map=new HashMap<>();
//        map.put("token",jwt_token);
//        map.put("message:"," Admin Login Successful");
//        return map;
//    }
//}
