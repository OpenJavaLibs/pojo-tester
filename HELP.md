# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.3.4/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.3.4/maven-plugin/build-image.html)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.


# Publish an Open‑Source Java Library to Maven Central (2025 edition)

*An end‑to‑end, copy‑pasteable guide using a fictional org **OpenJavaLibs** and library **pojo-helper**. It covers the new Central Publisher Portal flow (tokens + the Central Publishing Maven Plugin), GPG signing, POM metadata, `settings.xml`, and optional CI with GitHub Actions.*

---

## 0) What you’ll need

* **Java**: JDK 17+ (`java -version`)
* **Maven**: 3.9+ (`mvn -v`)
* **GPG**: `gpg --version` (for signing)
* **Git & GitHub**
* A few minutes on **central.sonatype.com** to create a namespace and a **publishing token**

> Why this guide? As of 2024–2025, new (and migrated) projects publish via the **Central Publisher Portal** using **tokens** and the **Central Publishing Maven Plugin** — not the legacy OSSRH “staging” plugin.

---

## 1) Create your GitHub organization and repository

1. In GitHub, create an org: **OpenJavaLibs**
2. Create a public repo: **pojo-helper**
3. Initialize locally:

```bash
mkdir pojo-helper && cd pojo-helper
git init -b main
cat > README.md <<'EOF'
# pojo-helper
Small helpers for working with POJOs.
EOF
cat > .gitignore <<'EOF'
.target/
.idea/
*.class
.mvn/wrapper/
.DS_Store
EOF

mkdir -p src/main/java/io/github/openjavalibs/pojohelper
cat > src/main/java/io/github/openjavalibs/pojohelper/PojoUtils.java <<'EOF'
package io.github.openjavalibs.pojohelper;

public final class PojoUtils {
  private PojoUtils() {}
  public static boolean isBlank(String s) { return s == null || s.trim().isEmpty(); }
}
EOF

git add .
git commit -m "chore: bootstrap pojo-helper"
# Add your remote and push
# git remote add origin git@github.com:OpenJavaLibs/pojo-helper.git
# git push -u origin main
```

**Naming the coordinates:** we’ll publish under the namespace `io.github.openjavalibs`, so your Maven coordinates will be:

```
<groupId>io.github.openjavalibs</groupId>
<artifactId>pojo-helper</artifactId>
<version>1.0.0</version>
```

---

## 2) Claim your namespace on the Central Portal

1. Go to **central.sonatype.com** and **Sign in with GitHub**.
2. Create/claim a **Namespace**. Two common options:

   * **GitHub-based** (fast & free): `io.github.<your-github-org-or-user>` → e.g., `io.github.openjavalibs`.
   * **DNS-based** (if you own a domain): `com.example` style.
3. Verify ownership as guided by the Portal. After verification, you can publish any **groupId** that **starts with** that namespace.

> Tip: If you previously used OSSRH (`oss.sonatype.org`) you can migrate to the Portal. For brand‑new projects, start with the Portal.

---

## 3) Generate a **publishing token** (Central Portal → Account Settings)

1. In the Portal, open **Account Settings → Generate User Token**.
2. Copy the **token username** and **token password** once — you won’t see them again. Store securely (e.g., a password manager / CI secrets).

We’ll place these in Maven’s `settings.xml` as the credentials for server id **`central`**.

---

## 4) Create and publish your **GPG key**

Maven Central requires **PGP/GPG signatures** on every artifact.

```bash
# 4.1 Generate a new RSA 4096-bit key (use your name/email)
gpg --full-generate-key

# 4.2 List and copy the LONG key id (40 hex chars)
gpg --list-secret-keys --keyid-format LONG

# 4.3 Upload your public key so others can verify signatures
# (keys.openpgp.org is widely used)
gpg --keyserver keys.openpgp.org --send-keys <YOUR_LONG_KEY_ID>

# 4.4 Optional: export keys for CI
gpg --armor --export <YOUR_LONG_KEY_ID> > public.gpg
gpg --armor --export-secret-keys <YOUR_LONG_KEY_ID> > private.gpg
```

> You’ll use the **long key id** in Maven config. For CI, prefer importing the private key from a secret and passing the passphrase via env vars.

---

## 5) Create a Central‑ready `pom.xml`

This POM includes required metadata (**name, description, license, SCM, developers**), generates **sources** and **javadoc** JARs, signs artifacts with **GPG**, and wires the **Central Publishing Maven Plugin** (`org.sonatype.central:central-publishing-maven-plugin`).

> Replace names/URLs as appropriate. Keep `<groupId>` within your claimed namespace, e.g., `io.github.openjavalibs`.

