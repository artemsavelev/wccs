package com.smart.wccs.service;

import com.smart.wccs.model.Work;

import java.util.List;

public interface WorkService {

    List<Work> getAllWork();

    List<Work> getAllWorkForAdmin();

    Work getById(Long id);

    void create(Work work);

    void update(Long id, Work work);

    List<Work> createAll(List<Work> works);

    void delete(Long id);

}
