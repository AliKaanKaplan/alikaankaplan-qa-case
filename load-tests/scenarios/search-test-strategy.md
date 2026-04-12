# Search Test Strategy

## Objective

Investigate and validate the behavior of the search functionality on n11.com.

---

## Scope

- Header search behavior
- Search result page response
- JMeter-based execution (smoke & data-driven)
- Response validation

---

## Tools Used

- Browser DevTools
- Apache JMeter
- CSV Data Set Config

---

## Technical Approach

1. Search request identified via browser DevTools: /arama?q=<keyword>
2. Successful browser response captured and stored as evidence
3. Equivalent HTTP request modeled in JMeter
4. Two test plans created:
- Smoke test
- Data-driven test

5. CLI execution enabled via `.bat` scripts

6. Outputs structured into:
- results/
- logs/
- evidence/

---

## Key Finding

JMeter requests are blocked by Cloudflare and return: HTTP 403 Forbidden

---

## Interpretation

The system is protected by an anti-bot mechanism (Cloudflare), which:
- Allows browser traffic
- Blocks protocol-level tools like JMeter

---

## Conclusion

- Search flow successfully analyzed and modeled
- JMeter implementation is technically correct
- Execution is limited by environment restrictions

This reflects a realistic production scenario where load tools may be restricted by security layers.