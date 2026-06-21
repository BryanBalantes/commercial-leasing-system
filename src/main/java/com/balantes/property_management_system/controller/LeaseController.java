package com.balantes.property_management_system.controller;

import com.balantes.property_management_system.dto.LeaseDTO;
import com.balantes.property_management_system.service.CommercialUnitService;
import com.balantes.property_management_system.service.LeaseService;
import com.balantes.property_management_system.service.TenantPaymentRecordService;
import com.balantes.property_management_system.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/leases")
public class LeaseController {

    private final LeaseService leaseService;
    private final UserService userService;
    private final TenantPaymentRecordService paymentService;
    private final CommercialUnitService commercialUnitService;

    public LeaseController(LeaseService leaseService,
                           UserService userService,
                           TenantPaymentRecordService paymentService,
                           CommercialUnitService commercialUnitService) {
        this.leaseService = leaseService;
        this.userService = userService;
        this.paymentService = paymentService;
        this.commercialUnitService = commercialUnitService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("leases", leaseService.getAll());
        return "leases/list";
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable int id, Model model) {
        model.addAttribute("lease", leaseService.getById(id));
        return "leases/view";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("lease", new LeaseDTO());
        model.addAttribute("users", userService.getAll());
        model.addAttribute("units", commercialUnitService.getAvailableUnits());
        return "leases/add";
    }

    @PostMapping("/add")
    public String create(
            @Valid @ModelAttribute("lease") LeaseDTO leaseDTO,
            BindingResult result,
            @RequestParam("file") MultipartFile file,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("users", userService.getAll());
            return "leases/add";
        }

        leaseService.create(leaseDTO, file);
        return "redirect:/users/";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable int id, Model model) {

        LeaseDTO lease = leaseService.getById(id);

        model.addAttribute("lease", leaseService.getById(id));
        model.addAttribute("users", userService.getAll());
        model.addAttribute("units", commercialUnitService.getUnitsForUpdate(lease.getCommercialUnitId())
        );
        return "leases/update";
    }

    @PostMapping("/update")
    public String update(
            @ModelAttribute("lease") LeaseDTO leaseDTO,
            @RequestParam(value = "file", required = false) MultipartFile file) {

        leaseService.update(leaseDTO, file);

        return "redirect:/users/view/" + leaseDTO.getUserId();
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        leaseService.delete(id);
        return "redirect:/users/";
    }

    @GetMapping("/user/{userId}")
    public String getByUser(@PathVariable int userId, Model model) {
        model.addAttribute("leases", leaseService.getByUser(userId));
        model.addAttribute("user", userService.getById(userId));
        return "leases/user-leases";
    }
}
