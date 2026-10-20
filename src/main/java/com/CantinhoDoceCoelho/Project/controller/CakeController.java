package com.CantinhoDoceCoelho.Project.controller;


import com.CantinhoDoceCoelho.Project.domain.cake.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping
    public ResponseEntity <Page<DataDetailedCake>> list (@PageableDefault (size = 10, sort = {"flavor" }) Pageable pageable){
        var page = cakeRepository.findAllByActiveTrue(pageable).map(DataDetailedCake::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity update (@RequestBody @Valid DataUpdateCake data){
        var cake = cakeRepository.getReferenceById(data.id());
        cake.updateInformation(data);

        return ResponseEntity.ok(new DataDetailedCake(cake));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id){
        var cake = cakeRepository.getReferenceById(id);

        cake.deactivate();

        return ResponseEntity.noContent().build();

    }

    @GetMapping("/{id}")
    public ResponseEntity detailCake(@PathVariable Long id){

        var cake = cakeRepository.getReferenceById(id);
        return ResponseEntity.ok(new DataDetailedCake(cake));
    }

}
