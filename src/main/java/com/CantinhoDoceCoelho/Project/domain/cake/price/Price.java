package com.CantinhoDoceCoelho.Project.domain.cake.price;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Price {

    @Positive
    private BigDecimal price;


}
