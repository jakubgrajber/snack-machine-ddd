package com.greybear.snackmachine.repository;

import com.greybear.snackmachine.dto.SnackMachineDTO;
import org.springframework.data.repository.CrudRepository;


public interface SnackMachineRepository extends CrudRepository<SnackMachineDTO, Long> {
}
