package sideseven_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sideseven_spring.model.Venta;

import java.util.Date;
import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, Long> {
    List<Venta>findByClienteId(Long clienteId);
    List<Venta>findByProductoId(Long productoId);
    List<Venta>findByFechaBetween(Date inicio, Date fin);
}
