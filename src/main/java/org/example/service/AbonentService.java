package org.example.service;

import org.example.rpovzi.tables.daos.AbonentDao;
import org.example.rpovzi.tables.pojos.Abonent;
import lombok.AllArgsConstructor;
import org.example.repository.AbonentRepository;
import org.jooq.Condition;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.jooq.impl.DSL.trueCondition;

@Service
@AllArgsConstructor
public class AbonentService {

    private final AbonentRepository abonentRepository;

    private final AbonentDao abonentDao;

    public List<Abonent> getList(Integer page, Integer pageSize) {

        Condition condition = trueCondition();

        return abonentRepository.fetch(condition, page, pageSize);
    }

    public Abonent create(Abonent abonent){
        abonentDao.insert(abonent);
        return abonent;
    }

    public Abonent update(Abonent abonent){
        abonentDao.update(abonent);
        return abonent;
    }

    public void delete(Long id){
        abonentDao.deleteById(id);
    }

    public Abonent get(Long id) {
        return abonentDao.findById(id);
    }
}
