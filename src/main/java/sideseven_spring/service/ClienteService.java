package sideseven_spring.service;


import org.springframework.stereotype.Service;
import sideseven_spring.model.Cliente;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteService {

    private final List<Cliente> clientes = new ArrayList<>();

    public List<Cliente> listarClientes(){
        return clientes;
    }

    public Cliente buscarPorId(int id){
        return clientes.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void agregarCliente(Cliente cliente){
        clientes.add(cliente);
    }

    public void actualizarCliente(Cliente clienteNuevo){
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getId() == clienteNuevo.getId()) {
                clientes.set(i, clienteNuevo);
                return;
            }
        }
    }

    public void eliminarCliente(int id){
        clientes.removeIf(c -> c.getId() == id);
    }
}
