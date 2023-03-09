package com.greybear.snackmachine.rest;

import com.greybear.snackmachine.domain.Snack;
import com.greybear.snackmachine.domain.SnackMachine;

import com.greybear.snackmachine.service.SnackMachineService;
import com.greybear.snackmachine.service.SnackService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final SnackMachineService snackMachineService;
    private final SnackService snackService;

    @GetMapping("/snack-machine-test")
    public String test() {

        Optional<SnackMachine> optionalSnackMachine = snackMachineService.findById(1L);

        if (optionalSnackMachine.isPresent())
            return optionalSnackMachine.get().toString();

        else return "Snack Machine not found.";
    }

    @GetMapping("snack-test")
    public String getSnack() {

        Optional<Snack> byId = snackService.findById(1);

        if (byId.isPresent())
            return byId.get().toString();
        else return "Snack not found";
    }
}
