#!/bin/sh

# --batch to prevent interactive command --yes to assume "yes" for questions
gpg --quiet --batch --yes --decrypt --passphrase="$1" \
--output release/loudping-upload.jks release/loudping-upload.jks.gpg
