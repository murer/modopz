#!/bin/bash -xe

mvn clean verify source:jar-no-fork \
    javadoc:jar gpg:sign \
    "-Dgpg.passphrase=$MODOPZ_SECRET" \
    -Dmaven.test.skip.exec \
    -T 10
