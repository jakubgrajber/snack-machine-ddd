package com.greybear.snackmachine.rest;

import com.greybear.snackmachine.domain.Snack;
import com.greybear.snackmachine.domain.SnackMachine;
import com.greybear.snackmachine.repository.SnackMachineRepository;
import com.greybear.snackmachine.repository.SnackRepository;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Slf4j
public class TestController {

    private final SnackMachineRepository snackMachineRepository;
    private final SnackRepository snackRepository;

    @GetMapping("/test")
    public void test() {

        Iterable<SnackMachine> snackMachines = snackMachineRepository.findAll();
        for (SnackMachine snackMachine : snackMachines)
            log.info(snackMachine.toString());

        Iterable<Snack> snacks = snackRepository.findAll();
        for (Snack snack : snacks)
            log.info(snack.toString());
    }
}
