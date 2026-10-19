package com.bcp.gestion_cuentas.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransaccionResponse {
    private Long id;
    private String tipo;
    private BigDecimal monto;
    private LocalDateTime fecha;
    private String numeroCuentaOrigen;
    private String numeroCuentaDestino;

    public TransaccionResponse(Long id, String tipo, BigDecimal monto, LocalDateTime fecha, String numeroCuentaOrigen, String numeroCuentaDestino) {
        this.id = id;
        this.tipo = tipo;
        this.monto = monto;
        this.fecha = fecha;
        this.numeroCuentaOrigen = numeroCuentaOrigen;
        this.numeroCuentaDestino = numeroCuentaDestino;
    }

    // Getters
    public Long getId() { return id; }
    public String getTipo() { return tipo; }
    public BigDecimal getMonto() { return monto; }
    public LocalDateTime getFecha() { return fecha; }
    public String getNumeroCuentaOrigen() { return numeroCuentaOrigen; }
    public String getNumeroCuentaDestino() { return numeroCuentaDestino; }
}