# Security Policy

Thank you for helping keep **Pojo Tester** and its users safe.

Pojo Tester is a test-scope library: it is used to validate POJOs
(constructors, getters, setters, `equals`, `hashCode`, `toString`) in unit
tests and is not normally shipped in production runtime. Even so, we take
security reports seriously, including issues in the library's own code,
its build/release pipeline, and its dependencies.

## Supported Versions

Security fixes are provided for the versions below. Only the latest patch
release of each supported line receives updates.

| Version  | Supported          |
| -------- | ------------------ |
| 21.0.x   | :pojo_tester:      |

The version number follows the Java baseline the library targets (currently
Java 21), so `21.x.x` releases require Java 21 or newer. When a new line
is released (for example `25.x.x`), the previous line will be supported for a
transition period announced in the release notes.

We recommend always using the latest release available on
[Maven Central](https://central.sonatype.com/artifact/io.github.openjavalibs/pojo-tester).
Only the latest patch release of a supported line receives security fixes.

## Reporting a Vulnerability

**Please do not report security vulnerabilities through public GitHub
issues, discussions, or pull requests.**

Instead, use one of these private channels:

1. **GitHub Private Vulnerability Reporting (preferred)**
   Go to the [Security tab](https://github.com/OpenJavaLibs/pojo-tester/security)
   of this repository and click **Report a vulnerability**.

### What to include

To help us triage quickly, please include as much of the following as you can:

- A description of the issue and its potential impact
- The affected version(s) and Java version
- Steps to reproduce, ideally with a minimal proof of concept
- Any suggested fix or mitigation
- Whether you plan to disclose the issue publicly, and your preferred timeline

## What to Expect

| Stage                     | Target timeline                      |
| ------------------------- | ------------------------------------ |
| Acknowledgement of report | Within **3 business days**           |
| Initial assessment        | Within **7 days**                    |
| Status updates            | At least every **14 days**           |
| Fix or mitigation         | Typically within **90 days**, depending on severity and complexity |

This is a volunteer-maintained open-source project, so these are good-faith
targets rather than guarantees.

### If the vulnerability is accepted

- We will confirm the issue and work with you on a fix.
- A patched release will be published to Maven Central.
- A [GitHub Security Advisory](https://github.com/OpenJavaLibs/pojo-tester/security/advisories)
  will be published, with a CVE requested where appropriate.
- Unless you prefer to remain anonymous, we will credit you in the advisory
  and release notes.

### If the vulnerability is declined

We will explain our reasoning, for example when the behaviour is intended,
out of scope, or not exploitable in practice. You are welcome to reply with
additional information and we will reconsider.

## Coordinated Disclosure

We ask that you give us a reasonable chance to release a fix before
disclosing details publicly. We will coordinate the disclosure date with you
and aim to publish the advisory at the same time as the fixed release.

## Scope

**In scope**

- Vulnerabilities in the Pojo Tester source code
- Vulnerable or compromised dependencies shipped with the library
- Issues in the build, release, or CI/CD configuration of this repository

**Out of scope**

- Vulnerabilities in the user's own code under test
- Issues that require an already-compromised development environment
- Reports from automated scanners without a demonstrated impact
- Bugs that are not security-relevant (please open a regular
  [issue](https://github.com/OpenJavaLibs/pojo-tester/issues) for those)
