package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import com.example.demo.model.Cliente;
import java.util.List;

@Service
@Transactional
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Cliente> getAllClientes(){
        return clienteRepository.findAll();
    }

    public Cliente getClienteById(Integer id) {
        return clienteRepository.findById(id).orElse(null);
    }

    public Cliente saveCliente(Cliente cliente) {
        if (cliente.getId() == null || (cliente.getContrasena() != null && !cliente.getContrasena().isEmpty())) {
            String passHashed = passwordEncoder.encode(cliente.getContrasena());
            cliente.setContrasena(passHashed);
        } else {
            Cliente existingCliente = clienteRepository.findById(cliente.getId()).orElse(null);
            if (existingCliente != null) {
                cliente.setContrasena(existingCliente.getContrasena());
            }
        }
        return clienteRepository.save(cliente);
    }

    public Cliente login(Cliente cliente) {
        Cliente foundCliente = clienteRepository.findByEmail(cliente.getEmail());

        if(foundCliente != null && passwordEncoder.matches(cliente.getContrasena(), foundCliente.getContrasena())) {
            return foundCliente;
        }
        return null;
    }

    public void deleteCliente(Integer id) {
        clienteRepository.deleteById(id);
    }
}
