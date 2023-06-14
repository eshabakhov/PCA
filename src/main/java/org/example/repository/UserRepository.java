package org.example.repository;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.rpovzi.tables.pojos.User;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.example.rpovzi.Tables.USER;


@Repository
@RequiredArgsConstructor
public class UserRepository {

    @Value("${auth.attempts.reset.minutes}")
    private Integer attemptsResetMinutes;

    private final DSLContext dslContext;

    public List<User> fetch(Condition condition, Integer page, Integer pageSize) {
        return dslContext
                .selectFrom(USER)
                .where(condition)
                .limit(pageSize)
                .offset((page - 1) * pageSize)
                .fetchInto(User.class);
    }

    public User fetchActual(String login) {
        return dslContext
                .selectFrom(USER)
                .where(USER.LOGIN.eq(login))
                .fetchOneInto(User.class);
    }

    public void resetLocks() {
        dslContext
                .update(USER)
                .set(USER.IS_LOCKED, false)
                .setNull(USER.DATETIME_OF_LOCK)
                .where(USER.DATETIME_OF_LOCK.lessOrEqual(LocalDateTime.now().minusMinutes(attemptsResetMinutes)))
                .execute();
    }

    public void setLock(String login, LocalDateTime now){
        dslContext
                .update(USER)
                .set(USER.IS_LOCKED, true)
                .set(USER.DATETIME_OF_LOCK, now)
                .where(USER.LOGIN.eq(login))
                .execute();
    }
}
