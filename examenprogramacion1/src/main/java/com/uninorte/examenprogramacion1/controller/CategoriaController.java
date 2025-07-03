package com.uninorte.examenprogramacion1.controller;

import com.uninorte.examenprogramacion1.model.Categoria;
import com.uninorte.examenprogramacion1.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public String listar(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<Categoria> categorias = categoriaService.listarCategorias(PageRequest.of(page, 5));
        model.addAttribute("categorias", categorias);
        model.addAttribute("paginaActual", page);
        return "categorias/listar";
    }

    @GetMapping("/nueva")
    public String mostrarFormularioNueva(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "categorias/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute @Valid Categoria categoria, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "categorias/formulario";
        }
        categoriaService.guardar(categoria);
        return "redirect:/categorias";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Categoria categoria = categoriaService.obtenerPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoría inválida: " + id));
        model.addAttribute("categoria", categoria);
        return "categorias/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        categoriaService.eliminar(id);
        return "redirect:/categorias";
    }
}
