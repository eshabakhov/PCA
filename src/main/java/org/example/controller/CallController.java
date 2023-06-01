package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.rpovzi.tables.pojos.Abonent;
import org.example.rpovzi.tables.pojos.Call;
import org.example.service.CallService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/calls")
@AllArgsConstructor
public class CallController {

    private final CallService callService;


    @GetMapping(value = "/list")
    public List<Call> getList(
            @RequestParam(value = "/page", defaultValue = "1") Integer page,
            @RequestParam(value = "/pageSize", defaultValue = "10") Integer pageSize) {
        return callService.getList(page, pageSize);
    }

    @GetMapping(value = "/{id}")
    public Call get(
            @PathVariable Long id) {
        return callService.get(id);
    }

    @PostMapping
    public Call create(@RequestBody Call call) {
        return callService.create(call);
    }

    @PutMapping
    public Call update(@RequestBody Call call) {
        return callService.update(call);
    }

    @DeleteMapping(value = "/{id}")
    public void create(@PathVariable Long id) {
        callService.delete(id);
    }
}
