package org.example.service;

import lombok.AllArgsConstructor;
import org.example.repository.AuditRepository;
import org.example.rpovzi.tables.daos.AuditDao;
import org.example.rpovzi.tables.pojos.Audit;
import org.jooq.Condition;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.jooq.impl.DSL.trueCondition;

@Service
@AllArgsConstructor
public class AuditService {
    private final AuditDao auditDao;

    private final AuditRepository auditRepository;

    public Audit create(Audit audit) {
        auditDao.insert(audit);
        return audit;
    }

    public List<Audit> getList(Integer page, Integer pageSize) {

        Condition condition = trueCondition();

        return auditRepository.fetch(condition, page, pageSize);
    }

}
