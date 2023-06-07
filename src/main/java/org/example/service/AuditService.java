package org.example.service;

import lombok.AllArgsConstructor;
import org.example.rpovzi.tables.daos.AuditDao;
import org.example.rpovzi.tables.pojos.Audit;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuditService {
    private final AuditDao auditDao;

    public Audit create(Audit audit) {
        auditDao.insert(audit);
        return audit;
    }

}
