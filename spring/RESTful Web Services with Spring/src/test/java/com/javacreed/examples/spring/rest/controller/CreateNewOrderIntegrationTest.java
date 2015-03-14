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

import com.javacreed.examples.spring.core.events.orders.CreateOrderEvent;
import com.javacreed.examples.spring.core.services.OrderService;
import com.javacreed.examples.spring.rest.controller.fixture.RestDataFixture;
import com.javacreed.examples.spring.rest.controller.fixture.RestEventFixtures;

public class CreateNewOrderIntegrationTest {

  MockMvc mockMvc;

  @InjectMocks
  OrderCommandsController controller;

  @Mock
  OrderService orderService;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);

    this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
        .setMessageConverters(new MappingJackson2HttpMessageConverter()).build();

    Mockito.when(orderService.createOrder(Matchers.any(CreateOrderEvent.class))).thenReturn(
        RestEventFixtures.orderCreated(UUID.fromString("f3512d26-72f6-4290-9265-63ad69eccc13")));
  }

  // createOrder - validation?

  @Test
  public void thatCreateOrderPassesLocationHeader() throws Exception {

    this.mockMvc.perform(
        MockMvcRequestBuilders.post("/aggregators/orders").content(RestDataFixture.standardOrderJSON())
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(
        MockMvcResultMatchers.header().string("Location",
            Matchers.endsWith("/aggregators/orders/f3512d26-72f6-4290-9265-63ad69eccc13")));
  }

  @Test
  public void thatCreateOrderRendersAsJson() throws Exception {

    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post("/aggregators/orders").content(RestDataFixture.standardOrderJSON())
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.jsonPath("$.items['" + RestDataFixture.YUMMY_ITEM + "']").value(12))
        .andExpect(MockMvcResultMatchers.jsonPath("$.key").value("f3512d26-72f6-4290-9265-63ad69eccc13"));
  }

  @Test
  public void thatCreateOrderUsesHttpCreated() throws Exception {

    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post("/aggregators/orders").content(RestDataFixture.standardOrderJSON())
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isCreated());
  }
}
