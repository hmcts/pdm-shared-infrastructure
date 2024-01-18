#!/bin/zsh

if [[ -n $PDM_HOME ]]; then
	clear
	echo "*** Make sure Eclipse is NOT running ***"
	cd $PDM_HOME
	echo "Sonar Logs are in $SONAR_LOGS"
	rm -rf $SONAR_LOGS/*.log
	echo "Starting Sonar..."
	sonar start
else
	echo "PDM_HOME is not set, please run 'source pdm_env.sh' first"
fi
