package com.greybear.snackmachine.domain;

import lombok.Getter;

import java.util.Objects;

@Getter
public abstract class Entity {

    protected long id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        if (id == 0 || entity.id == 0) return false;
        return id == entity.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
