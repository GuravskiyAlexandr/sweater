package com.example.sweater.repository;

import com.example.sweater.domain.Messege;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessegeRepository extends CrudRepository<Messege, Integer> {

    List<Messege> findByTag(String filtr);
}
