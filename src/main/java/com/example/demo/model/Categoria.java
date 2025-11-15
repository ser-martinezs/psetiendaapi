package com.example.demo.model;

import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.util.Set;
import jakarta.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor  
@Entity
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombreCategoria",length = 50, nullable = false)
    private String nombreCategoria;

    @ManyToMany(mappedBy = "categorias")
    Set<Producto> productos;
}
