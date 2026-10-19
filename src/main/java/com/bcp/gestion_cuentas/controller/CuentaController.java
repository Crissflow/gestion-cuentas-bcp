package com.bcp.gestion_cuentas.controller;

import com.bcp.gestion_cuentas.dto.CuentaRequest;
import com.bcp.gestion_cuentas.dto.CuentaResponse;
import com.bcp.gestion_cuentas.service.CuentaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {

    private final CuentaService service;

    public CuentaController(CuentaService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CuentaResponse registrar(@Valid @RequestBody CuentaRequest request) {
        return service.registrar(request);
    }

    @GetMapping
    public List<CuentaResponse> listarTodas() {
        return service.listarTodas();
    }

    @GetMapping("/cliente/{clienteId}")
    public List<CuentaResponse> listarPorCliente(@PathVariable Long clienteId) {
        return service.listarPorCliente(clienteId);
    }
}