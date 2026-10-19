package com.bcp.gestion_cuentas.service;

import com.bcp.gestion_cuentas.dto.TransaccionRequest;
import com.bcp.gestion_cuentas.dto.TransaccionResponse;
import com.bcp.gestion_cuentas.entity.Cuenta;
import com.bcp.gestion_cuentas.entity.Transaccion;
import com.bcp.gestion_cuentas.repository.CuentaRepository;
import com.bcp.gestion_cuentas.repository.TransaccionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransaccionService {

    private final TransaccionRepository transaccionRepository;
    private final CuentaRepository cuentaRepository;

    public TransaccionService(TransaccionRepository transaccionRepository, CuentaRepository cuentaRepository) {
        this.transaccionRepository = transaccionRepository;
        this.cuentaRepository = cuentaRepository;
    }

    @Transactional
    public TransaccionResponse procesarTransaccion(TransaccionRequest request) {
        Cuenta origen = null;
        Cuenta destino = null;

        switch (request.getTipo().toUpperCase()) {
            case "DEPOSITO":
                if (request.getCuentaDestinoId() == null) {
                    throw new RuntimeException("Se requiere el ID de la cuenta destino para un depósito");
                }
                destino = cuentaRepository.findById(request.getCuentaDestinoId())
                        .orElseThrow(() -> new RuntimeException("Cuenta destino no encontrada"));
                destino.setSaldo(destino.getSaldo().add(request.getMonto()));
                cuentaRepository.save(destino);
                break;

            case "RETIRO":
                if (request.getCuentaOrigenId() == null) {
                    throw new RuntimeException("Se requiere el ID de la cuenta origen para un retiro");
                }
                origen = cuentaRepository.findById(request.getCuentaOrigenId())
                        .orElseThrow(() -> new RuntimeException("Cuenta origen no encontrada"));
                if (origen.getSaldo().compareTo(request.getMonto()) < 0) {
                    throw new RuntimeException("Saldo insuficiente en la cuenta para realizar el retiro");
                }
                origen.setSaldo(origen.getSaldo().subtract(request.getMonto()));
                cuentaRepository.save(origen);
                break;

            case "TRANSFERENCIA":
                if (request.getCuentaOrigenId() == null || request.getCuentaDestinoId() == null) {
                    throw new RuntimeException("Se requieren cuentas origen y destino para una transferencia");
                }
                if (request.getCuentaOrigenId().equals(request.getCuentaDestinoId())) {
                    throw new RuntimeException("No se puede realizar una transferencia a la misma cuenta");
                }
                origen = cuentaRepository.findById(request.getCuentaOrigenId())
                        .orElseThrow(() -> new RuntimeException("Cuenta origen no encontrada"));
                destino = cuentaRepository.findById(request.getCuentaDestinoId())
                        .orElseThrow(() -> new RuntimeException("Cuenta destino no encontrada"));

                if (origen.getSaldo().compareTo(request.getMonto()) < 0) {
                    throw new RuntimeException("Saldo insuficiente para realizar la transferencia");
                }

                origen.setSaldo(origen.getSaldo().subtract(request.getMonto()));
                destino.setSaldo(destino.getSaldo().add(request.getMonto()));

                cuentaRepository.save(origen);
                cuentaRepository.save(destino);
                break;

            default:
                throw new RuntimeException("Tipo de transacción no soportado: " + request.getTipo());
        }

        Transaccion transaccion = new Transaccion(
                request.getTipo().toUpperCase(),
                request.getMonto(),
                LocalDateTime.now(),
                origen,
                destino
        );

        Transaccion guardada = transaccionRepository.save(transaccion);
        return mapearAResponse(guardada);
    }

    public List<TransaccionResponse> listarHistorialCuenta(Long cuentaId) {
        return transaccionRepository.findByCuentaOrigenIdOrCuentaDestinoId(cuentaId, cuentaId)
                .stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    private TransaccionResponse mapearAResponse(Transaccion t) {
        String numOrigen = t.getCuentaOrigen() != null ? t.getCuentaOrigen().getNumeroCuenta() : null;
        String numDestino = t.getCuentaDestino() != null ? t.getCuentaDestino().getNumeroCuenta() : null;
        return new TransaccionResponse(
                t.getId(),
                t.getTipo(),
                t.getMonto(),
                t.getFecha(),
                numOrigen,
                numDestino
        );
    }
}