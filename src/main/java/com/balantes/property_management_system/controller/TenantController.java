package com.balantes.property_management_system.controller;

import com.balantes.property_management_system.dto.LeaseDTO;
import com.balantes.property_management_system.dto.TenantPaymentRecordDTO;
import com.balantes.property_management_system.dto.UserDTO;
import com.balantes.property_management_system.service.LeaseService;
import com.balantes.property_management_system.service.TenantPaymentRecordService;
import com.balantes.property_management_system.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/tenant")
public class TenantController {

    private final UserService userService;
    private final TenantPaymentRecordService paymentService;
    private final LeaseService leaseService;

    public TenantController(UserService userService, TenantPaymentRecordService paymentService,
                            LeaseService leaseService){
        this.userService = userService;
        this.leaseService = leaseService;
        this.paymentService = paymentService;

    }
    @GetMapping
    public String index(Authentication authentication, Model model) {

        model.addAttribute("title", "MyAccount");

        String email = authentication.getName();

        UserDTO user = userService.getByEmail(email);
        int userId = user.getId();

        LeaseDTO lease = leaseService.getActiveLeaseByUser(userId);
        List<TenantPaymentRecordDTO> payments = paymentService.getByUserId(userId);

        model.addAttribute("user", user);
        model.addAttribute("lease", lease);
        model.addAttribute("paymentRecords", payments);

        return "tenant/tenant";
    }
}


