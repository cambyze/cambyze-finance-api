package com.cambyze.finance.api;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
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
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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


  private static final String DESC_GET_EFFECTIVE_RATE =
      "#/components/schemas/EffectiveRateFromCashFlow = The cashflow (drawdowns and repayments) and the calculation base (true=real/365,false=30/360). "
          + "Drawdowns and repayments are Array<Payment> (date at the format 'DD/MM/YYYY', amount as a double)";

  @GET
  @Consumes("application/json")
  @Operation(summary = "Get effective rate",
      description = "Get the effective rate from the cashflow (drawdowns and repayments)",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = DESC_GET_EFFECTIVE_RATE, required = true,
          content = @Content(mediaType = "application/json",
              contentSchema = @Schema(implementation = EffectiveRateFromCashFlow.class),
              array = @ArraySchema(schema = @Schema(implementation = Payment.class)))),
      responses = {@ApiResponse(description = "The calculated rate",
          content = @Content(mediaType = "double"))})

  @Path("/effectiveRateFromCashFlow")
  @GetMapping("/effectiveRateFromCashFlow")
  public double calculateRate(@RequestBody EffectiveRateFromCashFlow effectiveRateFromCashFlow) {
    DateTimeFormatter europeanDateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    LinkedHashMap<LocalDate, BigDecimal> cashFlow = new LinkedHashMap<LocalDate, BigDecimal>();

    // Add the drawdowns to the cashflow
    Iterable<Payment> drawdowns = effectiveRateFromCashFlow.getDrawdowns();
    for (Payment p : drawdowns) {
      // Drawdowns are negative
      cashFlow.put(LocalDate.parse(p.getDate(), europeanDateFormatter),
          BigDecimal.valueOf(p.getAmount() * -1.0));
    }

    Iterable<Payment> repayments = effectiveRateFromCashFlow.getRepayments();
    // Add the repayments to the cashflow
    for (Payment p : repayments) {
      // repayments are positive
      cashFlow.put(LocalDate.parse(p.getDate(), europeanDateFormatter),
          BigDecimal.valueOf(p.getAmount()));
    }

    LOGGER.info(
        "cashFlow :" + cashFlow + " / isActualDay: " + effectiveRateFromCashFlow.getIsActualDays());

    BigDecimal rate = FinancialCalculation.effectiveRateFromCashFlow(cashFlow,
        effectiveRateFromCashFlow.getIsActualDays());

    LOGGER.info("Calculated rate: " + rate.doubleValue());

    return rate.doubleValue();
  }

  private static final String DESC_GET_NET_PRESENT_VALUE =
      "#/components/schemas/NetPresentValueCashFlow = The date of discounting + the discount effective rate + the cashflow (drawdowns and repayments) and the calculation base (true=real/365,false=30/360). "
          + "The start date (date of the discounting) at the format 'DD/MM/YYYY'. "
          + "Drawdowns and repayments are Array<Payment> (date at the format 'DD/MM/YYYY', amount as a double)";


  @GET
  @Consumes("application/json")
  @Operation(summary = "Get Net Present Value",
      description = "Get the Net Present Value from the start date and the effective rate for the discounting of the cashflow (drawdowns and repayments)",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = DESC_GET_NET_PRESENT_VALUE, required = true,
          content = @Content(mediaType = "application/json",
              contentSchema = @Schema(implementation = NetPresentValueCashFlow.class),
              array = @ArraySchema(schema = @Schema(implementation = Payment.class)))),
      responses = {@ApiResponse(description = "The Net Present Value",
          content = @Content(mediaType = "double"))})

  @Path("/netPresentValueFromCashFlow")
  @GetMapping("/netPresentValueFromCashFlow")
  public double calculateNPV(@RequestBody NetPresentValueCashFlow netPresentValueCashFlow) {
    DateTimeFormatter europeanDateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    LinkedHashMap<LocalDate, BigDecimal> cashFlow = new LinkedHashMap<LocalDate, BigDecimal>();

    // Add the start date to the cashflow with the amount of zero
    // This amount will be the calculated NPV
    Payment startPayment = new Payment(netPresentValueCashFlow.getStartdate(), 0.0);
    cashFlow.put(LocalDate.parse(startPayment.getDate(), europeanDateFormatter),
        BigDecimal.valueOf(startPayment.getAmount()));

    // Add the drawdowns to the cashflow
    Iterable<Payment> drawdowns = netPresentValueCashFlow.getDrawdowns();
    for (Payment p : drawdowns) {
      // Drawdowns are negative
      cashFlow.put(LocalDate.parse(p.getDate(), europeanDateFormatter),
          BigDecimal.valueOf(p.getAmount() * -1.0));
    }

    Iterable<Payment> repayments = netPresentValueCashFlow.getRepayments();
    // Add the repayments to the cashflow
    for (Payment p : repayments) {
      // repayments are positive
      cashFlow.put(LocalDate.parse(p.getDate(), europeanDateFormatter),
          BigDecimal.valueOf(p.getAmount()));
    }

    LOGGER.info("startdate:" + netPresentValueCashFlow.getStartdate() + " / rate: "
        + netPresentValueCashFlow.getEffectiveRate() + " / cashFlow:" + cashFlow
        + " / isActualDay: " + netPresentValueCashFlow.getIsActualDays());

    BigDecimal amount = FinancialCalculation.sumOfDiscountedAmounts(cashFlow,
        BigDecimal.valueOf(netPresentValueCashFlow.getEffectiveRate() / 100.0),
        netPresentValueCashFlow.getIsActualDays());

    double r = Math.round(amount.doubleValue() * 100) / 100;
    LOGGER.info("Calculated Net Present Value: " + r);
    return r;
  }

}
