package com.blue.bluefood.domain.service;

import com.blue.bluefood.domain.filter.VendaDiariaFilter;


public interface VendaReportService {
	byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffset);
}
