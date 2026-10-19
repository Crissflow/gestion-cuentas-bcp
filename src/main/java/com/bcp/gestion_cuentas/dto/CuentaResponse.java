package com.bcp.gestion_cuentas.dto;

import java.math.BigDecimal;

public class CuentaResponse {
    private Long id;
    private String numeroCuenta;
    private String tipo;
    private BigDecimal saldo;
    private Long clienteId;
    private String nombreCliente;

    public CuentaResponse(Long id, String numeroCuenta, String tipo, BigDecimal saldo, Long clienteId, String nombreCliente) {
        this.id = id;
        this.numeroCuenta = numeroCuenta;
        this.tipo = tipo;
        this.saldo = saldo;
        this.clienteId = clienteId;
        this.nombreCliente = nombreCliente;
    }

    // Getters
    public Long getId() { return id; }
    public String getNumeroCuenta() { return numeroCuenta; }
    public String getTipo() { return tipo; }
    public BigDecimal getSaldo() { return saldo; }
    public Long getClienteId() { return clienteId; }
    public String getNombreCliente() { return nombreCliente; }
}