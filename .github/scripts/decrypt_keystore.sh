#!/bin/sh

# --batch to prevent interactive command --yes to assume "yes" for questions
gpg --quiet --batch --yes --decrypt --passphrase="$KEYSTORE_GPG_PASSPHRASE" \
--output release/loudping-upload.jks release/loudping-upload.jks.gpg