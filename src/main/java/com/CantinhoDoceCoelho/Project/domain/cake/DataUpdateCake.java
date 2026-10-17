package com.CantinhoDoceCoelho.Project.domain.cake;

import com.CantinhoDoceCoelho.Project.domain.cake.price.Price;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


public record DataUpdateCake(

        @NotNull
        Long id,

        Flavor flavor,

        @Valid
        Price price
) {
}
