package com.javacreed.examples.spring.rest.controller;

import java.util.UUID;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.javacreed.examples.spring.core.events.orders.DeleteOrderEvent;
import com.javacreed.examples.spring.core.services.OrderService;
import com.javacreed.examples.spring.rest.controller.fixture.RestEventFixtures;

public class CancelOrderIntegrationTest {

  MockMvc mockMvc;

  @InjectMocks
  OrderCommandsController controller;

  @Mock
  OrderService orderService;

  UUID key = UUID.fromString("f3512d26-72f6-4290-9265-63ad69eccc13");

  @Test
  public void thatDeleteOrderUsesHttpForbiddenOnEntityDeletionFailure() throws Exception {

    Mockito.when(orderService.deleteOrder(org.mockito.Matchers.any(DeleteOrderEvent.class))).thenReturn(
        RestEventFixtures.orderDeletedFailed(key));

    this.mockMvc
        .perform(
            MockMvcRequestBuilders.delete("/aggregators/orders/{id}", key.toString())
                .accept(MediaType.APPLICATION_JSON)).andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isForbidden());
  }

  @Test
  public void thatDeleteOrderUsesHttpNotFoundOnEntityLookupFailure() throws Exception {

    Mockito.when(orderService.deleteOrder(org.mockito.Matchers.any(DeleteOrderEvent.class))).thenReturn(
        RestEventFixtures.orderDeletedNotFound(key));

    this.mockMvc
        .perform(
            MockMvcRequestBuilders.delete("/aggregators/orders/{id}", key.toString())
                .accept(MediaType.APPLICATION_JSON)).andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isNotFound());

  }

  @Test
  public void thatDeleteOrderUsesHttpOkOnSuccess() throws Exception {

    Mockito.when(orderService.deleteOrder(org.mockito.Matchers.any(DeleteOrderEvent.class))).thenReturn(
        RestEventFixtures.orderDeleted(key));

    this.mockMvc
        .perform(
            MockMvcRequestBuilders.delete("/aggregators/orders/{id}", key.toString())
                .accept(MediaType.APPLICATION_JSON)).andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk());

    Mockito.verify(orderService).deleteOrder(
        org.mockito.Matchers.argThat(org.hamcrest.Matchers.<DeleteOrderEvent> hasProperty("key",
            org.hamcrest.Matchers.equalTo(key))));
  }

}
