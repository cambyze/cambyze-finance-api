package com.cambyze.finance_api;

/**
 * Java bean for the request body parameter of the API /effectiveRateFromCashFlow
 * 
 * @author Thierry NESTELHUT
 * @author CAMBYZE
 * @see <a href="https://cambyze.com">Cambyze</a>
 */
public class EffectiveRateFromCashFlow {

  private Iterable<Payment> drawdowns;
  private Iterable<Payment> repayments;
  private Boolean isActualDays;

  public EffectiveRateFromCashFlow(Iterable<Payment> drawdowns, Iterable<Payment> repayments,
      Boolean isActualDays) {
    super();
    this.drawdowns = drawdowns;
    this.repayments = repayments;
    this.isActualDays = isActualDays;
  }

  public EffectiveRateFromCashFlow() {
    super();
    this.drawdowns = null;
    this.repayments = null;
    this.isActualDays = null;
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
