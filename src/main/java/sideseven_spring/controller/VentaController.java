package sideseven_spring.controller;

import org.springframework.web.bind.annotation.*;
import sideseven_spring.model.Venta;
import sideseven_spring.service.VentaService;

import java.util.List;
import java.util.Map;

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

    // Endpoint nuevo para ventas con múltiples productos
    @PostMapping("/nueva")
    public Venta registrarNueva(@RequestBody VentaRequest request){
        return ventaService.registrarVenta(request.getClienteId(), request.getProductos());
    }

    // Endpoint legacy para compatibilidad
    @PostMapping
    @Deprecated
    public void registrar(@RequestBody Venta venta){
        ventaService.registrarVenta(venta);
    }

    // DTO para recibir la petición
    public static class VentaRequest {
        private Long clienteId;
        private Map<Long, Integer> productos; // Map de productoId -> cantidad

        public Long getClienteId() { return clienteId; }
        public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

        public Map<Long, Integer> getProductos() { return productos; }
        public void setProductos(Map<Long, Integer> productos) { this.productos = productos; }
    }
}
