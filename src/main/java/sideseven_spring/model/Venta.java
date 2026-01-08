package sideseven_spring.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "ventas")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

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
    public Producto getProducto() { return producto; }

    public Date getFecha() { return fecha; }
    public double getTotal() { return total; }
}
