#!/bin/zsh

if [[ -n $PDM_HOME ]]; then
	echo "Generating checkstyle report"
	cd $PDM_HOME
	mvn checkstyle:checkstyle
	echo "Checkstyle report saved to $SITE_DIR/checkstyle.html"
else
	echo "PDM_HOME is not set, please run 'source pdm_env.sh' first"
fi
