package sideseven_spring.service;


import org.springframework.stereotype.Service;
import sideseven_spring.model.Cliente;
import sideseven_spring.model.Producto;
import sideseven_spring.model.Venta;
import sideseven_spring.repository.ClienteRepository;
import sideseven_spring.repository.ProductoRepository;
import sideseven_spring.repository.VentaRepository;

import java.util.Date;
import java.util.List;

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

   public List<Venta> listarVentas() {
       return ventaRepository.findAll();
   }

   public Venta registrarVenta(Venta venta) {
       // Verificar que el cliente existe
       Cliente cliente = clienteRepository.findById(venta.getCliente().getId())
               .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

       // Verificar que el producto existe y tiene stock
       Producto producto = productoRepository.findById(venta.getProducto().getId())
               .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

       if (producto.getStock() <= 0) {
           throw new RuntimeException("Producto sin stock");
       }

       // Reducir stock
       producto.setStock(producto.getStock() - 1);
       productoRepository.save(producto);

       // Establecer precio y fecha si no están definidos
       if (venta.getPrecio() == 0) {
           venta.setPrecio(producto.getPrecio());
       }
       if (venta.getFecha() == null) {
           venta.setFecha(new Date());
       }

       return ventaRepository.save(venta);
   }


}
