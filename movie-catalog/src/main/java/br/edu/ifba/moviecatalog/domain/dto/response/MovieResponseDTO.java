package br.edu.ifba.moviecatalog.domain.dto.response;

import java.util.List;
import java.util.stream.Collectors;

import br.edu.ifba.moviecatalog.domain.enums.Category;
import br.edu.ifba.moviecatalog.entity.Movie;

public record MovieResponseDTO(Long id, String name,String synopsis, String photo, Category category){
    
    public MovieResponseDTO(Movie movie){
        this(movie.getId(), movie.getName(), movie.getSynopsis(), movie.getPhoto(), movie.getCategory());
    }

    public static List<MovieResponseDTO> toListDTO(List<Movie> list){
        return list.stream().map(MovieResponseDTO::new).collect(Collectors.toList());
    }

    public static MovieResponseDTO toDto(Movie movie) {
        return new MovieResponseDTO(movie);
    }
}
