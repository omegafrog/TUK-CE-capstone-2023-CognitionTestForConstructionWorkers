package com.tukorea.cogTest.domain;

import java.util.List;
import java.util.Optional;

public interface NotPassedRepository {
    List<NotPassedCount> findByField_id(Long fieldId);
    NotPassedCount save(NotPassedCount entity);

}
