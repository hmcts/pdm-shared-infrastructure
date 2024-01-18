#!/bin/zsh

if [[ -n $PDM_HOME ]]; then
	echo "Generating PMD report"
	cd $PDM_HOME
	mvn site
	echo "PMD report saved to $SITE_DIR/pmd.html"
else
	echo "PDM_HOME is not set, please run 'source pdm_env.sh' first"
fi
