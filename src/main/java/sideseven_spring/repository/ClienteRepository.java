package sideseven_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sideseven_spring.model.Cliente;

import java.util.List;

public interface ClienteRepository  extends JpaRepository<Cliente, Long> {
    List<Cliente> findByNombreContainingIgnoreCase(String nombre);
    List<Cliente> findByDireccionContainingIgnoreCase(String direccion);
}
