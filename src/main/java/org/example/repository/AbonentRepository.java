package org.example.repository;

import lombok.AllArgsConstructor;
import org.example.rpovzi.tables.pojos.Abonent;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.example.rpovzi.Tables.ABONENT;

@Repository
@AllArgsConstructor
public class AbonentRepository {

    private final DSLContext dslContext;

    public List<Abonent> fetch(Condition condition, Integer page, Integer pageSize) {
        return dslContext
                .selectFrom(ABONENT)
                .where(condition)
                .limit(pageSize)
                .offset((page - 1) * pageSize)
                .fetchInto(Abonent.class);
    }

    public Integer count(Condition condition) {
        return dslContext
                .selectCount()
                .from(ABONENT)
                .where(condition)
                .fetchOneInto(Integer.class);
    }
}
