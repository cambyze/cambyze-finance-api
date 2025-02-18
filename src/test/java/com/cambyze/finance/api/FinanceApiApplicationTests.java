package com.cambyze.finance.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import javax.ws.rs.core.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class FinanceApiApplicationTests {

  @Autowired
  private MockMvc mockMvc;

  private String regBody = "{\r\n" + "    \"drawdowns\" : [\r\n"
      + "        {\"date\":\"01/01/2020\",\"amount\":300000.0}\r\n" + "    ],\r\n"
      + "    \"repayments\" : [\r\n" + "        {\"date\":\"01/02/2020\",\"amount\":51764.51},\r\n"
      + "        {\"date\":\"01/03/2020\",\"amount\":51764.51},\r\n"
      + "        {\"date\":\"01/04/2020\",\"amount\":51764.51},\r\n"
      + "        {\"date\":\"01/05/2020\",\"amount\":51764.51},\r\n"
      + "        {\"date\":\"01/06/2020\",\"amount\":51764.51},\r\n"
      + "        {\"date\":\"01/07/2020\",\"amount\":51764.51}\r\n" + "    ],\r\n"
      + "    \"isActualDays\" : false    \r\n" + "}";

  @Test
  public void testGetEffectiveRateFromCashFlow() throws Exception {
    mockMvc.perform(get("/effectiveRateFromCashFlow")).andExpect(status().isBadRequest());
    mockMvc.perform(
        get("/effectiveRateFromCashFlow").contentType(MediaType.APPLICATION_JSON).content(regBody))
        .andExpect(status().isOk()).andExpect(content().string("12.6825"));
  }

  private String regBody2 = "{\"startdate\":\"01/01/2020\",\"effectiveRate\":20.0,\r\n"
      + "    \"drawdowns\" : [],\r\n" + "    \"repayments\" : [\r\n"
      + "        {\"date\":\"01/01/2021\",\"amount\":120000.0}\r\n" + "    ],\r\n"
      + "    \"isActualDays\" : false    \r\n" + "}";

  private String regBody3 = "{\"startdate\":\"01/01/2020\",\"effectiveRate\":12.6825,\r\n"
      + "    \"drawdowns\" : [],\r\n" + "    \"repayments\" : [\r\n"
      + "        {\"date\":\"01/02/2020\",\"amount\":51764.51},\r\n"
      + "        {\"date\":\"01/03/2020\",\"amount\":51764.51},\r\n"
      + "        {\"date\":\"01/04/2020\",\"amount\":51764.51},\r\n"
      + "        {\"date\":\"01/05/2020\",\"amount\":51764.51},\r\n"
      + "        {\"date\":\"01/06/2020\",\"amount\":51764.51},\r\n"
      + "        {\"date\":\"01/07/2020\",\"amount\":51764.51}\r\n" + "    ],\r\n"
      + "    \"isActualDays\" : false    \r\n" + "}";

  @Test
  public void testGetNPVFromCashFlow() throws Exception {
    mockMvc.perform(get("/netPresentValueFromCashFlow")).andExpect(status().isBadRequest());
    mockMvc.perform(get("/netPresentValueFromCashFlow").contentType(MediaType.APPLICATION_JSON)
        .content(regBody2)).andExpect(status().isOk()).andExpect(content().string("100000.0"));
    mockMvc.perform(get("/netPresentValueFromCashFlow").contentType(MediaType.APPLICATION_JSON)
        .content(regBody3)).andExpect(status().isOk()).andExpect(content().string("300000.0"));
  }

}

