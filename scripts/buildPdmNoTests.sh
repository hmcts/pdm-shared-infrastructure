#!/bin/zsh

if [[ -n $PDM_HOME ]]; then
	echo "Building Maven project *** SKIPPING ALL TESTS ***"
	cd $PDM_HOME
	mvn clean install -Dmaven.test.skip=true
else
	echo "PDM_HOME is not set, please run 'source pdm_env.sh' first"
fi
