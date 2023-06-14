package org.example.service;

import org.example.exception.ValidationException;
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

    public Abonent create(Abonent abonent) throws ValidationException {
        StringBuilder stringBuilder = new StringBuilder();
        if (!abonent.getPhoneNumber().matches("^\\d{11}$"))
        {
            stringBuilder.append("Неправильный номер телефона");
            stringBuilder.append("\n");
            throw new ValidationException(stringBuilder.toString());
        }
        if (!abonent.getInn().matches("^\\d{10}$"))
        {
            stringBuilder.append("Неправильный ИНН");
            stringBuilder.append("\n");
            throw new ValidationException(stringBuilder.toString());
        }
        if (abonent.getId()!=null)
            abonent.setId(null);
        abonentDao.insert(abonent);
        return abonent;
    }

    public Abonent update(Abonent abonent){
        StringBuilder stringBuilder = new StringBuilder();
        if (!abonent.getPhoneNumber().matches("^\\d{11}$"))
        {
            stringBuilder.append("Неправильный номер телефона");
            stringBuilder.append("\n");
            throw new ValidationException(stringBuilder.toString());
        }
        if (!abonent.getInn().matches("^\\d{10}$"))
        {
            stringBuilder.append("Неправильный ИНН");
            stringBuilder.append("\n");
            throw new ValidationException(stringBuilder.toString());
        }
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
