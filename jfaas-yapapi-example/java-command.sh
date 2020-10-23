#!/bin/sh
java -cp /golem/input/$1:/golem/input/jfaas-runner-1.jar network.golem.jfaas.runner.JfaasRunner /golem/input/$2

