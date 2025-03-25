package ru.mirea.v_is.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.mirea.v_is.model.Detail;
import ru.mirea.v_is.model.User;
import ru.mirea.v_is.repo.DetailRepo;

@Service
@RequiredArgsConstructor
public class DetailServiceImpl implements DetailService {

    private final UserService userService;
    private final DetailRepo detailRepo;

    @Override
    @Transactional
    public Detail save(Detail detail) {
        detail.setUser(userService.getAuthenticationUser());
        return detailRepo.save(detail);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        detailRepo.deleteById(id);
    }

    @Override
    @Transactional
    public Page<Detail> getDetailByAuthUser(Pageable pageable) {
        return detailRepo.getDetailByUser(userService.getAuthenticationUser(), pageable);
    }

    @Override
    public Detail findById(Long id) {
        return detailRepo.findById(id).orElseThrow(() -> new RuntimeException("Detail not found"));
    }
}
