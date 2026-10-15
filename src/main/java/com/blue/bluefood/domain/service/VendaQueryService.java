package com.blue.bluefood.domain.service;

import java.util.List;

import com.blue.bluefood.domain.filter.VendaDiariaFilter;
import com.blue.bluefood.domain.model.dto.VendaDiaria;


public interface VendaQueryService {
	
	List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filter, String timeOffset);
	
	
}
