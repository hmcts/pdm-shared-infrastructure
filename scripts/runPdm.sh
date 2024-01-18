#!/bin/zsh

if [[ -n $PDM_HOME ]]; then
	echo "Running PDM"
	cd $PDM_HOME
	mvn spring-boot:run
else
	echo "PDM_HOME is not set, please run 'source pdm_env.sh' first"
fi
