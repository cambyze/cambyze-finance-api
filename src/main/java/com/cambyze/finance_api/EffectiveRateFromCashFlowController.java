package com.cambyze.finance_api;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.cambyze.finance.FinancialCalculation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

/**
 * REST API controller for the financial library
 * 
 * @author Thierry NESTELHUT
 * @author CAMBYZE
 * @see <a href="https://cambyze.com">Cambyze</a>
 * 
 */
@OpenAPIDefinition(
    info = @Info(title = "Cambyze financial service", version = "0.0",
        description = "Services to perform financial calculation",
        termsOfService = "https://cambyze.com/termsofservice/",
        license = @License(name = "Apache 2.0",
            url = "https://www.apache.org/licenses/LICENSE-2.0.html"),
        contact = @Contact(url = "https://cambyze.com/", name = "Cambyze support",
            email = "support@cambyze.com")),
    servers = {@Server(description = "Cambyze server", url = "https://cambyze.com/financial-api")})
@RestController
public class EffectiveRateFromCashFlowController {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(EffectiveRateFromCashFlowController.class);

  public EffectiveRateFromCashFlowController() {
    super();
  }

  @GetMapping("/effectiveRateFromCashFlow")
  @Operation(summary = "Get effective rate",
      description = "Get the effective rate from the cashflow (drawdowns and repayments")
  public BigDecimal calculateRate(
      @RequestBody EffectiveRateFromCashFlow effectiveRateFromCashFlow) {
    DateTimeFormatter europeanDateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    LinkedHashMap<LocalDate, BigDecimal> cashFlow = new LinkedHashMap<LocalDate, BigDecimal>();

    // Add the drawdowns to the cashflow
    Iterable<Payment> paymentsCollection = effectiveRateFromCashFlow.getDrawdowns();
    for (Payment p : paymentsCollection) {
      // Drawdowns are negative
      cashFlow.put(LocalDate.parse(p.getDate(), europeanDateFormatter),
          BigDecimal.valueOf(p.getAmount() * -1.0));
    }

    // Add the repayments to the cashflow
    paymentsCollection = effectiveRateFromCashFlow.getRepayments();
    for (Payment p : paymentsCollection) {
      // repayments are positive
      cashFlow.put(LocalDate.parse(p.getDate(), europeanDateFormatter),
          BigDecimal.valueOf(p.getAmount()));
    }

    LOGGER.info(
        "cashFlow :" + cashFlow + " / isActualDay: " + effectiveRateFromCashFlow.getIsActualDays());

    BigDecimal rate = FinancialCalculation.effectiveRateFromCashFlow(cashFlow,
        effectiveRateFromCashFlow.getIsActualDays());

    LOGGER.info("Calculated rate: " + rate.doubleValue());

    return rate;
  }

}
