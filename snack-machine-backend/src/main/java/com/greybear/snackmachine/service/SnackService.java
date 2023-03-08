package com.greybear.snackmachine.service;

import com.greybear.snackmachine.domain.Snack;
import com.greybear.snackmachine.dto.SnackDTO;
import com.greybear.snackmachine.repository.SnackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SnackService {

    private final SnackRepository snackRepository;

    public Optional<Snack> findById(long id) {

        Optional<SnackDTO> optionalSnackDTO = snackRepository.findById(id);
        if (optionalSnackDTO.isPresent()) {

            SnackDTO snackDTO = optionalSnackDTO.get();

            return Optional.of(
                    new Snack(snackDTO.getId(), snackDTO.getName()));

        } else return Optional.empty();
    }
}
