package com.greybear.snackmachine.repository;

import com.greybear.snackmachine.dto.SnackDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SnackRepository extends CrudRepository<SnackDTO, Long> {

}
