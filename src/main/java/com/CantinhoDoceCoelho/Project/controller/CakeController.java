package com.CantinhoDoceCoelho.Project.controller;


import com.CantinhoDoceCoelho.Project.domain.cake.Cake;
import com.CantinhoDoceCoelho.Project.domain.cake.CakeRegister;
import com.CantinhoDoceCoelho.Project.domain.cake.CakeRepository;
import com.CantinhoDoceCoelho.Project.domain.cake.DataDetailedCake;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/cakes")
public class CakeController {

    @Autowired
    private CakeRepository cakeRepository;

    @PostMapping
    @Transactional
    public ResponseEntity register(@RequestBody @Valid CakeRegister data, UriComponentsBuilder uriBuilder) {

        var cake = new Cake(data);
        cakeRepository.save(cake);

        var uri = uriBuilder.path("/cakes/{id}").buildAndExpand(cake.getId()).toUri();

        return ResponseEntity.created(uri).body(new DataDetailedCake(cake));
    }


}
