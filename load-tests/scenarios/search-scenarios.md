# Search Functionality - Test Scenarios

## Overview
This document defines test scenarios for validating the search functionality on n11.com.

---

## Scenario 1 — Search with a valid keyword

**Objective:**  
Verify that a user can search for a valid keyword and reach the search results page.

**Input:**  
`Telefon`

**Expected Behavior:**
- Search request is sent to `/arama?q=<keyword>`
- Search results page is returned
- Response contains the searched keyword
- Response represents a valid listing page

**Note:**  
Browser execution succeeds. JMeter requests are blocked by Cloudflare (HTTP 403).

---

## Scenario 2 — Search with another valid keyword

**Objective:**  
Verify that search works consistently with different valid inputs.

**Input:**  
`Laptop`

**Expected Behavior:**
- Request structure remains correct
- Search results page is returned
- Keyword appears in response

**Note:**  
May be blocked by Cloudflare in JMeter execution.

---

## Scenario 3 — Search with a non-existing keyword

**Objective:**  
Validate system behavior for meaningless or non-existing inputs.

**Input:**  
`asdasdasdzzx`

**Expected Behavior:**
- Request is accepted
- Response is returned (even if no results)
- System does not crash

---

## Scenario 4 — Search with special characters

**Objective:**  
Verify system stability with unexpected input formats.

**Input:**  
`@@@`

**Expected Behavior:**
- Input is safely handled or sanitized
- No server-side crash occurs
- System returns controlled response

---

## Scenario 5 — Data-driven search execution

**Objective:**  
Verify repeated execution using external data source.

**Input Source:**  
`search_terms.csv`

**Expected Behavior:**
- Each keyword is read from CSV
- Query parameter updates dynamically (`q=${searchTerm}`)
- Same request flow executes per iteration