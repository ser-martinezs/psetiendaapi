package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import com.example.demo.model.Cliente;
import java.util.List;
import java.util.Collections;

@Service
@Transactional
public class ClienteService implements UserDetailsService{
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PedidoService pedidoService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Cliente cliente = clienteRepository.findByEmail(email);
        if (cliente == null) {
            throw new UsernameNotFoundException("Usuario no encontrado con email: " + email);
        }
        String roleName = "ROLE_USER";
        if (cliente.getRol() != null && cliente.getRol().getId() == 3) {
            roleName = "ROLE_ADMIN";
        }
        return new User(
                cliente.getEmail(), 
                cliente.getContrasena(), 
                Collections.singletonList(new SimpleGrantedAuthority(roleName))
        );
    }    

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
        if (pedidoRepository.findByClienteId(id) != null) {
               pedidoService.deletePedido(pedidoRepository.findByClienteId(id).getId());
        }
        clienteRepository.deleteById(id);
    }

    public Cliente findByEmail(String email) {
        return clienteRepository.findByEmail(email);
    }
}
