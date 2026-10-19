package com.bcp.gestion_cuentas.controller;

import com.bcp.gestion_cuentas.dto.TransaccionRequest;
import com.bcp.gestion_cuentas.dto.TransaccionResponse;
import com.bcp.gestion_cuentas.service.TransaccionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transacciones")
public class TransaccionController {

    private final TransaccionService service;

    public TransaccionController(TransaccionService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransaccionResponse realizarTransaccion(@Valid @RequestBody TransaccionRequest request) {
        return service.procesarTransaccion(request);
    }

    @GetMapping("/cuenta/{cuentaId}")
    public List<TransaccionResponse> historialCuenta(@PathVariable Long cuentaId) {
        return service.listarHistorialCuenta(cuentaId);
    }
}