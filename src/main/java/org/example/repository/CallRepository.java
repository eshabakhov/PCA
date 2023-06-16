package org.example.repository;

import lombok.AllArgsConstructor;
import org.example.dto.CallDto;
import org.example.rpovzi.tables.pojos.Call;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static org.example.rpovzi.Tables.*;

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

    public Integer count(Condition condition) {
        return dslContext
                .selectCount()
                .from(CALL)
                .where(condition)
                .fetchOneInto(Integer.class);
    }

    public List<CallDto> fetchDtos(Condition condition, Integer page, Integer pageSize) {
        List<CallDto> result = new ArrayList<>();
        dslContext
                .select(CALL.ID, ABONENT.NAME, CITY.NAME, CALL.DATETIME, CALL.MINUTES)
                .from(CALL)
                .leftJoin(ABONENT)
                .on(ABONENT.ID.eq(CALL.ABONENT_ID))
                .leftJoin(CITY)
                .on(CITY.ID.eq(CALL.CITY_ID))
                .where(condition)
                .limit(pageSize)
                .offset((page - 1) * pageSize)
                .fetch()
                .forEach(obj -> {
                    CallDto callDto = new CallDto();
                    callDto.setId(obj.getValue(CALL.ID));
                    callDto.setAbonentName(obj.getValue(ABONENT.NAME));
                    callDto.setCityName(obj.getValue(CITY.NAME));
                    callDto.setDate(obj.getValue(CALL.DATETIME).toLocalDate());
                    callDto.setTime(obj.getValue(CALL.DATETIME).toLocalTime());
                    callDto.setMinutes(obj.getValue(CALL.MINUTES));
                    result.add(callDto);
                });
        return result;
    }
}
