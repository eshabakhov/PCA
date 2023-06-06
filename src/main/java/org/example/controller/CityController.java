package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.rpovzi.tables.pojos.Call;
import org.example.rpovzi.tables.pojos.City;
import org.example.service.CityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/cities")
@AllArgsConstructor
public class CityController {

    private final CityService CityService;


    @GetMapping(value = "/list")
    public List<City> getList(
            @RequestParam(value = "/page", defaultValue = "1") Integer page,
            @RequestParam(value = "/pageSize", defaultValue = "10") Integer pageSize) {
        return CityService.getList(page, pageSize);
    }

    @GetMapping(value = "/{id}")
    public City get(
            @PathVariable Long id) {
        return CityService.get(id);
    }

    @PostMapping
    public City create(@RequestBody City City) {
        return CityService.create(City);
    }

    @PutMapping
    public City update(@RequestBody City City) {
        return CityService.update(City);
    }

    @DeleteMapping(value = "/{id}")
    public void create(@PathVariable Long id) {
        CityService.delete(id);
    }
}
