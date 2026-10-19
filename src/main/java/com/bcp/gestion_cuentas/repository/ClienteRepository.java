package com.bcp.gestion_cuentas.repository;

import com.bcp.gestion_cuentas.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}