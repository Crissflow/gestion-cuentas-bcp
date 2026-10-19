package com.bcp.gestion_cuentas.service;

import com.bcp.gestion_cuentas.dto.ClienteRequest;
import com.bcp.gestion_cuentas.dto.ClienteResponse;
import com.bcp.gestion_cuentas.entity.Cliente;
import com.bcp.gestion_cuentas.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public ClienteResponse registrar(ClienteRequest request) {
        Cliente cliente = new Cliente(
                request.getNombres(),
                request.getApellidos(),
                request.getDocumento(),
                request.getCorreo()
        );
        Cliente guardado = repository.save(cliente);
        return mapearAResponse(guardado);
    }

    public ClienteResponse buscarPorId(Long id) {
        Cliente cliente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe el cliente con ID: " + id));
        return mapearAResponse(cliente);
    }

    public List<ClienteResponse> listar() {
        return repository.findAll().stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    private ClienteResponse mapearAResponse(Cliente cliente) {
        return new ClienteResponse(
                cliente.getId(),
                cliente.getNombres(),
                cliente.getApellidos(),
                cliente.getDocumento(),
                cliente.getCorreo()
        );
    }
}