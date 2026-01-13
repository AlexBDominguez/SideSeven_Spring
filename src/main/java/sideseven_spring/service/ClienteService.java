package sideseven_spring.service;


import org.springframework.stereotype.Service;
import sideseven_spring.model.Cliente;
import sideseven_spring.repository.ClienteRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository){
        this.clienteRepository = clienteRepository;
    }

    public Cliente crearCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public List<Cliente> obtenerTodos(){
        return clienteRepository.findAll();
    }

    public Optional<Cliente> obtenerPorId(Long id){
        return clienteRepository.findById(id);
    }

    public Cliente actualizarCliente(Long id, Cliente datos){
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + id));

                cliente.setNombre(datos.getNombre());
                cliente.setDireccion(datos.getDireccion());

                return clienteRepository.save(cliente);
    }

    public void eliminarCliente(Long id){
        clienteRepository.deleteById(id);
    }

 }
