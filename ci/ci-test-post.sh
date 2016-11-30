#!/bin/bash -xe

mkdir -p "$CIRCLE_ARTIFACTS/dist"
cp modopz-server/target/modopz-server-dist-single.jar $CIRCLE_ARTIFACTS/dist || true
du -hs $CIRCLE_ARTIFACTS/dist/*
