package com.example.demo.adapter;

public interface EntityToDto<Entity, DTO> {
    public DTO changeToDto(Entity entity);
}