package com.adrianjaime.calmatumente2.data.repository;

import java.util.List;

/**
 * Created by emaneff on 19/01/2017.
 */
public interface Repository<T> {

    Session Session();
    void insert(T entity);
    boolean update(T entity);
    boolean delete(T entity);
    T get(int id);
    List<T> getAll();
    List<T> queryList(String query, String[] args);

}
