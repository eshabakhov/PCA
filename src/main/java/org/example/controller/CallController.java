package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.dto.CallDto;
import org.example.dto.ResponseList;
import org.example.rpovzi.tables.pojos.Call;
import org.example.service.AuditService;
import org.example.service.CallService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(value = "/calls")
@AllArgsConstructor
public class CallController {

    private final CallService callService;

    private final AuditService auditService;

    @GetMapping(value = "/list")
    public ResponseList<CallDto> getList(
            Principal principal,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        auditService.audit(principal, "/calls/list", "GET");
        return callService.getList(page, pageSize);
    }

    @GetMapping(value = "/{id}")
    public Call get(
            Principal principal,
            @PathVariable Long id) {
        auditService.audit(principal, "/calls/{id}", "GET");
        return callService.get(id);
    }

    @PostMapping
    public Call create(Principal principal, @RequestBody Call call) {
        auditService.audit(principal, "/calls/", "POST");
        return callService.create(call);
    }

    @PutMapping
    public Call update(Principal principal, @RequestBody Call call) {
        auditService.audit(principal, "/calls/", "PUT");
        return callService.update(call);
    }

    @DeleteMapping(value = "/{id}")
    public void create(Principal principal, @PathVariable Long id) {
        auditService.audit(principal, "/calls/{id}", "DELETE");
        callService.delete(id);
    }
}
