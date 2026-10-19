package com.bcp.gestion_cuentas.service;

import com.bcp.gestion_cuentas.dto.CuentaRequest;
import com.bcp.gestion_cuentas.dto.CuentaResponse;
import com.bcp.gestion_cuentas.entity.Cliente;
import com.bcp.gestion_cuentas.entity.Cuenta;
import com.bcp.gestion_cuentas.repository.ClienteRepository;
import com.bcp.gestion_cuentas.repository.CuentaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CuentaService {

    private final CuentaRepository cuentaRepository;
    private final ClienteRepository clienteRepository;

    public CuentaService(CuentaRepository cuentaRepository, ClienteRepository clienteRepository) {
        this.cuentaRepository = cuentaRepository;
        this.clienteRepository = clienteRepository;
    }

    public CuentaResponse registrar(CuentaRequest request) {
        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + request.getClienteId()));

        Cuenta cuenta = new Cuenta(
                request.getNumeroCuenta(),
                request.getTipo(),
                request.getSaldo(),
                cliente
        );

        Cuenta guardada = cuentaRepository.save(cuenta);
        return mapearAResponse(guardada);
    }

    public List<CuentaResponse> listarTodas() {
        return cuentaRepository.findAll().stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    public List<CuentaResponse> listarPorCliente(Long clienteId) {
        return cuentaRepository.findByClienteId(clienteId).stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    private CuentaResponse mapearAResponse(Cuenta cuenta) {
        String nombreCompleto = cuenta.getCliente().getNombres() + " " + cuenta.getCliente().getApellidos();
        return new CuentaResponse(
                cuenta.getId(),
                cuenta.getNumeroCuenta(),
                cuenta.getTipo(),
                cuenta.getSaldo(),
                cuenta.getCliente().getId(),
                nombreCompleto
        );
    }
}