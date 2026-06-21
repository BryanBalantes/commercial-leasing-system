package com.balantes.property_management_system.controller;

import com.balantes.property_management_system.config.USER_TYPE;
import com.balantes.property_management_system.dto.LeaseDTO;
import com.balantes.property_management_system.dto.UserDTO;
import com.balantes.property_management_system.service.LeaseService;
import com.balantes.property_management_system.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("/users")
public class UserController {
    private UserService userService;
    private final TenantPaymentRecordService paymentService;
    private final LeaseService leaseService;
    private final CommercialUnitService commercialUnitService;

    public UserController(UserService userService, TenantPaymentRecordService paymentService,
                          LeaseService leaseService,
                          CommercialUnitService commercialUnitService) {
        this.userService = userService;
        this.paymentService = paymentService;
        this.leaseService = leaseService;
        this.commercialUnitService = commercialUnitService;
    }

    @GetMapping("/")
    public String listAll(Model model) {
        model.addAttribute("title", "Users");
        List<UserDTO> users = userService.getAll();
        model.addAttribute("users", users);
        return "users/list";
    }

    @GetMapping("/view/{id}")
    public String getViewPage(Model model, @PathVariable int id) {

        UserDTO userDTO = userService.getById(id);
        model.addAttribute("title", "Tenant");
        model.addAttribute("user", userDTO);

        if (USER_TYPE.ADMIN.getType().equalsIgnoreCase(userDTO.getType())) {
            model.addAttribute("title", "MyProfile");
            return "users/user-admin";
        }

        List<TenantPaymentRecordDTO> paymentRecords =
                paymentService.getByUserId(id);
        model.addAttribute("paymentRecords", paymentRecords);

        LeaseDTO lease = leaseService.getActiveByUser(id)
                .stream()
                .findFirst()
                .orElse(null);

        model.addAttribute("lease", lease);

        List<CommercialUnitDTO> units = commercialUnitService.getAllUnits();
        model.addAttribute("units", units);

        return "users/user";
    }

    @GetMapping("/update/{id}")
    public String getUpdatePage(Model model, @PathVariable int id) {

        UserDTO userDTO = userService.getById(id);
        model.addAttribute("user", userDTO);

        List<TenantPaymentRecordDTO> paymentRecords =
                paymentService.getByUserId(id);
        model.addAttribute("paymentRecords", paymentRecords);

        LeaseDTO lease = leaseService.getActiveByUser(id)
                .stream()
                .findFirst()
                .orElse(null);

        model.addAttribute("lease", lease);

        List<CommercialUnitDTO> units = commercialUnitService.getAllUnits();
        model.addAttribute("units", units);

        return "users/user";
    }

    @GetMapping("/add")
    public String getAddPage(Model model) {
        model.addAttribute("title", "Tenants");
        model.addAttribute("user", new UserDTO());
        return "users/add";
    }

    @PostMapping("/add")
    public String create(
            @Valid @ModelAttribute("user") UserDTO userDTO,
            BindingResult result,
            Model model) {

        userDTO.setType(USER_TYPE.GENERAL.getType());

        if (result.hasErrors()) {
            model.addAttribute(
                    "errors",
                    result.getAllErrors().stream()
                            .map(error -> error.getDefaultMessage())
                            .collect(Collectors.toUnmodifiableList()));

            return "users/add";
        }

        userService.create(userDTO);

        if (userDTO.hasErrors()) {
            model.addAttribute("errors", userDTO.getErrors());
            return "users/add";
        }

        return "redirect:/users/";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("user") UserDTO userDTO) {
        userService.update(userDTO);
        return "redirect:/users/";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.delete(id);
        return "redirect:/users/";
    }
}

