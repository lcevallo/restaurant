package com.lc.api.restaurant.exceptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.TransientPropertyValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestaurantExceptionHandler extends ResponseEntityExceptionHandler {

  @Autowired
  private MessageSource messageSource;

  protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request){

    String mensajeUsuario =	messageSource.getMessage("mensaje.invalido", null, LocaleContextHolder.getLocale());
    String mensajeDesarrollador = ex.getCause()!=null? ex.getCause().toString() :ex.toString() ;

    List<Error> errores = Arrays.asList(new Error(mensajeUsuario,mensajeDesarrollador));

    return handleExceptionInternal(ex, errores, headers, HttpStatus.BAD_REQUEST , request);
  }

  @ExceptionHandler({NoSuchElementException.class})
  public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException ex,
         WebRequest request){

    String mensajeUsuario=messageSource.getMessage("recurso.no-encontrado",null, LocaleContextHolder.getLocale());
    String mensajeDesarrollador= ExceptionUtils.getRootCauseMessage(ex);
    List<Error> errores= Arrays.asList(new Error(mensajeUsuario,mensajeDesarrollador));

    return handleExceptionInternal(ex, errores, new HttpHeaders(),HttpStatus.NOT_FOUND, request);
  }

  protected ResponseEntity<Object> handleMethodArgumentNotValid (MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request){

    List<Error> errores = crearListaDeErrores(ex.getBindingResult());

    return handleExceptionInternal(ex,errores,headers, HttpStatus.BAD_REQUEST, request);

  }

  @ExceptionHandler({EmptyResultDataAccessException.class})
  public ResponseEntity<Object> handleEmptyResultDataAccessException(
      EmptyResultDataAccessException ex ,WebRequest request)
  {

    String mensajeUsuario=messageSource.getMessage("recurso.no-encontrado",null, LocaleContextHolder.getLocale());
    String mensajeDesarrollador=ExceptionUtils.getRootCauseMessage(ex);
    List<Error> errores= Arrays.asList(new Error(mensajeUsuario,mensajeDesarrollador));

    return handleExceptionInternal(ex, errores, new HttpHeaders(),HttpStatus.NOT_FOUND, request);
  }


  @ExceptionHandler({TransientPropertyValueException.class})
  public ResponseEntity<Object> handleTransientPropertyValueException(TransientPropertyValueException ex,WebRequest request )
  {
    String mensajeUsuario=messageSource.getMessage("valor.no-seteado",null, LocaleContextHolder.getLocale());
    String mensajeDesarrollador=ExceptionUtils.getRootCauseMessage(ex);
    List<Error> errores= Arrays.asList(new Error(mensajeUsuario,mensajeDesarrollador));

    return handleExceptionInternal(ex, errores, new HttpHeaders(),HttpStatus.NOT_FOUND, request);
   }

  private List<Error> crearListaDeErrores(BindingResult bindingResult) {
    List<Error> errores= new ArrayList<>();

    bindingResult.getFieldErrors().forEach(fieldError -> {
      String mensajeUsuario = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
      String mensajeDesarrollador = fieldError.toString();
      errores.add(new Error(mensajeUsuario, mensajeDesarrollador));
    });

    return errores;

  }




  public static class Error{
    private String mensajeUsuario;
    private String mensajeDesarrollador;


    public Error(String mensajeUsuario, String mensajeDesarrollador) {
      this.mensajeUsuario = mensajeUsuario;
      this.mensajeDesarrollador = mensajeDesarrollador;
    }

    public String getMensajeUsuario() {
      return mensajeUsuario;
    }

    public String getMensajeDesarrollador() {
      return mensajeDesarrollador;
    }
  }

}
