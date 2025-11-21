package com.evaluacion.productosapi.service.exception;

public class ProductoYaExisteException extends RuntimeException {
    public ProductoYaExisteException(String nombre) {
        super("Ya existe un producto con el nombre: " + nombre);
    }
}