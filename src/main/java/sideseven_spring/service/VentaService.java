package sideseven_spring.service;


import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VentaService {

    private final List<Venta> ventas = new ArrayList<>();

    public List<Venta> listarVentas(){
        return ventas;
    }

    public void registrarVenta(Venta venta){
        ventas.add(venta);
    }
}
