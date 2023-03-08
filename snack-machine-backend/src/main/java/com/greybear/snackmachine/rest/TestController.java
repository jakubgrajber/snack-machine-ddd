package com.greybear.snackmachine.rest;

import com.greybear.snackmachine.domain.SnackMachine;

import com.greybear.snackmachine.service.SnackMachineService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final SnackMachineService snackMachineService;

    @GetMapping("/test")
    public String test() {

        Optional<SnackMachine> optionalSnackMachine = snackMachineService.findById(1L);

        if (optionalSnackMachine.isPresent())
            return optionalSnackMachine.get().toString();

        else return "Snack Machine not found.";
    }
}
