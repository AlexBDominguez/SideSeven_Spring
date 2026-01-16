package sideseven_spring.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ventas")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha")
    private Date fecha;


    @Column(name = "total", nullable = false)
    private double total;

    public Venta() {}

    public Venta(Cliente cliente, Producto producto, Date fecha, double total) {
        this.cliente = cliente;
        this.producto = producto;
        this.fecha = fecha;
        this.total = total;
    }

    public Long getId() { return id; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    // Alias para getPrecio() que devuelve el total
    public double getPrecio() { return total; }
    public void setPrecio(double precio) { this.total = precio; }

    @Override
    public String toString() {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm");

        String clienteNombre = (cliente != null) ? cliente.getNombre() : "N/A";
        String productoNombre = (producto != null) ? producto.getNombre() : "N/A";
        String fechaStr = (fecha != null) ? sdf.format(fecha) : "N/A";

        return String.format("[Venta #%d] Cliente: %s | Producto: %s | Fecha: %s | Total: %.2f€",
                id, clienteNombre, productoNombre, fechaStr, total);
    }
}