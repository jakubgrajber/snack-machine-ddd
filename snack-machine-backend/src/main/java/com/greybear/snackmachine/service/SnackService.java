package com.greybear.snackmachine.service;

import com.greybear.snackmachine.domain.Snack;
import com.greybear.snackmachine.dto.SnackDTO;
import com.greybear.snackmachine.repository.SnackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class SnackService {

    private final Map<Long, Snack> inMemorySnacks;

    @Autowired
    public SnackService(SnackRepository snackRepository) {

        this.inMemorySnacks = new HashMap<>();

        Iterable<SnackDTO> allSnacks = snackRepository.findAll();

        for (SnackDTO snackDTO : allSnacks)
            inMemorySnacks.put(snackDTO.getId(), new Snack(snackDTO.getId(), snackDTO.getName()));

    }

    public Optional<Snack> findById(long id) {

        if (inMemorySnacks.containsKey(id))
            return Optional.of(inMemorySnacks.get(id));

        else return Optional.empty();
    }
}
