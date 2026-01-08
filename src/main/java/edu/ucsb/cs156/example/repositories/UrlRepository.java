package edu.ucsb.cs156.example.repositories;

import edu.ucsb.cs156.example.entities.Url;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The UrlRepository is a repository for Url entities, that is, it is the abstraction for the
 * database table for Urls
 */
@Repository
public interface UrlRepository extends CrudRepository<Url, Long> {}
