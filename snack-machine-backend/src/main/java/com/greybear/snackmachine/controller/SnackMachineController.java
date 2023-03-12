package com.greybear.snackmachine.controller;

import com.greybear.snackmachine.domain.SnackMachine;
import com.greybear.snackmachine.repository.SnackMachineRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

import static com.greybear.snackmachine.domain.Money.*;

@Controller
@RequiredArgsConstructor
public class SnackMachineController {

    private final SnackMachineRepository snackMachineRepository;
    private SnackMachine mainSnackMachine;

    @PostConstruct
    private void postConstruct() {
        mainSnackMachine = snackMachineRepository.findById(1L).orElseThrow(IllegalArgumentException::new);
    }

    @GetMapping("/")
    public String snackMachineView(Model model) {
        model.addAttribute("snackMachine", mainSnackMachine);
        model.addAttribute("purchaseInfo", "Insert money");
        return "snack-machine";
    }

    @GetMapping("insertMoney")
    public String insertMoney(@RequestParam Value value) {
        mainSnackMachine.insertMoney(
                switch (value) {
                    case ONE_CENT -> CENT;
                    case TEN_CENT -> TEN_CENT;
                    case QUARTER -> QUARTER;
                    case ONE_DOLLAR -> DOLLAR;
                    case FIVE_DOLLAR -> FIVE_DOLLAR;
                    case TWENTY_DOLLAR -> TWENTY_DOLLAR;
                });
        snackMachineRepository.save(mainSnackMachine);
        return "redirect:/";
    }

    @GetMapping("returnMoney")
    public String returnMoney() {
        mainSnackMachine.returnMoney();
        snackMachineRepository.save(mainSnackMachine);
        return "redirect:/";
    }

    @GetMapping("buySnack")
    public String buySnack(@RequestParam int position, Model model) {
        model.addAttribute("snackMachine", mainSnackMachine);
        if (mainSnackMachine.canMakePurchase(position)) {
            mainSnackMachine.buySnack(position);
            snackMachineRepository.save(mainSnackMachine);
            model.addAttribute("purchaseInfo", "Transaction completed!");
        } else
            model.addAttribute("purchaseInfo", "Not enough money!");

        return "snack-machine";
    }
}
