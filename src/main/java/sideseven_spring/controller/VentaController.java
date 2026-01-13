package sideseven_spring.controller;

import org.springframework.web.bind.annotation.*;
import sideseven_spring.model.Venta;
import sideseven_spring.service.VentaService;

import java.util.List;

@RestController
@RequestMapping("/ventas")
public class VentaController {

    private final VentaService ventaService;

    public VentaController(VentaService ventaService){
        this.ventaService = ventaService;
    }

    @GetMapping
    public List<Venta> listar(){
        return ventaService.listarVentas();
    }

    @PostMapping
    public void registrar(@RequestBody Venta venta){
        ventaService.registrarVenta(venta);
    }
}
