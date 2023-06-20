package com.example.demo.adapter;

public interface DtoToEntity<ENTITY> {
    ENTITY dtoToEntity(ENTITY e);
}
