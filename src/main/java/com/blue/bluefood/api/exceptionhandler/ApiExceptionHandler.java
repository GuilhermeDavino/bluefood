package com.blue.bluefood.api.exceptionhandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.blue.bluefood.core.validation.ValidacaoException;
import com.blue.bluefood.domain.exception.EntidadeEmUsoException;
import com.blue.bluefood.domain.exception.EntidadeNaoEncontradaException;
import com.blue.bluefood.domain.exception.NegocioException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	private MessageSource messageSource;
	
	private static final String MSG_ERRO_GENERICA_USUARIO_FINAL = "Ocorreu um erro interno inesperado no sistema. "
			+ "Tente novamente e se o problema persistir, entre em contato "
			+ "com o administrador do sistema";
	
	@ExceptionHandler(ValidacaoException.class)
	public ResponseEntity<Object> handleValidacaoException(ValidacaoException exception, WebRequest request) {
		List<Problem.Object> problemErros = 
				exception.getBindResult().getFieldErrors().stream()
				.map(objectError -> {
					String name = objectError.getObjectName();
					
					if(objectError instanceof FieldError) {
						name = ((FieldError) objectError).getField();
					}
					
					String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
					
					return Problem.Object.builder()
							.name(name)
							.userMessage(message)
							.build();
				}).collect(Collectors.toList());
		
		ProblemType problemType = ProblemType.DADOS_INVALIDOS;
		String detail = "Um ou mais campos estão inválidos. "
				+ "Faça o preenchimento correto e tente novamente.";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(detail)
				.timestamp(LocalDateTime.now())
				.objects(problemErros).build();
		
		return super.handleExceptionInternal(exception, problem, new HttpHeaders(), status, request);
	}
			
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, 
			HttpStatus status, WebRequest request) {
		
		List<Problem.Object> problemErros = ex.getBindingResult()
				.getAllErrors()
				.stream().map(objectError -> {
						
					String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
					
					String name = objectError.getObjectName();
					
					if(objectError instanceof FieldError) {
						name = ((FieldError) objectError).getField();
					}
					
					return Problem.Object.builder()
					.name(name)
					.userMessage(message)
					.build();
						
				}).collect(Collectors.toList());
		
		ProblemType problemType = ProblemType.DADOS_INVALIDOS;
		String detail = "Um ou mais campos estão inválidos. "
				+ "Faça o preenchimento correto e tente novamente.";
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(detail)
				.timestamp(LocalDateTime.now())
				.objects(problemErros).build();
		
		return super.handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleUncaughtException(Exception exception, WebRequest request) {
		ProblemType problemType = ProblemType.ERRO_DE_SISTEMA;
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		HttpHeaders headers = new HttpHeaders();
		String detail = MSG_ERRO_GENERICA_USUARIO_FINAL;
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(detail)
				.timestamp(LocalDateTime.now()).build();
		return super.handleExceptionInternal(exception, problem, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		String recurso = ex.getRequestURL();
		
		ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
		String detail = String.format("O recurso %s, que você tentou acessar é inexistente", recurso);
		status = HttpStatus.NOT_FOUND;
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(detail)
				.timestamp(LocalDateTime.now()).build();
		
		return super.handleExceptionInternal(ex, problem, headers, status, request);
	}

	protected ResponseEntity<Object> handleTypeMismatch(
			TypeMismatchException exception, HttpHeaders headers, 
			HttpStatus status, WebRequest request) {
		
		if (exception instanceof MethodArgumentTypeMismatchException) {
			return handleMethodArgumentTypeMismatchException(
					(MethodArgumentTypeMismatchException) exception, 
					headers, status, request);
		}
		
		return super.handleTypeMismatch(exception, headers, status, request);
	}
	
	public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(
			MethodArgumentTypeMismatchException exception, 
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String name = exception.getName();
		String value = exception.getValue().toString();
		String typeRequired = exception.getRequiredType().getSimpleName();
		
		ProblemType problemType = ProblemType.PARAMETRO_INVALIDO;
		String detail = String.format("O parâmetro de URL %s recebeu o valor '%s', "
				+ "que é um tipo inválido por favor corrija e informe um valor"
				+ " compativel com o tipo %s", name, value, typeRequired);
		String userMessage = String.format("O parâmetro de URL %s recebeu o valor '%s', "
				+ "que é um tipo inválido por favor corrija e informe um valor"
				+ " compativel", name, value);
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		
		Problem problem = createProblemBuilder(httpStatus, problemType, detail)
				.userMessage(userMessage)
				.timestamp(LocalDateTime.now()).build();
		
		return super.handleExceptionInternal(exception, problem, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException exception,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		Throwable rootCause = ExceptionUtils.getRootCause(exception);
		
		if (rootCause instanceof InvalidFormatException) {
			return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
		}
		
		if (rootCause instanceof PropertyBindingException) {
			return handlePropertyBindingException((PropertyBindingException) rootCause, headers, status, request);
		}
		
		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		String detail = "O corpo da requisição está inválido. Verifique erro de sintaxe";
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
				.timestamp(LocalDateTime.now()).build();
		
		return handleExceptionInternal(exception, problem,
				new HttpHeaders(), status, request);
	}
	
	private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException rootCause, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		status = HttpStatus.BAD_REQUEST;
		String path = rootCause.getPath().stream()
		.map(reference -> reference.getFieldName())
		.collect(Collectors.joining("."));
		ProblemType problemType = ProblemType.ERRO_PROPRIEDADE_JSON;
		String detail = String.format("Propriedade inválida. Corrigir os erros na propriedade %s", path);
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
				.timestamp(LocalDateTime.now()).build();
		return handleExceptionInternal(rootCause, problem, headers, status, request);
	}

	private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException exception,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		
		String path = exception.getPath().stream()
				.map(reference -> reference.getFieldName())
				.collect(Collectors.joining("."));
		
		String detail = String.format("A propriedade '%s' recebeu o valor '%s', "
				+ "que é um tipo inválido. Corrija e "
				+ "informe um valor compatível com o tipo %s.",
				path, exception.getValue(), exception.getTargetType().getName());
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
				.timestamp(LocalDateTime.now()).build();
		
		return handleExceptionInternal(exception, problem, headers, status, request);
	}

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> handleEntidadeNaoEncontradaException(
			EntidadeNaoEncontradaException exception, WebRequest request) {
		
		HttpStatus http = HttpStatus.NOT_FOUND;
		ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
		String detail = exception.getMessage();
		Problem problem = createProblemBuilder(http, problemType, detail)
				.userMessage(detail)
				.timestamp(LocalDateTime.now()).build();
		
		
		return handleExceptionInternal(exception, problem,
				new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> handleNegocioException(NegocioException exception, WebRequest request) {
		HttpStatus http = HttpStatus.BAD_REQUEST;
		ProblemType problemType = ProblemType.ERRO_NEGOCIO;
		Problem problem = createProblemBuilder(http, problemType, exception.getMessage())
				.userMessage(exception.getMessage())
				.timestamp(LocalDateTime.now()).build();
		
		
		return handleExceptionInternal(exception, problem, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException exception, WebRequest request) {
		HttpStatus http = HttpStatus.CONFLICT;
		ProblemType problemType = ProblemType.ENTITDADE_EM_USO;
		
		Problem problem = createProblemBuilder(http, problemType, exception.getMessage())
				.userMessage(exception.getMessage())
				.timestamp(LocalDateTime.now()).build();
		
		return handleExceptionInternal(exception, problem,
				new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception exception, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		if (body == null) {
			
			body = Problem.builder()
					.title(status.getReasonPhrase())
					.status(status.value()).build();
			
		} else if (body instanceof String) {
			
			body = Problem.builder()
					.title((String) body)
					.status(status.value())
					.build();
		}
		
		
		return super.handleExceptionInternal(exception, body, headers, status, request);
	}
	
	private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, 
			ProblemType problem, String detail) {
		
		return Problem.builder()
				.status(status.value())
				.type(problem.getUri())
				.title(problem.getTitle())
				.detail(detail);
	
	}
}
