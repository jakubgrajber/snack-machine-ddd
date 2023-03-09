package com.greybear.snackmachine.repository;

import com.greybear.snackmachine.domain.Snack;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SnackRepository extends CrudRepository<Snack, Long> {

}
