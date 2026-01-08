package sideseven_spring.model;


import jakarta.persistence.*;
import sideseven_spring.controller.VentaController;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="clientes")
public class Cliente{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nombre;
    private String direccion;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Venta> historialCompras = new ArrayList<>();

    public Cliente() {}

    public Cliente(String nombre, String direccion) {
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public Long getId() { return id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public List<Venta> getHistorialCompras() { return historialCompras; }

    @Override
    public String toString() {
        return "Cliente{id=" + id + ", nombre='" + nombre + "', direccion='" + direccion + "'}";
    }
}
