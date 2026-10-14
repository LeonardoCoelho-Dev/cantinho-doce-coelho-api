package com.CantinhoDoceCoelho.Project.domain.cake;

import com.CantinhoDoceCoelho.Project.domain.cake.price.Price;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CakeRegister(

      @NotNull
      Flavor flavor,

      @NotNull
      Price price
) {


}
