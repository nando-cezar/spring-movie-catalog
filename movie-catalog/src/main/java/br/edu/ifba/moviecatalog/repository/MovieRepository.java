package br.edu.ifba.moviecatalog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifba.moviecatalog.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    public List<Movie> findByNameContains(String name);
}
