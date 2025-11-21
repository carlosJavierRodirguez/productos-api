package com.evaluacion.productosapi.controller;

import com.evaluacion.productosapi.entity.Categoria;
import com.evaluacion.productosapi.service.exception.ProductoNoEncontradoException;
import com.evaluacion.productosapi.service.exception.ProductoYaExisteException;
import com.evaluacion.productosapi.service.exception.StockInsuficienteException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductoNoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> manejarNoEncontrado(ProductoNoEncontradoException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", "Producto no encontrado");
        errorResponse.put("detalle", ex.getMessage());
        errorResponse.put("timestamp", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(ProductoYaExisteException.class)
    public ResponseEntity<Map<String, Object>> manejarProductoYaExiste(ProductoYaExisteException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", "Producto ya existe");
        errorResponse.put("detalle", ex.getMessage());
        errorResponse.put("timestamp", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler({StockInsuficienteException.class, IllegalArgumentException.class})
    public ResponseEntity<Map<String, Object>> manejarBadRequest(RuntimeException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", "Solicitud inválida");
        errorResponse.put("detalle", ex.getMessage());
        errorResponse.put("timestamp", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> manejarCategoriaInvalida(MethodArgumentTypeMismatchException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        if (ex.getRequiredType() == Categoria.class) {
            errorResponse.put("error", "Categoría inválida");
            errorResponse.put("detalle", "Las categorías válidas son: TECNOLOGIA, ACCESORIOS, OFICINA");
        } else {
            errorResponse.put("error", "Parámetro inválido");
            errorResponse.put("detalle", ex.getMessage());
        }
        errorResponse.put("timestamp", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> manejarDataIntegrity(DataIntegrityViolationException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", "Violación de integridad de datos");
        errorResponse.put("detalle", "Ya existe un producto con ese nombre o hay un conflicto de datos");
        errorResponse.put("timestamp", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> manejarGeneral(Exception ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", "Error interno del servidor");
        errorResponse.put("detalle", ex.getMessage());
        errorResponse.put("timestamp", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}