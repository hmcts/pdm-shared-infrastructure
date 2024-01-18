#!/bin/zsh

if [[ -n $PDM_HOME ]]; then
	cd $PDM_HOME
	clear
	Echo Starting Sonar Analysis...
	mvn clean verify sonar:sonar \
	  -Dsonar.projectKey=pdm \
	  -Dsonar.host.url=http://localhost:9000 \
	  -Dsonar.login=sqp_db78686ddc0ca3079070836729f93c904124cb7b
else
	echo "PDM_HOME is not set, please run 'source pdm_env.sh' first"
fi
