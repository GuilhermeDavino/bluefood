package com.blue.bluefood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blue.bluefood.domain.filter.VendaDiariaFilter;
import com.blue.bluefood.domain.model.dto.VendaDiaria;
import com.blue.bluefood.domain.service.VendaQueryService;
import com.blue.bluefood.domain.service.VendaReportService;

@RestController
@RequestMapping("/estatisticas")
public class EstatisticasController {
	
	@Autowired
	private VendaQueryService service;
	
	@Autowired
	private VendaReportService vendaReportService;
	
	@GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
		return service.consultarVendasDiarias(filtro, timeOffset);
	}
	
	@GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> consultarVendasDiariasPdf(VendaDiariaFilter filtro, @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
		byte[] bytesPdf = vendaReportService.emitirVendasDiarias(filtro, timeOffset);
		var headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diarias.pdf");
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_PDF)
				.headers(headers)
				.body(bytesPdf);
	}
}
