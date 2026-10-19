package com.bcp.gestion_cuentas.service;

import com.bcp.gestion_cuentas.dto.TransaccionRequest;
import com.bcp.gestion_cuentas.entity.Cliente;
import com.bcp.gestion_cuentas.entity.Cuenta;
import com.bcp.gestion_cuentas.repository.CuentaRepository;
import com.bcp.gestion_cuentas.repository.TransaccionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransaccionServiceTest {

    @Mock
    private TransaccionRepository transaccionRepository;

    @Mock
    private CuentaRepository cuentaRepository;

    @InjectMocks
    private TransaccionService transaccionService;

    private Cuenta cuentaOrigen;

    @BeforeEach
    void setUp() {
        Cliente cliente = new Cliente("Christopher", "Neciosup", "76543210", "christopher@bcp.pe");
        cliente.setId(1L);

        cuentaOrigen = new Cuenta("193-76543210-0-12", "AHORROS", new BigDecimal("100.00"), cliente);
        cuentaOrigen.setId(1L);
    }

    @Test
    @DisplayName("Debe lanzar excepción si la cuenta no tiene saldo suficiente para un retiro")
    void testRetiroSaldoInsuficiente() {
        // Dado un saldo de 100.00 e intento de retirar 500.00
        TransaccionRequest request = new TransaccionRequest();
        request.setTipo("RETIRO");
        request.setMonto(new BigDecimal("500.00"));
        request.setCuentaOrigenId(1L);

        when(cuentaRepository.findById(1L)).thenReturn(Optional.of(cuentaOrigen));

        // Cuando & Entonces
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            transaccionService.procesarTransaccion(request);
        });

        assertEquals("Saldo insuficiente en la cuenta para realizar el retiro", exception.getMessage());
        verify(transaccionRepository, never()).save(any());
    }
}