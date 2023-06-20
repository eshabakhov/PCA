package org.example.service;

import lombok.AllArgsConstructor;
import org.example.dto.ResponseList;
import org.example.repository.CityRepository;
import org.example.rpovzi.tables.daos.CityDao;
import org.example.rpovzi.tables.pojos.City;
import org.jooq.Condition;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.jooq.impl.DSL.trueCondition;

@Service
@AllArgsConstructor
public class CityService {

    private final CityRepository cityRepository;

    private final CityDao cityDao;

    public ResponseList<City> getList(Integer page, Integer pageSize) {
        ResponseList<City> responseList = new ResponseList<>();
        Condition condition = trueCondition();

        List<City> list = cityRepository.fetch(condition, page, pageSize);

        responseList.setList(list);
        responseList.setTotal(cityRepository.count(condition));
        responseList.setCurrentPage(page);
        responseList.setPageSize(pageSize);
        return responseList;
    }

    public City create(City City) {
        cityDao.insert(City);
        return City;
    }

    public City update(City City) {
        cityDao.update(City);
        return City;
    }

    public void delete(Long id) {
        cityDao.deleteById(id);
    }

    public City get(Long id) {
        return cityDao.findById(id);
    }

}
