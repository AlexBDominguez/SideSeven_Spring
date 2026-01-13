package sideseven_spring.service;

import org.springframework.stereotype.Service;
import sideseven_spring.model.Producto;
import sideseven_spring.repository.ProductoRepository;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository){
        this.productoRepository = productoRepository;
    }

    public Producto crearProducto(Producto producto){
        return productoRepository.save(producto);
    }

    public List<Producto> obtenerTodos(){
        return productoRepository.findAll();
    }

    public Producto obtenerPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
    }

    public Producto actualizarProducto(Long id, Producto datos){
        Producto producto = obtenerPorId(id);
        producto.setNombre(datos.getNombre());
        producto.setCategoria(datos.getCategoria());
        producto.setPrecio(datos.getPrecio());
        producto.setStock(datos.getStock());
        return productoRepository.save(producto);
    }

    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }

}
