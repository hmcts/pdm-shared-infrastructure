#!/bin/zsh

if [[ -n $PDM_ROOT ]]; then
	cd $PDM_ROOT/database_pdm_poc
	clear
	echo \\q - quit
	echo \\! pwd - Show current directory
	echo \\cd {dir} - Change Directory
	echo \\i {filename} - Run file
	psql -d pdm -U postgres
else
	echo "PDM_ROOT is not set, please run 'source pdm_env.sh' first"
fi
