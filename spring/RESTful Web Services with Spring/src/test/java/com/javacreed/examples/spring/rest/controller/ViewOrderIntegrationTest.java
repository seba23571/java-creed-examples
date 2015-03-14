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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.javacreed.examples.spring.core.events.orders.RequestOrderDetailsEvent;
import com.javacreed.examples.spring.core.services.OrderService;
import com.javacreed.examples.spring.rest.controller.fixture.RestDataFixture;
import com.javacreed.examples.spring.rest.controller.fixture.RestEventFixtures;

public class ViewOrderIntegrationTest {

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
        .setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
  }

  @Test
  public void thatViewOrderRendersCorrectly() throws Exception {
    Mockito.when(orderService.requestOrderDetails(Matchers.any(RequestOrderDetailsEvent.class))).thenReturn(
        RestEventFixtures.orderDetailsEvent(key));

    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get("/aggregators/orders/{id}", key.toString()).accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.jsonPath("$.items['" + RestDataFixture.YUMMY_ITEM + "']").value(12))
        .andExpect(MockMvcResultMatchers.jsonPath("$.key").value(key.toString()));
  }

  @Test
  public void thatViewOrderUsesHttpNotFound() throws Exception {

    Mockito.when(orderService.requestOrderDetails(Matchers.any(RequestOrderDetailsEvent.class))).thenReturn(
        RestEventFixtures.orderDetailsNotFound(key));

    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get("/aggregators/orders/{id}", key.toString()).accept(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  public void thatViewOrderUsesHttpOK() throws Exception {

    Mockito.when(orderService.requestOrderDetails(Matchers.any(RequestOrderDetailsEvent.class))).thenReturn(
        RestEventFixtures.orderDetailsEvent(key));

    this.mockMvc.perform(
        MockMvcRequestBuilders.get("/aggregators/orders/{id}", key.toString()).accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

}
