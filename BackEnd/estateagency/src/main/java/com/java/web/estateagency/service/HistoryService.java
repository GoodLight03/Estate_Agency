package com.java.web.estateagency.service;

import java.util.List;

import com.java.web.estateagency.entity.History;

public interface HistoryService {
    List<History> getByCusumer(Long id);
}
