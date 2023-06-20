package org.example.service;

import lombok.AllArgsConstructor;
import org.example.dto.CallDto;
import org.example.dto.ResponseList;
import org.example.repository.CallRepository;
import org.example.rpovzi.tables.daos.CallDao;
import org.example.rpovzi.tables.pojos.Call;
import org.example.rpovzi.tables.pojos.City;
import org.jooq.Condition;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static org.jooq.impl.DSL.trueCondition;

@Service
@AllArgsConstructor
public class CallService {

    private final CallRepository callRepository;

    private final CityService cityService;

    private final CallDao callDao;

    public ResponseList<CallDto> getList(Integer page, Integer pageSize) {
        ResponseList<CallDto> responseList = new ResponseList<>();
        Condition condition = trueCondition();

        List<CallDto> list = callRepository.fetchDtos(condition, page, pageSize);

        responseList.setList(list);
        responseList.setTotal(callRepository.count(condition));
        responseList.setCurrentPage(page);
        responseList.setPageSize(pageSize);
        return responseList;
    }

    public Call create(Call call) {
        call.setPrice(calculateCallPrice(call));
        callDao.insert(call);
        return call;
    }

    public Call update(Call call) {
        call.setPrice(calculateCallPrice(call));
        callDao.update(call);
        return call;
    }

    public void delete(Long id) {
        callDao.deleteById(id);
    }

    public Call get(Long id) {
        return callDao.findById(id);
    }

    public BigDecimal calculateCallPrice(Call call) {
        BigDecimal price;
        City city = cityService.get(call.getCityId());
        if (city == null) {
            return BigDecimal.ZERO;
        }

        BigDecimal rate = call.getDatetime().getHour() >= 6 ? city.getDayRate() : city.getNightRate();

        price = rate.multiply(BigDecimal.valueOf(call.getMinutes()));
        if (call.getMinutes() > city.getDiscountCallMinutes()) {
            price = price.multiply(BigDecimal.valueOf((100 - city.getDiscountPercent()) / 100.0));
        }
        return price;
    }
}
