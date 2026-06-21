package com.balantes.property_management_system.controller;

import com.balantes.property_management_system.model.enums.CommercialUnitStatus;
import com.balantes.property_management_system.service.CommercialUnitService;
import com.balantes.property_management_system.service.LeaseService;
import com.balantes.property_management_system.service.TenantPaymentRecordService;
import com.balantes.property_management_system.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.NumberFormat;
import java.util.Locale;

@Controller
@RequestMapping("/homepage")
public class AdminDashboardController {

    private final UserService userService;
    private final LeaseService leaseService;
    private final TenantPaymentRecordService paymentService;
    private final CommercialUnitService commercialUnitService;

    public AdminDashboardController(UserService userService,
                                    LeaseService leaseService,
                                    TenantPaymentRecordService paymentService,
                                    CommercialUnitService commercialUnitService) {

        this.userService = userService;
        this.leaseService = leaseService;
        this.paymentService = paymentService;
        this.commercialUnitService = commercialUnitService;
    }

    @GetMapping("/")
    public String dashboard(Model model) {

        model.addAttribute("title", "Dashboard");

        // USERS
        model.addAttribute("users", userService.getActiveUsers());

        // LEASES
        model.addAttribute("leases", leaseService.getAll());

        // PAYMENTS
        model.addAttribute("paymentRecords", paymentService.getAll());

        // ACTIVE TENANTS
        model.addAttribute("activeTenants",
                userService.countActiveTenants());

        // OCCUPIED SPACES
        model.addAttribute("totalLeases",
                leaseService.getAll().size());

        // AVAILABLE SPACES
        long availableSpaces = commercialUnitService.getAllUnits()
                .stream()
                .filter(unit -> unit.getStatus() == CommercialUnitStatus.VACANT)
                .count();

        model.addAttribute("availableSpaces", availableSpaces);

        // OCCUPIED AND AVAILABLE RATE
        long totalUnits = commercialUnitService.getAllUnits().size();

        long occupiedSpaces = totalUnits - availableSpaces;

        double occupiedRate = totalUnits > 0
                ? (occupiedSpaces * 100.0) / totalUnits
                : 0;

        double availableRate = totalUnits > 0
                ? (availableSpaces * 100.0) / totalUnits
                : 0;

        model.addAttribute("occupiedRate",
                String.format("%.0f%%", occupiedRate));

        model.addAttribute("availableRate",
                String.format("%.0f%%", availableRate));

        // TOTAL REVENUE
        double totalRevenue =
                leaseService.getAll()
                        .stream()
                        .mapToDouble(lease ->

                                (lease.getOneMonthAdvance() != null
                                        ? lease.getOneMonthAdvance()
                                        : 0)

                                        +

                                        (lease.getOneMonthDeposit() != null
                                                ? lease.getOneMonthDeposit()
                                                : 0)

                        )
                        .sum()

                        +

                        paymentService.getAll()
                                .stream()
                                .mapToDouble(payment ->

                                        payment.getAmount() != null
                                                ? payment.getAmount()
                                                : 0

                                )
                                .sum();

        NumberFormat pesoFormat =
                NumberFormat.getCurrencyInstance(
                        new Locale("en", "PH"));

        model.addAttribute("totalRevenue",
                pesoFormat.format(totalRevenue));

        return "home/home";
    }
}