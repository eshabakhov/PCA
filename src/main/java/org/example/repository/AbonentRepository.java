package org.example.repository;

import com.example.rpovzi.tables.pojos.Abonent;
import lombok.AllArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.rpovzi.Tables.ABONENT;

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
}
