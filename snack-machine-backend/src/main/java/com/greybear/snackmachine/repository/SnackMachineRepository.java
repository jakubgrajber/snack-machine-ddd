package com.greybear.snackmachine.repository;

import com.greybear.snackmachine.domain.SnackMachine;
import org.springframework.data.repository.CrudRepository;


public interface SnackMachineRepository extends CrudRepository<SnackMachine, Long> {
}
