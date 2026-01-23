package sideseven_spring.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sideseven_spring.model.Cliente;
import sideseven_spring.model.Producto;
import sideseven_spring.model.Venta;
import sideseven_spring.model.VentaDetalle;
import sideseven_spring.repository.ClienteRepository;
import sideseven_spring.repository.ProductoRepository;
import sideseven_spring.repository.VentaRepository;
import sideseven_spring.repository.VentaDetalleRepository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class VentaService {

   private final VentaRepository ventaRepository;
   private final ClienteRepository clienteRepository;
   private final ProductoRepository productoRepository;
   private final VentaDetalleRepository ventaDetalleRepository;

   public VentaService(VentaRepository ventaRepository,
                       ClienteRepository clienteRepository,
                       ProductoRepository productoRepository,
                       VentaDetalleRepository ventaDetalleRepository) {
      this.ventaRepository = ventaRepository;
      this.clienteRepository = clienteRepository;
      this.productoRepository = productoRepository;
      this.ventaDetalleRepository = ventaDetalleRepository;
   }

   @Transactional
   public Venta registrarVenta(Long clienteId, Map<Long, Integer> productosConCantidades) {
       Cliente cliente = clienteRepository.findById(clienteId)
               .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + clienteId));

       // Crear la venta
       Venta venta = new Venta(cliente, new Date());
       venta = ventaRepository.save(venta);

       // Agregar los detalles (productos)
       for (Map.Entry<Long, Integer> entry : productosConCantidades.entrySet()) {
           Long productoId = entry.getKey();
           Integer cantidad = entry.getValue();

           Producto producto = productoRepository.findById(productoId)
                   .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + productoId));

           if (producto.getStock() < cantidad) {
               throw new RuntimeException("Stock insuficiente para producto: " + producto.getNombre() +
                                        " (disponible: " + producto.getStock() + ", solicitado: " + cantidad + ")");
           }

           // Reducir stock
           producto.setStock(producto.getStock() - cantidad);
           productoRepository.save(producto);

           // Crear detalle
           VentaDetalle detalle = new VentaDetalle(venta, producto, cantidad);
           venta.agregarDetalle(detalle);
       }

       // Recalcular total y guardar
       venta.recalcularTotal();
       return ventaRepository.save(venta);
   }

   // Método legacy para compatibilidad con un solo producto
   @Deprecated
   @Transactional
   public Venta registrarVenta(Long clienteId, Long productoId) {
       Map<Long, Integer> productos = Map.of(productoId, 1);
       return registrarVenta(clienteId, productos);
   }

   public List<Venta> listarVentas() {
       return ventaRepository.findAll();
   }

   @Deprecated
   @Transactional
   public Venta registrarVenta(Venta venta) {
       // Método legacy - redirigir al nuevo método
       if (venta.getProducto() != null) {
           return registrarVenta(venta.getCliente().getId(), venta.getProducto().getId());
       }
       throw new RuntimeException("Método obsoleto, usar registrarVenta(clienteId, Map<productoId, cantidad>)");
   }


}
