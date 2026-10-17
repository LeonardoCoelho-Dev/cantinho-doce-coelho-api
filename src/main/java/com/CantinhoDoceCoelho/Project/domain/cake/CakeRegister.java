package com.CantinhoDoceCoelho.Project.domain.cake;

import com.CantinhoDoceCoelho.Project.domain.cake.price.Price;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;


public record CakeRegister(

      @NotNull
      Flavor flavor,

      @NotNull @Valid
      Price price
) {


}
