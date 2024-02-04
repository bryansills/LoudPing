# Making an open source app that you can easily upload to the Play Store

This document hopefully demystifies some stuff around this process.
It should be safe and easy to push out a new version of your app.
Having everything be open source complicates things, but it is still totally doable.
This document does not include the step that need to be taken in order to make a new release.
That is contained in the [`RELEASING.md`](./../RELEASING.md) file.

## Goals

- Have a documented, secure, and mostly automated release process
- Do not require developers to ever touch a keystore themselves
- Give developers the tools to debug problems if things ever go wrong
- Maybe something else???

## Terms

- keystore: a file that has fancy cryptographic stuff associated with it. For this document, this keystore file will always have a `.jks` file extension.

## Key files

- [`build-android-release.yaml`](./../.github/workflows/build-android-release.yaml): the Github Action workflow that produces an `.aab` file that will be uploaded to Google Play.
- [`loudping-googleplay.jks`](NOPE): a keystore (see: Terms) that Google Play will use to create an `.apk` file that is installed onto the user's device. DO NOT COMMIT THIS FILE TO THE REPOSITORY IN ANY FORM. DO NOT SHARE THIS FILE. This file is used when first creating your app in Google Play and basically never anywhere else. This can be used by developers in extreme circumstances to fix issues that can only be solved by installing an `.apk` onto a device that already has the app installed from Google Play. Otherwise, don't touch it. Don't use it. But also, don't ever lose it. This might be the most important file for this entire application. Practically speaking, if your office is burning to the ground and you can only grab one thing, make it this file and the passwords for it. This file an its associated passwords can be stored in a password manager (like 1Password).
- [`loudping-upload.jks`](STILL NO): another keystore, but this one will be used to create an `.aab` file that is uploaded to Google Play. This file is placed in the repository in a GPG-encrypted form ([`loudping-upload.jks.gpg`](loudping-upload.jks.gpg)). `build-android-release.yaml` will decrypt this keystore (see: [`decrypt_keystore.sh`](./../.github/scripts/decrypt_keystore.sh)) while the Github Action is being executed in order to create the `.aab` file. This file (in its unencrypted form) an its associated passwords can be stored in a password manager.

MORE TO COME...