package com.smart.wccs.service;

import com.smart.wccs.model.Work;

import java.util.List;

public interface WorkService {

    List<Work> getAllWork();

    Work getById(Long id);

    void create(Work work);

    List<Work> createAll(List<Work> works);

    void delete(Long id);

}
