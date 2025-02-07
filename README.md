# cambyze-finance-api

REST API to the Java library for finance calculation

<p>
This library takes into account <b>performance</b> aspects. For instance, by using the <a href="https://w.wiki/9N4M">Newton's method</a> and parallelism (Java class parallelStream(),...)
<p>
The <b>3.0.1 Open API configuration</b> files are generated automatically in the folder "target\generated-OpenAPI-specification" (openapi.json/openapi.yaml)
</p>.<p></p><p> . </p><p>
Example of cashflow to use for the request: <code>GET https://cambyze.com/financial-api/effectiveRateFromCashFlow</code>
</p><p>
<code>
{
    "drawdowns" : [
        {"date":"01/01/2020","amount":300000.0}
    ],
    "repayments" : [
        {"date":"01/02/2020","amount":51764.51},
        {"date":"01/03/2020","amount":51764.51},
        {"date":"01/04/2020","amount":51764.51},
        {"date":"01/05/2020","amount":51764.51},
        {"date":"01/06/2020","amount":51764.51},
        {"date":"01/07/2020","amount":51764.51}
    ],
    "isActualDays" : false    
}
</code>
</p>
