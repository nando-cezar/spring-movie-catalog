package br.edu.ifba.moviecatalog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifba.moviecatalog.entity.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    public List<Movie> findByNameContains(String name);
}
