package com.mikle.mikle.repositories;

import com.mikle.mikle.models.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository <Products, Long>{ // прописали наследование и тем самым подключили интерфейс JpaRepository для работы с базой данных
    List<Products> findByTitle(String title);
}
