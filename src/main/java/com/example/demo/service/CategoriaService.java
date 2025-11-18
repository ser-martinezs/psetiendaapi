package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.repository.CategoriaRepository;
import jakarta.transaction.Transactional;
import com.example.demo.model.Categoria;
import com.example.demo.model.Producto;

import java.util.List;

@Service
@Transactional
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> getAllCategorias(){
        return categoriaRepository.findAll();
    }

    public Categoria getCategoriaById(Integer id) {
        return categoriaRepository.findById(id).orElse(null);
    }

    public Categoria saveCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public void deleteCategoria(Integer id) {
        Categoria categoria = categoriaRepository.findById(id).orElse(null);
        if (categoria != null) {
            categoriaRepository.deleteProductoCategoriaRelations(id);
            categoriaRepository.deleteById(id);
        }
    }  
}
