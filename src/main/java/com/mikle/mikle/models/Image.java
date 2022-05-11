package com.mikle.mikle.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "images")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "originalFileName")
    private String originalFileName;

    @Column(name = "size")
    private Long size;

    @Column(name = "ContentType")
    private String ContentType; // расширение файла

    @Column(name = "isPreviewImage")
    private boolean isPreviewImage;

    @Lob // аннотация, что будем хранить нижнюю переменную в типе long blob
    private byte[] bytes;

    // образуем связь много к одному
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER) // каскадный тип - при изменении одного параметра товара меняются и другие; fetch - способ загрузки фотографий eager - пердварительная подгрузка свего свящанного с этой фотографией (медленная)
    private Products product;
}
