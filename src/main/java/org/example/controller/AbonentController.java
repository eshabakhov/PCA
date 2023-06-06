package org.example.controller;

import org.example.rpovzi.tables.pojos.Abonent;
import lombok.AllArgsConstructor;
import org.example.service.AbonentService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

import static org.springframework.security.authorization.AuthorityReactiveAuthorizationManager.hasRole;


@RestController
@RequestMapping(value = "/abonents")
@AllArgsConstructor
public class AbonentController {

    private final AbonentService abonentService;


    @GetMapping(value = "/list")
    public List<Abonent> getList(
            @RequestParam(value = "/page", defaultValue = "1") Integer page,
            @RequestParam(value = "/pageSize", defaultValue = "10") Integer pageSize) {
        return abonentService.getList(page, pageSize);
    }

    @GetMapping(value = "/{id}")
    public Abonent get(
            @PathVariable Long id) {
        return abonentService.get(id);
    }

    @PostMapping
    public Abonent create(@RequestBody Abonent abonent) {
        return abonentService.create(abonent);
    }

    @PutMapping
    public Abonent update(@RequestBody Abonent abonent) {
        return abonentService.update(abonent);
    }

    @DeleteMapping(value = "/{id}")
    public void create(@PathVariable Long id) {
        abonentService.delete(id);
    }
}
