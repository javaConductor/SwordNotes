package org.swordexplorer.notes.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Slf4j
@Component
public class EndpointsListener implements ApplicationListener<ContextRefreshedEvent> {

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    ApplicationContext applicationContext = event.getApplicationContext();
    applicationContext.getBean(RequestMappingHandlerMapping.class).getHandlerMethods()
      .forEach((requestMappingInfo, handlerMethod) -> log.info("REQUEST MAPPING :>> {}", requestMappingInfo.toString()));
  }
}
