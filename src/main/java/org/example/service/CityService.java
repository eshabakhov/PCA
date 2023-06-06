package org.example.service;

import lombok.AllArgsConstructor;
import org.example.repository.CityRepository;
import org.example.rpovzi.tables.daos.CityDao;
import org.example.rpovzi.tables.pojos.Call;
import org.example.rpovzi.tables.pojos.City;
import org.jooq.Condition;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.jooq.impl.DSL.trueCondition;

@Service
@AllArgsConstructor
public class CityService {

    private final CityRepository CityRepository;

    private final CityDao CityDao;

    public List<City> getList(Integer page, Integer pageSize) {

        Condition condition = trueCondition();

        return CityRepository.fetch(condition, page, pageSize);
    }

    public City create(City City){
        CityDao.insert(City);
        return City;
    }

    public City update(City City){
        CityDao.update(City);
        return City;
    }

    public void delete(Long id){
        CityDao.deleteById(id);
    }

    public City get(Long id) {
        return CityDao.findById(id);
    }

}
