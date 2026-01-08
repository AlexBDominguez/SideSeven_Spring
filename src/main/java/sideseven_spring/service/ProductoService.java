package sideseven_spring.service;

import org.springframework.stereotype.Service;
import sideseven_spring.model.Producto;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductoService {

    private final List<Producto> productos = new ArrayList<>();

    public List<Producto> listarProductos() {
        return productos;
    }

    public Producto buscarPorId(int id) {
        return productos.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public void actualizarProducto(Producto productoNuevo){
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getId() == productoNuevo.getId()) {
                productos.set(i, productoNuevo);
                return;
            }
        }
    }

    public void eliminarProducto(int id) {
        productos.removeIf(p -> p.getId() == id);
    }
}
