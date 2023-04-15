package br.edu.ifba.moviecatalog.domain.dto.request;

import br.edu.ifba.moviecatalog.domain.enums.Category;
import br.edu.ifba.moviecatalog.entity.Movie;

public record MovieRequestDTO(String name,String synopsis, String photo, Category category){

    public Movie toEntity(){
        return new Movie(name, synopsis, photo, category);
    }
}

