# Load Tests

## Prerequisites

- **Java 8+** must be installed and available on `PATH`
- **Apache JMeter 5.6.3** is required to run the test plans

### JMeter Setup

1. Download Apache JMeter from [https://jmeter.apache.org/download_jmeter.cgi](https://jmeter.apache.org/download_jmeter.cgi)
2. Extract the archive into `load-tests/jmeter-tools/` so that the folder structure looks like:

```
load-tests/
├── jmeter-tools/
│   ├── bin/
│   │   ├── jmeter.bat
│   │   └── ...
│   ├── lib/
│   └── ...
├── jmeter/
│   ├── test-plans/
│   ├── scripts/
│   ├── data/
│   └── evidence/
└── scenarios/
```

Alternatively, set the `JMETER_HOME` environment variable to point to your JMeter installation. The run scripts will use `JMETER_HOME` if defined; otherwise they default to the relative `jmeter-tools/` path.

## Running Tests

Open a terminal in the project root and execute the `.bat` scripts:

```bash
# Smoke test
load-tests\jmeter\scripts\run-search-smoke.bat

# Data-driven test
load-tests\jmeter\scripts\run-search-data-driven.bat
```

Results and reports are generated under `load-tests/jmeter/results/`.

## Project Structure

| Path | Description |
|---|---|
| `jmeter/test-plans/` | JMeter `.jmx` test plan files |
| `jmeter/scripts/` | CLI execution scripts (`.bat`) |
| `jmeter/data/` | Test data files (`search_terms.csv`) |
| `jmeter/evidence/` | Captured evidence (browser responses, Cloudflare blocks) |
| `scenarios/` | Test scenario and strategy documentation |

## Notes

- JMeter binary (`jmeter-tools/`) is excluded from version control due to its size. Each user must download it locally.
- n11.com is protected by Cloudflare, which blocks direct JMeter HTTP requests with HTTP 403. The test plans are technically correct; the limitation is environmental. See `scenarios/search-test-strategy.md` for details.
