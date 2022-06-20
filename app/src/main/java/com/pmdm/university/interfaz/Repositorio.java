package com.pmdm.university.interfaz;

import java.util.List;
import java.util.Optional;

public interface Repositorio <T> {

    void insert(T t);
    void update(T t);
    void delete(T t);
}
