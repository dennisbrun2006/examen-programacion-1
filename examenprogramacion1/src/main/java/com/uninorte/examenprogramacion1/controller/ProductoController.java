package com.uninorte.examenprogramacion1.controller;

import com.uninorte.examenprogramacion1.model.Categoria;
import com.uninorte.examenprogramacion1.model.Producto;
import com.uninorte.examenprogramacion1.service.CategoriaService;
import com.uninorte.examenprogramacion1.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public String listar(
            Model model,
            @RequestParam(defaultValue = "") String nombre,
            @RequestParam(required = false) Long categoriaId,
            @RequestParam(defaultValue = "0") int page
    ) {
        Page<Producto> productos = productoService.listarProductos(nombre, categoriaId, PageRequest.of(page, 5));
        List<Categoria> categorias = categoriaService.listarCategorias(PageRequest.of(0, 100)).getContent();

        model.addAttribute("productos", productos);
        model.addAttribute("categorias", categorias);
        model.addAttribute("nombre", nombre);
        model.addAttribute("categoriaId", categoriaId);
        model.addAttribute("paginaActual", page);

        return "productos/listar";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("categorias", categoriaService.listarCategorias(PageRequest.of(0, 100)).getContent());
        return "productos/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute @Valid Producto producto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categorias", categoriaService.listarCategorias(PageRequest.of(0, 100)).getContent());
            return "productos/formulario";
        }
        productoService.guardar(producto);
        return "redirect:/productos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Producto producto = productoService.obtenerPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto inválido: " + id));

        model.addAttribute("producto", producto);
        model.addAttribute("categorias", categoriaService.listarCategorias(PageRequest.of(0, 100)).getContent());
        return "productos/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return "redirect:/productos";
    }
}
