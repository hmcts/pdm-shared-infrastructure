#!/bin/zsh

if [[ -n $PDM_HOME ]]; then
	echo "Building Maven project"
	cd $PDM_HOME
	mvn clean install
else
	echo "PDM_HOME is not set, please run 'source pdm_env.sh' first"
fi
