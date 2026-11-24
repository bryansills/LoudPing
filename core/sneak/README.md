# Sneak
A simple tool to include (lightly obfuscated) secrets into apps.
This code uses [BuildConfig](https://github.com/gmazzo/gradle-buildconfig-plugin) to generate a simple class containing obfuscated constants (URLs, API keys, etc.) needed to make network requests to various network services.
These constants are deobfuscated using a simple bitwise flip with a "secret" key.
You can generate the obfuscated constants using the code in the `:tools:generate-app-secrets` Gradle module.

Do not include unobfuscated secrets in the apps.
