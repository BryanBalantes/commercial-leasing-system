package com.balantes.property_management_system.controller;

import com.balantes.property_management_system.dto.TenantPaymentRecordDTO;
import com.balantes.property_management_system.service.LeaseService;
import com.balantes.property_management_system.service.TenantPaymentRecordService;
import com.balantes.property_management_system.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

/**
 * The controller for handling requests for the payment record pages.
 *
 * @author Bryan.Balantes
 * */

@Controller
@RequestMapping("/records")
public class TenantPaymentRecordController {

    private final TenantPaymentRecordService service;
    private final UserService userService;
    private final LeaseService leaseService;

    public TenantPaymentRecordController(TenantPaymentRecordService service,
                                         UserService userService, LeaseService leaseService) {
        this.service = service;
        this.userService = userService;
        this.leaseService = leaseService;
    }

    @GetMapping("/")
    public String listAll(Model model) {
        List<TenantPaymentRecordDTO> paymentRecords = service.getAll();
        model.addAttribute("paymentRecords", paymentRecords);
        return "records/list";
    }

    @GetMapping("/{id}")
    public String getViewPage(Model model, @PathVariable int id) {
        TenantPaymentRecordDTO tenantPaymentRecordDTO = service.getById(id);
        model.addAttribute("record", tenantPaymentRecordDTO);
        return "records/view";
    }

    @GetMapping("/add")
    public String addPage(Model model) {
        model.addAttribute("title", "Payments");
        model.addAttribute("record", new TenantPaymentRecordDTO());
        model.addAttribute("users", userService.getAll());

        // 🔥 ADD THIS
        model.addAttribute("leases", leaseService.getAll()); // OR leaseService.getAll()

        return "records/add";
    }

    @PostMapping("/add")
    public String create(@ModelAttribute("record") TenantPaymentRecordDTO dto,
                         @RequestParam("file") MultipartFile file) {

        service.create(dto, file);
        return "redirect:/users/";
    }

    @GetMapping("/update/{id}")
    public String updatePage(@PathVariable int id, Model model) {
        model.addAttribute("record", service.getById(id));
        model.addAttribute("users", userService.getAll());
        return "records/update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("record") TenantPaymentRecordDTO dto,
                         @RequestParam("file") MultipartFile file) {

        service.update(dto, file);
        return "redirect:/users/";
    }

    @PostMapping("/image/{id}")
    public ResponseEntity<?> uploadImage(@PathVariable Integer id,
                                         @RequestParam("file") MultipartFile file) {
        service.uploadImage(id, file);
        return ResponseEntity.ok("Uploaded");
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        service.delete(id);
        return "redirect:/users/";
    }
}
