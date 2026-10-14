package com.CantinhoDoceCoelho.Project.domain.cake;

import com.CantinhoDoceCoelho.Project.domain.cake.price.Price;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Cake")
@Table(name = "cakes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Cake {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Price price;

    @Enumerated(EnumType.STRING)
    private Flavor flavor;

    private Boolean active;

    public Cake(CakeRegister data) {
        this.active = true;
        this.price = data.price();
        this.flavor = data.flavor();
    }




}
