package com.CantinhoDoceCoelho.Project.domain.cake;

import com.CantinhoDoceCoelho.Project.domain.cake.price.Price;
import jakarta.validation.constraints.NotNull;

public record DataUpdateCake(

        @NotNull
        Long id,

        Flavor flavor,

        Price price
) {
}
