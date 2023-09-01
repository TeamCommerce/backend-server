package com.commerce.backendserver.image.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static jakarta.persistence.InheritanceType.SINGLE_TABLE;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Inheritance(strategy = SINGLE_TABLE)
@DiscriminatorColumn(name = "image_type")
@NoArgsConstructor(access = PROTECTED)
@Table(name = "t_image")
public abstract class Image {

    @Id
    @Column(name = "image_id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String url;

    protected Image(String url) {
        this.url = url;
    }
}
