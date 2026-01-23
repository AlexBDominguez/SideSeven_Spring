package sideseven_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sideseven_spring.model.VentaDetalle;

import java.util.List;

public interface VentaDetalleRepository extends JpaRepository<VentaDetalle, Long> {
    List<VentaDetalle> findByVentaId(Long ventaId);
}

