package ru.mockingrave.ethereum.javabackend.mapper.base;

public interface BaseMapper<D, E> {

    D toDto(E entity);

    E toEntity(D dto);
}
