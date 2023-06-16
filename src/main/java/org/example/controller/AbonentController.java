package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.rpovzi.tables.pojos.Abonent;
import org.example.service.AbonentService;
import org.example.service.AuditService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping(value = "/abonents")
@AllArgsConstructor
public class AbonentController {

    private final AbonentService abonentService;

    private final AuditService auditService;

    @GetMapping(value = "/list")
    public List<Abonent> getList(
            Principal principal,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        auditService.audit(principal, "/abonents/list", "GET");
        return abonentService.getList(page, pageSize);
    }

    @GetMapping(value = "/{id}")
    public Abonent get(
            Principal principal,
            @PathVariable Long id) {
        auditService.audit(principal, "/abonents/{id}", "GET");
        return abonentService.get(id);
    }

    @PostMapping
    public Abonent create(Principal principal, @RequestBody Abonent abonent) {
        auditService.audit(principal, "/abonents/", "POST");
        return abonentService.create(abonent);
    }

    @PutMapping
    public Abonent update(Principal principal, @RequestBody Abonent abonent) {
        auditService.audit(principal, "/abonents/", "PUT");
        return abonentService.update(abonent);
    }

    @DeleteMapping(value = "/{id}")
    public void create(Principal principal, @PathVariable Long id) {
        auditService.audit(principal, "/abonents/{id}", "DELETE");
        abonentService.delete(id);
    }
}
