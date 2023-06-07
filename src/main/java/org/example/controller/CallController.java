package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.rpovzi.tables.pojos.Audit;
import org.example.rpovzi.tables.pojos.Call;
import org.example.service.AuditService;
import org.example.service.CallService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/calls")
@AllArgsConstructor
public class CallController {

    private final CallService callService;

    private final AuditService auditService;

    @GetMapping(value = "/list")
    public List<Call> getList(
            Principal principal,
            @RequestParam(value = "/page", defaultValue = "1") Integer page,
            @RequestParam(value = "/pageSize", defaultValue = "10") Integer pageSize) {
        auditService.create(new Audit(null, principal.getName(), "/calls/list", "GET", LocalDateTime.now()));
        return callService.getList(page, pageSize);
    }

    @GetMapping(value = "/{id}")
    public Call get(
            Principal principal,
            @PathVariable Long id) {
        auditService.create(new Audit(null, principal.getName(), "/calls/{id}", "GET", LocalDateTime.now()));
        return callService.get(id);
    }

    @PostMapping
    public Call create(Principal principal, @RequestBody Call call) {
        auditService.create(new Audit(null, principal.getName(), "/calls/", "POST", LocalDateTime.now()));
        return callService.create(call);
    }

    @PutMapping
    public Call update(Principal principal, @RequestBody Call call) {
        auditService.create(new Audit(null, principal.getName(), "/calls/", "PUT", LocalDateTime.now()));
        return callService.update(call);
    }

    @DeleteMapping(value = "/{id}")
    public void create(Principal principal, @PathVariable Long id) {
        auditService.create(new Audit(null, principal.getName(), "/calls/{id}", "DELETE", LocalDateTime.now()));
        callService.delete(id);
    }
}
