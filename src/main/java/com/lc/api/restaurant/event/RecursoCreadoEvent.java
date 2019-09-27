package com.lc.api.restaurant.event;

import javax.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationEvent;

public class RecursoCreadoEvent extends ApplicationEvent {

  private static final long serialVersionUID = -4123350350614441206L;
  private HttpServletResponse response;
  private Long codigo;

  /**
   * Create a new ApplicationEvent.
   *
   * @param source the object on which the event initially occurred (never {@code null})
   */

  public RecursoCreadoEvent(Object source, HttpServletResponse response, Long codigo) {
    super(source);
    this.response= response;
    this.codigo = codigo;
  }

  public HttpServletResponse getResponse() {
    return response;
  }

  public Long getCodigo() {
    return codigo;
  }
}
