package sideseven_spring.controller;

import org.springframework.web.bind.annotation.*;
import sideseven_spring.model.Cliente;
import sideseven_spring.service.ClienteService;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService){
        this.clienteService = clienteService;
    }

    @GetMapping
    public List<Cliente> listar(){
        return clienteService.listarClientes();
    }

    @GetMapping("/{id}")
    public Cliente buscar(@PathVariable int id){
        return clienteService.buscarPorId(id);
    }

    @PostMapping
    public void agregar(@RequestBody Cliente cliente){
        clienteService.agregarCliente(cliente);
    }

    @PutMapping("/{id}")
    public void actualizar(@PathVariable int id, @RequestBody Cliente cliente) {
        cliente.setId(id);
        clienteService.actualizarCliente(cliente);
    }
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable int id) {
        clienteService.eliminarCliente(id);
    }
}
