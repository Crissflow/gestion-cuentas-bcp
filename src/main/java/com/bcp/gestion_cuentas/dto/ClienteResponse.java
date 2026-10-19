package com.bcp.gestion_cuentas.dto;

public class ClienteResponse {

    private Long id;
    private String nombres;
    private String apellidos;
    private String documento;
    private String correo;

    public ClienteResponse(Long id, String nombres, String apellidos, String documento, String correo) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.documento = documento;
        this.correo = correo;
    }

    // Getters
    public Long getId() { return id; }
    public String getNombres() { return nombres; }
    public String getApellidos() { return apellidos; }
    public String getDocumento() { return documento; }
    public String getCorreo() { return correo; }
}