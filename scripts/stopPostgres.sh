#!/bin/zsh

if [[ -n $PGDATA ]]; then
	pg_ctl -D $PGDATA stop
else
	echo "PGDATA is not set, please run 'source pdm_env.sh' first"
fi
