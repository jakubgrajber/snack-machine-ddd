package com.greybear.snackmachine.domain;

import lombok.Getter;

import java.util.Objects;
import java.util.UUID;

@Getter
public abstract class Entity {

    protected UUID id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        if (id == null || entity.id == null) return false;
        return id == entity.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
