package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.rpovzi.tables.pojos.Abonent;
import org.example.rpovzi.tables.pojos.Audit;
import org.example.service.AbonentService;
import org.example.service.AuditService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
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
            @RequestParam(value = "/page", defaultValue = "1") Integer page,
            @RequestParam(value = "/pageSize", defaultValue = "10") Integer pageSize) {
        auditService.create(new Audit(null, principal.getName(), "/abonents/list", "GET", LocalDateTime.now()));
        return abonentService.getList(page, pageSize);
    }

    @GetMapping(value = "/{id}")
    public Abonent get(
            Principal principal,
            @PathVariable Long id) {
        auditService.create(new Audit(null, principal.getName(), "/abonents/{id}", "GET", LocalDateTime.now()));
        return abonentService.get(id);
    }

    @PostMapping
    public Abonent create(Principal principal, @RequestBody Abonent abonent) {
        auditService.create(new Audit(null, principal.getName(), "/abonents/", "POST", LocalDateTime.now()));
        return abonentService.create(abonent);
    }

    @PutMapping
    public Abonent update(Principal principal, @RequestBody Abonent abonent) {
        auditService.create(new Audit(null, principal.getName(), "/abonents/", "PUT", LocalDateTime.now()));
        return abonentService.update(abonent);
    }

    @DeleteMapping(value = "/{id}")
    public void create(Principal principal, @PathVariable Long id) {
        auditService.create(new Audit(null, principal.getName(), "/abonents/{id}", "DELETE", LocalDateTime.now()));
        abonentService.delete(id);
    }
}
