package com.smart.wccs.service;

import com.smart.wccs.model.SectionGroup;

import java.util.List;

public interface SectionGroupService {

    List<SectionGroup> getAllGroup();

    SectionGroup getById(Long id);
}
