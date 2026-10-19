package com.bcp.gestion_cuentas.controller;

import com.bcp.gestion_cuentas.dto.ClienteRequest;
import com.bcp.gestion_cuentas.dto.ClienteResponse;
import com.bcp.gestion_cuentas.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteResponse registrar(@Valid @RequestBody ClienteRequest request) {
        return service.registrar(request);
    }

    @GetMapping("/{id}")
    public ClienteResponse buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @GetMapping
    public List<ClienteResponse> listar() {
        return service.listar();
    }
}