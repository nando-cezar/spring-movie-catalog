package br.edu.ifba.moviecatalog.entity;

import br.edu.ifba.moviecatalog.domain.enums.Category;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "movies")
public class Movie {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(length = 1024)
    private String synopsis;
    private String photo;
    @Enumerated(EnumType.STRING)
    private Category category;

    public Movie(String name, String synopsis, String photo, Category category) {
        this.name = name;
        this.synopsis = synopsis;
        this.photo = photo;
        this.category = category;
    }

}