package com.greybear.snackmachine.service;

import com.greybear.snackmachine.domain.*;
import com.greybear.snackmachine.dto.SlotDTO;
import com.greybear.snackmachine.dto.SnackDTO;
import com.greybear.snackmachine.dto.SnackMachineDTO;
import com.greybear.snackmachine.repository.SnackMachineRepository;
import com.greybear.snackmachine.repository.SnackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class SnackMachineService {

    private final SnackMachineRepository snackMachineRepository;
    private final SnackRepository snackRepository;

    // TODO refactor - introduce mappers/factories
    public Optional<SnackMachine> findById(long id) {

        SnackMachine snackMachine = null;

        Optional<SnackMachineDTO> optionalSnackMachineDTO = snackMachineRepository.findById(id);

        if (optionalSnackMachineDTO.isPresent()) {

            SnackMachineDTO snackMachineDTO = optionalSnackMachineDTO.get();

            Money money = new Money(
                    snackMachineDTO.getOneCentCount(),
                    snackMachineDTO.getTenCentCount(),
                    snackMachineDTO.getQuarterCount(),
                    snackMachineDTO.getOneDollarCount(),
                    snackMachineDTO.getFiveDollarCount(),
                    snackMachineDTO.getTwentyDollarCount()
            );

            List<Slot> slots = new ArrayList<>();

            Set<SlotDTO> slotDTOS = snackMachineDTO.getSlots();

            for (SlotDTO slotDTO : slotDTOS) {
                Optional<SnackDTO> optionalSnackDTO = snackRepository.findById(slotDTO.getSnackId());

                if (optionalSnackDTO.isPresent()) {
                    Snack snack = new Snack(optionalSnackDTO.get().getName());
                    SnackPile snackPile = new SnackPile(snack, slotDTO.getQuantity(), slotDTO.getPrice());

                    slots.add(new Slot(slotDTO.getId(), snackPile, snackMachine, slotDTO.getPosition()));
                }
            }

            snackMachine = new SnackMachine(snackMachineDTO.getId(), money, slots);

            return Optional.of(snackMachine);

        } else return Optional.empty();
    }
}
