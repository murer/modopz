#!/bin/bash -xe

date

mvn clean install -Ddist -Dit -B -Dmaven.test.skip.exec
