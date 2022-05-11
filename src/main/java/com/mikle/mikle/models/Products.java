package com.mikle.mikle.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor // выставляет геттеры и сеттеры и тд, предудущая тоже самое делает
public class Products {

    @Id // указываем, что id - праймари кей таблицы
    @GeneratedValue(strategy = GenerationType.AUTO)// указываем тип генерации id
    @Column(name = "id") // указываем имена полей, которые находятся в базе данных
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description", columnDefinition = "text")
    // тип текст, чтоб не было ограничение в 256 символов переменной варчар
    private String description;

    @Column(name = "price")
    private int price;

    @Column(name = "city")
    private String city;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "product")
    // данным параметром указываем, что каждый товар, связанный с фотографией, будет записан в таблице image
    private List<Image> images = new ArrayList<>();

    @Column(name = "preview_image_id")
    private Long previewImageId; // поле для хранения айди главной (первой) фотографии

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    @Column(name = "date_of_created")
    private LocalDateTime dateOfCreated; // время создания товара

    @PrePersist // показываем, что это метод инициализации
    private void init() { // функция назначения времени создания объявления
        dateOfCreated = LocalDateTime.now();
    }

    public void addImageToProduct(Image image) {
        image.setProduct(this); // указываем, что товар текущий
        images.add(image);
    }
}
