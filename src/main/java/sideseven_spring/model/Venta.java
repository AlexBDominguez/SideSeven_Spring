package sideseven_spring.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ventas")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<VentaDetalle> detalles = new ArrayList<>();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "total", nullable = false)
    private double total;

    // Campo para guardar el nombre del cliente en el momento de la venta
    @Column(name = "nombre_cliente")
    private String nombreCliente;

    public Venta() {}

    public Venta(Cliente cliente, Date fecha) {
        this.cliente = cliente;
        this.fecha = fecha;
        this.nombreCliente = cliente.getNombre();
        this.total = 0.0;
    }

    public Long getId() { return id; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
        if (cliente != null) {
            this.nombreCliente = cliente.getNombre();
        }
    }

    public List<VentaDetalle> getDetalles() { return detalles; }
    public void setDetalles(List<VentaDetalle> detalles) { this.detalles = detalles; }

    public void agregarDetalle(VentaDetalle detalle) {
        detalles.add(detalle);
        detalle.setVenta(this);
        recalcularTotal();
    }

    public void recalcularTotal() {
        this.total = detalles.stream()
                .mapToDouble(VentaDetalle::getSubtotal)
                .sum();
    }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

    // Alias para getPrecio() que devuelve el total
    public double getPrecio() { return total; }
    public void setPrecio(double precio) { this.total = precio; }

    // Métodos de compatibilidad con código antiguo
    @Deprecated
    public Producto getProducto() {
        if (detalles != null && !detalles.isEmpty()) {
            return detalles.get(0).getProducto();
        }
        return null;
    }

    @Deprecated
    public void setProducto(Producto producto) {
        // Por compatibilidad, pero ya no se usa
    }

    @Override
    public String toString() {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm");

        String clienteNombre = (nombreCliente != null) ? nombreCliente :
                              (cliente != null) ? cliente.getNombre() : "N/A";
        String fechaStr = (fecha != null) ? sdf.format(fecha) : "N/A";

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("\n[Venta #%d] Cliente: %s | Fecha: %s | Total: %.2f€",
                id, clienteNombre, fechaStr, total));

        if (detalles != null && !detalles.isEmpty()) {
            sb.append("\nProductos:");
            for (VentaDetalle detalle : detalles) {
                sb.append("\n").append(detalle.toString());
            }
        }

        return sb.toString();
    }
}

