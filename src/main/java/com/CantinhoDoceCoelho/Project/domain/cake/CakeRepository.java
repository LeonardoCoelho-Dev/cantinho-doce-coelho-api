package com.CantinhoDoceCoelho.Project.domain.cake;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CakeRepository extends JpaRepository<Cake,Long> {
    Page<Cake> findAllByActiveTrue(Pageable pageable);
}
