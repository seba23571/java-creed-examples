package com.javacreed.examples.spring.rest.controller;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.javacreed.examples.spring.core.events.orders.RequestOrderDetailsEvent;
import com.javacreed.examples.spring.core.services.OrderService;
import com.javacreed.examples.spring.rest.controller.fixture.RestEventFixtures;

public class ViewOrderXmlIntegrationTest {

  MockMvc mockMvc;

  @InjectMocks
  OrderQueriesController controller;

  @Mock
  OrderService orderService;

  UUID key = UUID.fromString("f3512d26-72f6-4290-9265-63ad69eccc13");

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);

    this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
        .setMessageConverters(new MappingJackson2HttpMessageConverter(), new Jaxb2RootElementHttpMessageConverter())
        .build();
  }

  @Test
  public void thatViewOrderRendersJsonCorrectly() throws Exception {

    Mockito.when(orderService.requestOrderDetails(Matchers.any(RequestOrderDetailsEvent.class))).thenReturn(
        RestEventFixtures.orderDetailsEvent(key));

    // TODOCUMENT JSON Path in use here (really like this)

    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get("/aggregators/orders/{id}", key.toString()).accept(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.jsonPath("$.key").value(key.toString()));
  }

  @Test
  public void thatViewOrderRendersXMLCorrectly() throws Exception {

    Mockito.when(orderService.requestOrderDetails(Matchers.any(RequestOrderDetailsEvent.class))).thenReturn(
        RestEventFixtures.orderDetailsEvent(key));

    this.mockMvc
        .perform(MockMvcRequestBuilders.get("/aggregators/orders/{id}", key.toString()).accept(MediaType.TEXT_XML))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.TEXT_XML))
        .andExpect(MockMvcResultMatchers.xpath("/order/key").string(key.toString()));
  }
}
