#!/bin/bash
java -cp $1:../jfaas-runner/target/jfaas-runner-1.jar network.golem.jfaas.runner.JfaasRunner $2
