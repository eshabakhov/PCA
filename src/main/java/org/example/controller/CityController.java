package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.rpovzi.tables.pojos.Audit;
import org.example.rpovzi.tables.pojos.City;
import org.example.service.AuditService;
import org.example.service.CityService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/cities")
@AllArgsConstructor
public class CityController {

    private final CityService cityService;

    private final AuditService auditService;


    @GetMapping(value = "/list")
    public List<City> getList(
            Principal principal,
            @RequestParam(value = "/page", defaultValue = "1") Integer page,
            @RequestParam(value = "/pageSize", defaultValue = "10") Integer pageSize) {
        auditService.create(new Audit(null, principal.getName(), "/cities/list", "GET", LocalDateTime.now()));
        return cityService.getList(page, pageSize);
    }

    @GetMapping(value = "/{id}")
    public City get(
            Principal principal,
            @PathVariable Long id) {
        auditService.create(new Audit(null, principal.getName(), "/cities/{id}}", "GET", LocalDateTime.now()));
        return cityService.get(id);
    }

    @PostMapping
    public City create(Principal principal, @RequestBody City City) {
        auditService.create(new Audit(null, principal.getName(), "/cities/", "POST", LocalDateTime.now()));
        return cityService.create(City);
    }

    @PutMapping
    public City update(Principal principal, @RequestBody City City) {
        auditService.create(new Audit(null, principal.getName(), "/cities/", "PUT", LocalDateTime.now()));
        return cityService.update(City);
    }

    @DeleteMapping(value = "/{id}")
    public void create(Principal principal, @PathVariable Long id) {
        auditService.create(new Audit(null, principal.getName(), "/cities/{id}", "DELETE", LocalDateTime.now()));
        cityService.delete(id);
    }
}
