package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.dto.ResponseList;
import org.example.rpovzi.tables.pojos.City;
import org.example.service.AuditService;
import org.example.service.CityService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(value = "/cities")
@AllArgsConstructor
public class CityController {

    private final CityService cityService;

    private final AuditService auditService;


    @GetMapping(value = "/list")
    public ResponseList<City> getList(
            Principal principal,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        auditService.audit(principal, "/cities/list", "GET");
        return cityService.getList(page, pageSize);
    }

    @GetMapping(value = "/{id}")
    public City get(
            Principal principal,
            @PathVariable Long id) {
        auditService.audit(principal, "/cities/{id}", "GET");
        return cityService.get(id);
    }

    @PostMapping
    public City create(Principal principal, @RequestBody City City) {
        auditService.audit(principal, "/cities/", "POST");
        return cityService.create(City);
    }

    @PutMapping
    public City update(Principal principal, @RequestBody City City) {
        auditService.audit(principal, "/cities/", "PUT");
        return cityService.update(City);
    }

    @DeleteMapping(value = "/{id}")
    public void create(Principal principal, @PathVariable Long id) {
        auditService.audit(principal, "/cities/{id}", "DELETE");
        cityService.delete(id);
    }
}
