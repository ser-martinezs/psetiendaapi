package com.example.demo.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cliente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombreCliente", length = 100, nullable = false)
    private String nombre;

    @Column(name = "emailCliente", length = 150, nullable = false, unique = true)
    private String email;
    
    @Column(name = "contrasenaCliente", length = 255, nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String contrasena;

    @Column(name = "telefonoCliente", length = 15, nullable = false)
    private String telefono;

    @Column(name = "direccionCliente", length = 255, nullable = false)
    private String direccion;

    @ManyToOne
    @JoinColumn(name = "idComuna", nullable = false)
    private Comuna comuna;

    @ManyToOne
    @JoinColumn(name = "idRol", nullable = false)
    private Rol rol;

    @Column(name = "carrito", length = 512, nullable = true)
    private String carrito;

}
