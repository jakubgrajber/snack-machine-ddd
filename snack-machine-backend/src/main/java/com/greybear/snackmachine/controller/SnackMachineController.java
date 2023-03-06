package com.greybear.snackmachine.controller;

import com.greybear.snackmachine.domain.SnackMachine;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.greybear.snackmachine.domain.Money.*;

@Controller
public class SnackMachineController {

    private final SnackMachine snackMachine = new SnackMachine();

    @GetMapping("/")
    public String snackMachineView(Model model) {
        model.addAttribute("snackMachine", snackMachine);
        return "snack-machine";
    }

    @GetMapping("insertMoney")
    public String insertMoney(@RequestParam Value value) {
        snackMachine.insertMoney(
                switch (value) {
                    case ONE_CENT -> CENT;
                    case TEN_CENT -> TEN_CENT;
                    case QUARTER -> QUARTER;
                    case ONE_DOLLAR -> DOLLAR;
                    case FIVE_DOLLAR -> FIVE_DOLLAR;
                    case TWENTY_DOLLAR -> TWENTY_DOLLAR;
                });
        return "redirect:/";
    }

    @GetMapping("returnMoney")
    public String returnMoney() {
        snackMachine.returnMoney();
        return "redirect:/";
    }

    @GetMapping("buySnack")
    public String buySnack() {
        snackMachine.buySnack();
        return "redirect:/";
    }
}