```xml
<!-- pom.xml at the project root -->
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>io.github.openjavalibs</groupId>
  <artifactId>pojo-helper</artifactId>
  <version>1.0.0</version>
  <name>${project.groupId}:${project.artifactId}</name>
  <description>Small helpers for working with POJOs</description>
  <url>https://github.com/OpenJavaLibs/pojo-helper</url>

  <licenses>
    <license>
      <name>Apache License, Version 2.0</name>
      <url>https://www.apache.org/licenses/LICENSE-2.0.txt</url>
      <distribution>repo</distribution>
    </license>
  </licenses>

  <developers>
    <developer>
      <id>openjavalibs</id>
      <name>OpenJavaLibs Team</name>
      <organization>OpenJavaLibs</organization>
      <organizationUrl>https://github.com/OpenJavaLibs</organizationUrl>
    </developer>
  </developers>

  <scm>
    <connection>scm:git:https://github.com/OpenJavaLibs/pojo-helper.git</connection>
    <developerConnection>scm:git:ssh://git@github.com/OpenJavaLibs/pojo-helper.git</developerConnection>
    <url>https://github.com/OpenJavaLibs/pojo-helper</url>
    <tag>HEAD</tag>
  </scm>

  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven.compiler.release>17</maven.compiler.release>

    <!-- GPG -->
    <gpg.executable>gpg</gpg.executable>
    <!-- Use your long key id; or omit to use the default key -->
    <gpg.keyname><YOUR-GPG-KEY></gpg.keyname>
  </properties>

  <build>
    <plugins>
      <!-- Compile with modern JDK -->
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId>
        <version>3.13.0</version>
        <configuration>
          <release>${maven.compiler.release}</release>
        </configuration>
      </plugin>

      <!-- Attach -sources.jar -->
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-source-plugin</artifactId>
        <version>3.3.1</version>
        <executions>
          <execution>
            <id>attach-sources</id>
            <goals>
              <goal>jar</goal>
            </goals>
          </execution>
        </executions>
      </plugin>

      <!-- Attach -javadoc.jar -->
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-javadoc-plugin</artifactId>
        <version>3.7.0</version>
        <executions>
          <execution>
            <id>attach-javadocs</id>
            <goals>
              <goal>jar</goal>
            </goals>
          </execution>
        </executions>
      </plugin>

      <!-- GPG sign in the verify phase (non‑interactive CI friendly) -->
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-gpg-plugin</artifactId>
        <version>3.2.4</version>
        <executions>
          <execution>
            <id>sign-artifacts</id>
            <phase>verify</phase>
            <goals>
              <goal>sign</goal>
            </goals>
            <configuration>
              <gpgExecutable>${gpg.executable}</gpgExecutable>
              <keyname>${gpg.keyname}</keyname>
              <!-- Read passphrase from env; rely on gpg-agent if unset -->
              <passphrase>${env.GPG_PASSPHRASE}</passphrase>
              <gpgArguments>
                <arg>--pinentry-mode</arg>
                <arg>loopback</arg>
              </gpgArguments>
            </configuration>
          </execution>
        </executions>
      </plugin>

      <!-- Central Publishing Maven Plugin (Portal) -->
      <plugin>
        <groupId>org.sonatype.central</groupId>
        <artifactId>central-publishing-maven-plugin</artifactId>
        <version>0.8.0</version>
        <extensions>true</extensions>
        <configuration>
          <!-- matches server id in settings.xml -->
          <publishingServerId>central</publishingServerId>
          <!-- Optional (CI): publish automatically after validation -->
          <!-- <autoPublish>true</autoPublish> -->
          <!-- Optional: block until published; requires autoPublish -->
          <!-- <waitUntil>published</waitUntil> -->
        </configuration>
      </plugin>

    </plugins>
  </build>

  <!-- No legacy distributionManagement is required for the Portal plugin -->
</project>
```

> If you’re migrating from OSSRH (the old `nexus-staging-maven-plugin`), remove that plugin and wire the Central Publishing plugin instead.

---

## 6) Configure `~/.m2/settings.xml` (credentials + GPG)

Add the **Portal token** under server id **`central`** and (optionally) a GPG profile. Do this on any machine/CI runner that performs publishing.

```xml
<!-- ~/.m2/settings.xml -->
<settings>
  <servers>
    <server>
      <!-- MUST match <publishingServerId> in pom.xml -->
      <id>central</id>
      <username>${env.CENTRAL_TOKEN_USERNAME}</username>
      <password>${env.CENTRAL_TOKEN_PASSWORD}</password>
    </server>
  </servers>

  <profiles>
    <profile>
      <id>gpg</id>
      <properties>
        <gpg.executable>gpg</gpg.executable>
        <!-- Optional: set if you have multiple keys -->
        <gpg.keyname>80FB99EFC46EEF609CA94FC06F71D93E0823BB8B</gpg.keyname>
        <!-- Avoid plaintext: pass via env in CI. Locally you can omit to use gpg-agent -->
        <gpg.passphrase>${env.GPG_PASSPHRASE}</gpg.passphrase>
      </properties>
    </profile>
  </profiles>

  <activeProfiles>
    <activeProfile>gpg</activeProfile>
  </activeProfiles>
</settings>
```

> **Security tip:** Use Maven’s **encrypted passwords** for local dev, or provide credentials via CI secrets/env vars. The Central plugin supports Maven password encryption.

---

## 7) First local build (unsigned vs signed)

* Quick local build without signing (for development only):

```bash
mvn -q -Dgpg.skip clean verify
```

* Full release build (signs artifacts):

```bash
export GPG_PASSPHRASE='••••••••'   # if you’re not using gpg-agent
mvn -q clean deploy
```

You should see the Central Publishing plugin stage a bundle and upload it. If `autoPublish` is **not** set, you’ll be asked to publish the validated deployment manually in the Portal UI.

---

## 8) Publish in the Portal (manual or automatic)

* **Manual:** After `mvn deploy`, open the **Deployments** page in the Portal, review the validation, and **Publish**.
* **Automatic (CI‑friendly):** In the POM plugin config, set:

```xml
<autoPublish>true</autoPublish>
<waitUntil>published</waitUntil>
```

This publishes automatically after validation and blocks until published (fail‑fast if validation fails).

---

## 9) Verify the release

* Search your coordinates on **central.sonatype.com** (Search).
* Check the canonical repository URL: `https://repo1.maven.org/maven2/io/github/openjavalibs/pojo-helper/`
* Consumers can now depend on it:

```xml
<dependency>
  <groupId>io.github.openjavalibs</groupId>
  <artifactId>pojo-helper</artifactId>
  <version>1.0.0</version>
</dependency>
```

---

## 10) Optional: GitHub Actions CI for releases

Create `.github/workflows/release.yml` to publish on tag push.

```yaml
name: Release to Maven Central
on:
  push:
    tags:
      - 'v*'

jobs:
```

