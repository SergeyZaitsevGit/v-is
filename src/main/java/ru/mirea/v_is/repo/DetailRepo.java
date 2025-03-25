package ru.mirea.v_is.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.v_is.model.Detail;
import ru.mirea.v_is.model.User;

public interface DetailRepo extends JpaRepository<Detail, Long> {
    Page<Detail> getDetailByUser(User user, Pageable pageable);
}
