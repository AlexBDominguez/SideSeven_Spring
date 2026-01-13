package sideseven_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sideseven_spring.model.Producto;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByNombreContainingIgnoreCase(String nombre);
    List<Producto> findByCategoriaContainingIgnoreCase(String categoria);
    List<Producto> findByStockLessThanEqual(int stock);
}
