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
        return clienteService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Cliente buscar(@PathVariable Long id){
        return clienteService.obtenerPorId(id).orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }

    @PostMapping
    public void agregar(@RequestBody Cliente cliente){
        clienteService.crearCliente(cliente);
    }

    @PutMapping("/{id}")
    public void actualizar(@PathVariable Long id, @RequestBody Cliente cliente) {
        cliente.setId(id);
        clienteService.actualizarCliente(id, cliente);
    }
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        clienteService.eliminarCliente(id);
    }
}
