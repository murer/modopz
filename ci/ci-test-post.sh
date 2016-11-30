#!/bin/bash -xe

mkdir -p "$CIRCLE_ARTIFACTS/dist"
#cp ./RadioEu/api-web/build/libs/api-web.war "$CIRCLE_ARTIFACTS/dist/api-web.war" || true
#cp ./RadioEu/app/build/outputs/apk/app-dev-debug.apk "$CIRCLE_ARTIFACTS/dist/app-dev-debug.apk" || true
#cp ./RadioEU/app/build/outputs/apk/app-dev-release.apk "$CIRCLE_ARTIFACTS/dist/app-dev-release.apk" || true

#for k in core core-api core-api-test api-web app; do
#  mkdir -p "$CIRCLE_ARTIFACTS/build/$k"
#  cp -R "RadioEu/$k/build/reports" "$CIRCLE_ARTIFACTS/build/$k" || true
#done

du -hs $CIRCLE_ARTIFACTS/dist/*
