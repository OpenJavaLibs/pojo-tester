# GPG Key Creation, Publishing, and Maven Release Guide

This guide provides a complete, repeatable reference for creating GPG keys, publishing them to public keyservers, configuring Maven for seamless signing, and releasing to Maven Central.

---

## 1. Quick Verification: Is My Key Ready on Keyservers?

Before running `./build.sh` or `mvn deploy`, verify that public keyservers can resolve your key by its fingerprint:

```bash
# Check Ubuntu keyserver (Primary for Maven Central)
curl -s -o /dev/null -w "%{http_code}\n" "https://keyserver.ubuntu.com/pks/lookup?op=get&search=0x<YOUR_KEY_FINGERPRINT>"

# Check OpenPGP keyserver
curl -s -o /dev/null -w "%{http_code}\n" "https://keys.openpgp.org/vks/v1/by-fingerprint/<YOUR_KEY_FINGERPRINT>"
```

- **`200`**: The key is indexed and ready for Sonatype validation.
- **`404`**: The key is not uploaded yet or is still propagating (wait 5–10 minutes).

---

## 2. Generating a New GPG Key

If you need to generate a new key pair:

```bash
gpg --full-generate-key
```

1. **Key type:** Choose `(9) ECC (sign and encrypt)` or `(1) RSA and RSA` (default 4096 bits).
2. **Elliptic curve (if ECC):** `(1) Curve 25519` (recommended).
3. **Key expiration:** `3y` (or desired expiration, e.g., 2–3 years).
4. **User ID:**
   - **Real name:** Your full name (e.g., `Srikanth Kapila`)
   - **Email address:** Your publishing email (e.g., `srikanthkapila@gmail.com`)
   - **Comment:** (optional, press Enter to leave blank)
5. **Passphrase:** Enter a strong passphrase and save it in your password manager / macOS Keychain.

---

## 3. Finding Your Key ID and Fingerprint

List your secret keys:

```bash
gpg --list-secret-keys --keyid-format LONG
```

Example output:
```text
sec   ed25519/3DBE93736857917F 2026-11-19 [SC] [expires: 2029-11-18]
      475C87E3B50298B948C965F63DBE93736857917F
uid                 [ultimate] Srikanth Kapila <srikanthkapila@gmail.com>
```

- **Fingerprint:** `475C87E3B50298B948C965F63DBE93736857917F` (40 characters)
- **Key ID:** `3DBE93736857917F` (16 characters after the slash)

---

## 4. Publishing Your Public Key to Keyservers

Sonatype Central validates release signatures by looking up your public key on public keyservers.

### Method A: Via HTTP / HTTPS (Fastest & most reliable behind firewalls)

```bash
# Export the public key block and upload to Ubuntu keyserver
KEY=$(gpg --armor --export <YOUR_FINGERPRINT>)
curl -s -X POST --data-urlencode "keytext=$KEY" https://keyserver.ubuntu.com/pks/add

# Upload to keys.openpgp.org
curl -s -X POST \
  -H "Content-Type: application/json" \
  -d "{\"keytext\": $(python3 -c 'import sys, json; print(json.dumps(sys.stdin.read()))' <<< "$KEY")}" \
  https://keys.openpgp.org/vks/v1/upload
```

### Method B: Via GPG CLI

```bash
# Send to Ubuntu Keyserver (using HKPS / port 443)
gpg --keyserver hkps://keyserver.ubuntu.com --send-keys <YOUR_FINGERPRINT>

# Send to keys.openpgp.org
gpg --keyserver hkps://keys.openpgp.org --send-keys <YOUR_FINGERPRINT>
```

> [!NOTE]
> For **`keys.openpgp.org`**, check your email inbox for an automated confirmation email and click the verification link to make the key publicly searchable by email.

---

## 5. Automating Passphrase Entry in Maven (`settings.xml`)

To avoid typing the GPG passphrase manually or getting terminal prompt errors during `./build.sh` or `mvn deploy`, add your passphrase to `~/.m2/settings.xml`:

```xml
<settings>
  <servers>
    <server>
      <id>central</id>
      <username>YOUR_CENTRAL_TOKEN_USER</username>
      <password>YOUR_CENTRAL_TOKEN_PASSWORD</password>
    </server>
  </servers>

  <profiles>
    <profile>
      <id>gpg</id>
      <activation>
        <activeByDefault>true</activeByDefault>
      </activation>
      <properties>
        <gpg.passphrase>YOUR_GPG_PASSPHRASE</gpg.passphrase>
        <!-- Specify key ID if you have multiple keys installed -->
        <gpg.keyname>YOUR_KEY_FINGERPRINT</gpg.keyname>
      </properties>
    </profile>
  </profiles>
</settings>
```

---

## 6. Configuring macOS `gpg-agent` (Avoid TTY Errors)

Add this line to your shell profile (`~/.zshrc`):

```bash
export GPG_TTY=$(tty)
```

Optionally, configure `pinentry-mac` so macOS Keychain remembers the passphrase:
```bash
brew install pinentry-mac
echo "pinentry-program /opt/homebrew/bin/pinentry-mac" >> ~/.gnupg/gpg-agent.conf
gpgconf --kill gpg-agent
```

---

## 7. Troubleshooting Common Errors

### Error: `Inappropriate ioctl for device`
- **Cause:** GPG is trying to open an interactive prompt in a non-interactive shell or subshell.
- **Solution:** 
  1. Ensure `pom.xml` has `<arg>--pinentry-mode</arg><arg>loopback</arg>` configured in `maven-gpg-plugin`.
  2. Put `<gpg.passphrase>` in `~/.m2/settings.xml`.
  3. Ensure `export GPG_TTY=$(tty)` is run in your current shell.

### Error: `Could not find a public key by the key fingerprint`
- **Cause:** The key was not uploaded to `keyserver.ubuntu.com`, or was uploaded less than 5 minutes ago and is still replicating across nodes.
- **Solution:** 
  1. Upload the key to `https://keyserver.ubuntu.com/pks/add`.
  2. Run the `curl` check from Section 1 until it returns `200`.

### Error: `Component with package url ... already exists`
- **Cause:** Maven Central does not allow re-uploading an existing version.
- **Solution:** Bump the `<version>` in `pom.xml` (e.g., from `21.0.5` to `21.0.6`).
