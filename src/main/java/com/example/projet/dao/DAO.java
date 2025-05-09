package com.example.projet.dao;

import java.util.List;

public interface DAO<T> {
    T find(int id);
    List<T> findAll();
    boolean create(T obj);
    boolean update(T obj);
    boolean delete(T obj);
}