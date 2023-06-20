package org.example.repository;

import lombok.AllArgsConstructor;
import org.example.rpovzi.tables.pojos.City;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.example.rpovzi.Tables.CITY;

@Repository
@AllArgsConstructor
public class CityRepository {

    private final DSLContext dslContext;

    public List<City> fetch(Condition condition, Integer page, Integer pageSize) {
        return dslContext
                .selectFrom(CITY)
                .where(condition)
                .limit(pageSize)
                .offset((page - 1) * pageSize)
                .fetchInto(City.class);
    }

    public Integer count(Condition condition) {
        return dslContext
                .selectCount()
                .from(CITY)
                .where(condition)
                .fetchOneInto(Integer.class);
    }
}
