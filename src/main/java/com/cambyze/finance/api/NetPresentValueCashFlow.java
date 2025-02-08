package com.cambyze.finance.api;

/**
 * Java bean for the request body parameter of the API /netPresentValueFromCashFlow
 * 
 * @author Thierry NESTELHUT
 * @author CAMBYZE
 * @see <a href="https://cambyze.com">Cambyze</a>
 */
public class NetPresentValueCashFlow {

  private String startdate;
  private Double effectiveRate;
  private Iterable<Payment> drawdowns;
  private Iterable<Payment> repayments;
  private Boolean isActualDays;

  public NetPresentValueCashFlow() {
    super();
    this.startdate = null;
    this.effectiveRate = null;
    this.drawdowns = null;
    this.repayments = null;
    this.isActualDays = null;
  }

  public NetPresentValueCashFlow(String startdate, Double effectiveRate,
      Iterable<Payment> drawdowns, Iterable<Payment> repayments, Boolean isActualDays) {
    super();
    this.startdate = startdate;
    this.effectiveRate = effectiveRate;
    this.drawdowns = drawdowns;
    this.repayments = repayments;
    this.isActualDays = isActualDays;
  }

  public String getStartdate() {
    return startdate;
  }

  public void setStartdate(String startdate) {
    this.startdate = startdate;
  }

  public Double getEffectiveRate() {
    return effectiveRate;
  }

  public void setEffectiveRate(Double effectiveRate) {
    this.effectiveRate = effectiveRate;
  }

  public Iterable<Payment> getDrawdowns() {
    return drawdowns;
  }

  public void setDrawdowns(Iterable<Payment> drawdowns) {
    this.drawdowns = drawdowns;
  }

  public Iterable<Payment> getRepayments() {
    return repayments;
  }

  public void setRepayments(Iterable<Payment> repayments) {
    this.repayments = repayments;
  }

  public Boolean getIsActualDays() {
    return isActualDays;
  }

  public void setIsActualDays(Boolean isActualDays) {
    this.isActualDays = isActualDays;
  }

}
