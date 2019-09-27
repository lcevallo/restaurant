package com.lc.api.restaurant.event.listener;

import com.lc.api.restaurant.event.RecursoCreadoEvent;
import java.net.URI;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationListener;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class RecursoCreadoListener implements ApplicationListener<RecursoCreadoEvent> {


  @Override
  public void onApplicationEvent(RecursoCreadoEvent recursoCreadoEvent) {
    HttpServletResponse response = recursoCreadoEvent.getResponse();
    Long codigo = recursoCreadoEvent.getCodigo();
    adicionarHeaderLocation(response,codigo);

  }

  private void adicionarHeaderLocation(HttpServletResponse response, Long codigo) {
    URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
        .buildAndExpand(codigo).toUri();
    response.setHeader("Location",uri.toASCIIString());
  }

}
