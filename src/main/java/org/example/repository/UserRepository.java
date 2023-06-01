package org.example.repository;

import lombok.AllArgsConstructor;
import org.example.rpovzi.tables.pojos.User;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.postgresql.jdbc.EscapedFunctions.USER;

@Repository
@AllArgsConstructor
public class UserRepository {

    private final DSLContext dslContext;

    public List<User> fetch(Condition condition, Integer page, Integer pageSize) {
        return dslContext
                .selectFrom(USER)
                .where(condition)
                .limit(pageSize)
                .offset((page - 1) * pageSize)
                .fetchInto(User.class);
    }
}
