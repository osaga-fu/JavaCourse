package com.example.domains.contracts.services;

import java.sql.Timestamp;
import java.util.List;

import com.example.domains.core.contracts.services.ProjectionDomainService;
import com.example.domains.entities.Language;

public interface LanguageService extends ProjectionDomainService<Language,Integer> {
	List<Language> novedades(Timestamp fecha);
}
