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
        return productoService.listarProductos();
    }

    @GetMapping("/{id}")
    public Producto buscar(@PathVariable int id) {
        return productoService.buscarPorId(id);
    }

    @PostMapping
    public void agregar(@RequestBody Producto producto) {
        productoService.agregarProducto(producto);
    }

    @PutMapping("/{id}")
    public void actualizar(@PathVariable int id, @RequestBody Producto producto) {
        producto.setId(id);
        productoService.actualizarProducto(producto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable int id) {
        productoService.eliminarProducto(id);
    }
}
