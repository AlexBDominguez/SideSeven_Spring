package sideseven_spring.service;


import org.springframework.stereotype.Service;
import sideseven_spring.model.Cliente;
import sideseven_spring.model.Producto;
import sideseven_spring.model.Venta;
import sideseven_spring.repository.ClienteRepository;
import sideseven_spring.repository.ProductoRepository;
import sideseven_spring.repository.VentaRepository;

import java.util.Date;

@Service
public class VentaService {

   private final VentaRepository ventaRepository;
   private final ClienteRepository clienteRepository;
   private final ProductoRepository productoRepository;

   public VentaService(VentaRepository ventaRepository, ClienteRepository clienteRepository, ProductoRepository productoRepository) {
      this.ventaRepository = ventaRepository;
      this.clienteRepository = clienteRepository;
      this.productoRepository = productoRepository;
   }

   public Venta registrarVenta(Long clienteId, Long productoId){
       Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + clienteId));

       Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + productoId));

       if (producto.getStock() <= 0){
           throw new RuntimeException("Producto sin stock: " + productoId);
       }

       producto.setStock(producto.getStock() - 1);
       productoRepository.save(producto);

       Venta venta = new Venta (cliente, producto, new Date(), producto.getPrecio());
       return ventaRepository.save(venta);

   }


}
