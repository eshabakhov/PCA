package org.example.service;

import lombok.AllArgsConstructor;
import org.example.repository.AuditRepository;
import org.example.rpovzi.tables.daos.AuditDao;
import org.example.rpovzi.tables.pojos.Audit;
import org.example.rpovzi.tables.pojos.User;
import org.jooq.Condition;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static org.jooq.impl.DSL.trueCondition;

@Service
@AllArgsConstructor
public class AuditService {
    private final AuditDao auditDao;

    private final AuditRepository auditRepository;

    private final UserService userService;

    public Audit create(Audit audit) {
        auditDao.insert(audit);
        return audit;
    }

    public List<Audit> getList(Integer page, Integer pageSize) {

        Condition condition = trueCondition();

        return auditRepository.fetch(condition, page, pageSize);
    }

    public void audit(Principal principal, String endpoint, String method) {
        User user = userService.getByUsername(principal.getName());
        if (decideAudit(user, method)) {
            create(new Audit(null, principal.getName(), endpoint, method, LocalDateTime.now()));
        }

    }

    private Boolean decideAudit(User user, String method) {
        if (user != null && user.getIsAdmin() && Objects.equals(method, "GET")) {
            return false;
        }

        return true;
    }
}
