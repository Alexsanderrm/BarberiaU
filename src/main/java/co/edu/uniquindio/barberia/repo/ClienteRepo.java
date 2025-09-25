package co.edu.uniquindio.barberia.repo;


import co.edu.uniquindio.barberia.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepo extends JpaRepository<Cliente, Long> {
    boolean existsByDocumentoOrTelefono(String documento, String telefono);
}