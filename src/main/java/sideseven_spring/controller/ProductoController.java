package sideseven_spring.controller;


import org.springframework.web.bind.annotation.*;
import sideseven_spring.model.Producto;
import sideseven_spring.service.ProductoService;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService productoService;


    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public List<Producto> listar() {
        return productoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Producto buscar(@PathVariable Long id) {
        return productoService.obtenerPorId(id);
    }

    @PostMapping
    public void agregar(@RequestBody Producto producto) {
        productoService.crearProducto(producto);
    }

    @PutMapping("/{id}")
    public void actualizar(@PathVariable Long id, @RequestBody Producto producto) {
        producto.setId(id);
        productoService.actualizarProducto(id, producto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        productoService.eliminarProducto(id);
    }
}
