#!/bin/zsh

if [[ -n $PDM_HOME ]]; then
	echo "Stopping Sonar..."
	sonar stop
else
	echo "PDM_HOME is not set, please run 'source pdm_env.sh' first"
fi
