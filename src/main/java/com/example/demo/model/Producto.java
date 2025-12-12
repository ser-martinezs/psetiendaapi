package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombreProducto", length = 50, nullable = false)
    private String nombre;

    @Column(name = "descripcion", length = 250, nullable = false)
    private String descripcion;

    @Column(name = "precio", nullable = false)
    private Integer precio;

    @Column(name = "stock", nullable = false)
    private Integer stock;
    
    @ManyToMany
    @JoinTable(
        name = "producto_categorias",
        joinColumns = @JoinColumn(name = "id_producto"),  
        inverseJoinColumns = @JoinColumn(name = "id_categoria")  
    )
    private List<Categoria> categorias;

    
    @Column(name = "bbID", length = 512, nullable = true)
    private String bbID;
}
