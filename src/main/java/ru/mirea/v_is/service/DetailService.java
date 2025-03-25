package ru.mirea.v_is.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.mirea.v_is.model.Detail;

public interface DetailService {

    Detail save(Detail detail);

    void delete(Long id);

    Page<Detail> getDetailByAuthUser(Pageable pageable);

    Detail findById(Long id);

}
