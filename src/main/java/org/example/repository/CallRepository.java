package org.example.repository;

import lombok.AllArgsConstructor;
import org.example.rpovzi.tables.pojos.Call;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.example.rpovzi.Tables.CALL;

@Repository
@AllArgsConstructor
public class CallRepository {

    private final DSLContext dslContext;

    public List<Call> fetch(Condition condition, Integer page, Integer pageSize) {
        return dslContext
                .selectFrom(CALL)
                .where(condition)
                .limit(pageSize)
                .offset((page - 1) * pageSize)
                .fetchInto(Call.class);
    }
}
