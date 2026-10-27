package com.CantinhoDoceCoelho.Project.domain.cake;

import com.CantinhoDoceCoelho.Project.domain.shared.Price;

public record DataDetailedCake(Long id, Flavor flavor, Price price) {

    public DataDetailedCake(Cake cake) {
        this(cake.getId(),  cake.getFlavor(), cake.getPrice());
    }

}
