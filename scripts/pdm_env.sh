#!/bin/zsh
#XHIBIT PDM vars

export JAVA_VERSION="17"
export PG_VERSION="14"
export SONAR_VERSION="10.0.0.68432"

export JAVA_HOME="/Library/Java/JavaVirtualMachines/jdk-$JAVA_VERSION.jdk/Contents/Home"
export PDM_ROOT="/Users/$(whoami)/Projects/PDDA/pdm"
export PDM_HOME="$PDM_ROOT/pdmanager"
export PGDATA="/opt/homebrew/var/postgresql@$PG_VERSION"
export SONAR_HOME="/opt/homebrew/Cellar/sonarqube/$SONAR_VERSION"

export SONAR_DATA="/opt/homebrew/var/sonarqube/data"
export SONAR_LOGS="/opt/homebrew/var/sonarqube/logs"
export SONAR_TEMP="/opt/homebrew/var/sonarqube/temp"

#Checkstyle/PMD reports dir
export SITE_DIR="$PDM_HOME/target/site"
