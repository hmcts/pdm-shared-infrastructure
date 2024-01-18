#!/bin/zsh

if [[ -n $PDM_HOME ]]; then
	echo "Building Maven project - Bring down all latest versions *** SKIPPING ALL TESTS ***"
	cd $PDM_HOME
	rm -rf ~/.m2
	mvn clean validate
	mvn clean install -Dmaven.test.skip=true
else
	echo "PDM_HOME is not set, please run 'source pdm_env.sh' first"
fi
