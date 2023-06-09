package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.rpovzi.tables.pojos.Audit;
import org.example.service.AuditService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/audit")
@AllArgsConstructor
public class AuditController {

    private final AuditService auditService;

    @GetMapping(value = "/list")
    public List<Audit> getList(
            Principal principal,
            @RequestParam(value = "/page", defaultValue = "1") Integer page,
            @RequestParam(value = "/pageSize", defaultValue = "10") Integer pageSize) {
        auditService.audit(principal, "/abonents/list", "GET");
        return auditService.getList(page, pageSize);
    }
}
