package org.example.service;

import lombok.AllArgsConstructor;
import org.example.repository.CallRepository;
import org.example.rpovzi.tables.daos.CallDao;
import org.example.rpovzi.tables.pojos.Abonent;
import org.example.rpovzi.tables.pojos.Call;
import org.jooq.Condition;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.jooq.impl.DSL.trueCondition;

@Service
@AllArgsConstructor
public class CallService {

    private final CallRepository CallRepository;

    private final CallDao CallDao;

    public List<Call> getList(Integer page, Integer pageSize) {

        Condition condition = trueCondition();

        return CallRepository.fetch(condition, page, pageSize);
    }

    public Call create(Call call){
        CallDao.insert(call);
        return call;
    }

    public Call update(Call call){
        CallDao.update(call);
        return call;
    }

    public void delete(Long id){
        CallDao.deleteById(id);
    }

    public Call get(Long id) {
        return CallDao.findById(id);
    }

}
