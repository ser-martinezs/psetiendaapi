package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Categoria;
import com.example.demo.model.Producto;
import com.example.demo.repository.CategoriaRepository;
import com.example.demo.repository.ProductoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Producto> getAllProducts(){
        return productoRepository.findAll();
    }

    public Producto getProductById(Integer id) {
        return productoRepository.findById(id).orElse(null);
    }

    public Producto partialUpdate(Producto producto){
        Producto existingproducto = productoRepository.findById(producto.getId()).orElse(null);
        if (existingproducto != null) {
            if (producto.getNombre() != null) {
                existingproducto.setNombre(producto.getNombre());
            }

            if(producto.getDescripcion() != null) {
                existingproducto.setDescripcion(producto.getDescripcion());
            }

            if(producto.getPrecio() != null) {
                existingproducto.setPrecio(producto.getPrecio());
            }

            if(producto.getStock() != null) {
                existingproducto.setStock(producto.getStock());
            }

            if(producto.getCategorias() != null) {
                existingproducto.setCategorias(producto.getCategorias());
            }
            
            if(producto.getBbID() != null) {
                existingproducto.setBbID(producto.getBbID());
            }

            return productoRepository.save(existingproducto);
        }
        return null;
    }

    public Producto saveProducto(Producto producto) {
        if (producto.getCategorias() != null) {
            List<Categoria> categoriasExistentes = new ArrayList<>();
            for (Categoria cat : producto.getCategorias()) {
                if (cat.getId() != null) {
                    categoriaRepository.findById(cat.getId())
                        .ifPresent(categoriasExistentes::add);
                }
            }
            producto.setCategorias(categoriasExistentes);
        }
        return productoRepository.save(producto);
    }

    public void deleteProducto(Integer id) {
        Producto producto = productoRepository.findById(id).orElse(null);
        if (producto != null) {
            producto.getCategorias().clear(); 
            productoRepository.delete(producto);
        }
    }
}
