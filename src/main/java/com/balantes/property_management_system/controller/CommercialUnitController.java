package com.balantes.property_management_system.controller;

import com.balantes.property_management_system.dto.CommercialUnitDTO;
import com.balantes.property_management_system.service.CommercialUnitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/commercial-units")
public class CommercialUnitController {

    private final CommercialUnitService service;

    public CommercialUnitController(CommercialUnitService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("title", "Commercial Unit List");
        List<CommercialUnitDTO> units = service.getAllUnits();
        model.addAttribute("units", units);
        return "commercial-unit/commercial-unit-list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("isUpdate", false);
        model.addAttribute("commercialUnit", new CommercialUnitDTO());
        return "commercial-unit/add-commercial-unit";
    }

    @PostMapping("/add")
    public String create(@ModelAttribute CommercialUnitDTO commercialUnitDTO, Model model) {
        try{
            service.create(commercialUnitDTO);
            return "redirect:/commercial-units";
        }catch(RuntimeException e) {
            model.addAttribute("isUpdate", false);
            model.addAttribute("errorMessage", "Unit name already exists");
            model.addAttribute("commercialUnit", commercialUnitDTO);
            return "commercial-unit/add-commercial-unit";
        }
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Integer id, Model model) {
        CommercialUnitDTO unit = service.getById(id);
        model.addAttribute("commercialUnit", unit);
        model.addAttribute("isUpdate", true);
        return "commercial-unit/add-commercial-unit";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Integer id,
                         @ModelAttribute CommercialUnitDTO dto) {
        service.update(id, dto);
        return "redirect:/commercial-units";
    }

    @GetMapping("/permanent-delete/{id}")
    public String delete(@PathVariable Integer id) {
        service.delete(id);
        return "redirect:/commercial-units";
    }

    //    Soft Delete
    @GetMapping("/soft-delete/{id}")
    public String softDelete(@PathVariable Integer id) {
        service.softDelete(id);
        return "redirect:/commercial-units";
    }
}

