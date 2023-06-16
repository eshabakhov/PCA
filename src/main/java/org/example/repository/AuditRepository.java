package org.example.repository;

import lombok.AllArgsConstructor;
import org.example.rpovzi.tables.pojos.Audit;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.example.rpovzi.Tables.AUDIT;

@Repository
@AllArgsConstructor
public class AuditRepository {
    private final DSLContext dslContext;

    public List<Audit> fetch(Condition condition, Integer page, Integer pageSize) {
        return dslContext
                .selectFrom(AUDIT)
                .where(condition)
                .limit(pageSize)
                .offset((page - 1) * pageSize)
                .fetchInto(Audit.class);
    }

    public Integer count(Condition condition) {
        return dslContext
                .selectCount()
                .from(AUDIT)
                .where(condition)
                .fetchOneInto(Integer.class);
    }
}
