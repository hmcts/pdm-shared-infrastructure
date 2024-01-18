#!/bin/bash
#
# Bash Script to SFTP Pull log file .tar archives from DSS to presentation tier
# Script expects one argument:
# ~~ bash script.sh "/path/to/remote/directory/on/dss" "/path/to/store/logs/root"
#
# Author: Sean Bulley
# Change History (ddmmyyyy)
# 20032017 - SB - Created script
# 22/03/2017 - JC - Refactored script

## Defining  Global Variables

# Remote logs location on DSS passed in as first parameter
# We're going to assume that the entire contents of this directory are to be taken back by the script
DSS_PATH_TO_TARS=""

# LOCATION_OF_SCRIPTS_WORKINGS is base of where we have permissions to write, contains out work directory and the locatrion of the actual logs
LOCATION_OF_SCRIPTS_WORKINGS=""

# temp_logs is temporary logs directory on local box that we're using for our workground
TEMP_LOGS=""

# proxy_logs is logs directory on local box that we should be putting the extracted logs into
PROXY_LOGS=""

# Log file for THIS script
TRANSFER_LOGFILE=""

# Owner of log files once copied to local directory
#LOG_OWNER="tomcat.root"

# LOGIN DETAILS ALLOWING US TO CONNECT TO DSS
# hostname or IP of DSS
HOST='192.168.1.208'
# username for connecting to DSS
USER='xhibit'

# Var for testing arguments are valid or not
VALIDINPUT=0

# Gets current time and date for logging
function timestamp {
	date +"%Y-%m-%d %H:%M:%S"
}

function getParameters {
	# expects script remoteLocation filename
	DSS_PATH_TO_TARS="${1}"
	LOCATION_OF_SCRIPTS_WORKINGS="${2}"
	
	# check if either paramter is empty
	if [ -z ${DSS_PATH_TO_TARS} ] || [ -z ${DSS_PATH_TO_TARS} ]
	then
		# Not all values set - exit
		echo "Error: null input parameters. Exiting." >> ${TRANSFER_LOGFILE}
		exit 1
	else
    		VALIDINPUT=1
	fi
	
	# temp_logs is temporary logs directory on local box that we're using for our workground
	TEMP_LOGS=${LOCATION_OF_SCRIPTS_WORKINGS}/templogs

	# proxy_logs is logs directory on local box that we should be putting the extracted logs into
	PROXY_LOGS=${LOCATION_OF_SCRIPTS_WORKINGS}/proxylogs

	# Log file for THIS script
	TRANSFER_LOGFILE=${LOCATION_OF_SCRIPTS_WORKINGS}/transfer_log.log
}

# connect with sftp to DSS
# change directory to log directory
# change local directory to temp log directory
# get specified log file
# quit sftp
function sftpGetFromDSS {
	# script to change dir to the location of the working folder
	cd ${TEMP_LOGS}
	# SFTP to remote server and copy all the files back into the working folder
	sftp ${USER}@${HOST} << EOF
		cd $DSS_PATH_TO_TARS
		get *.tar.gz
		quit
EOF
}
TIMESTAMP=$( timestamp )
echo "${TIMESTAMP} script starting" >> ${TRANSFER_LOGFILE}

#set parameters
getParameters $1 $2

#print paths and make dir if not already exists
echo "temp log dir is ${TEMP_LOGS}" >> ${TRANSFER_LOGFILE}
mkdir -p "${TEMP_LOGS}"
echo "DSS logs is ${PROXY_LOGS}" >> ${TRANSFER_LOGFILE}
mkdir -p "${PROXY_LOGS}"

# as part of the SFTP - cd to the temp location
#ftp to server
echo "start SFTP" >> ${TRANSFER_LOGFILE}
sftpGetFromDSS >> ${TRANSFER_LOGFILE}
echo "end SFTP" >> ${TRANSFER_LOGFILE}

# for each file in tmplogs, uncompress and untar
for f in *
do
  echo "Processing $f file..." >> ${TRANSFER_LOGFILE}
  # take action on each file. $f store current file name
  # uncompress each tar.gz file, this will produce a .tar file in the working temp location
  FILENAME="${f%%.*}"
  gzip -df "$f"
  # make a directory with the filename without extension
  mkdir -p "${PROXY_LOGS}/${FILENAME}"
  chmod 764 "${PROXY_LOGS}/${FILENAME}"
  # extract the tar into that folder location
  tar -xvf "${FILENAME}.tar"
  #move the .log files to the specific logs folder location
  mv *.log “${PROXY_LOGS}/${FILENAME}” >> ${TRANSFER_LOGFILE}
  # remove the .tar file
  rm -f "${PROXY_LOGS}/${FILENAME}/*.tar"
done

#change permissions, then owner and group
cd ${PROXY_LOGS}
chmod -R 764 *
#chown -R $LOG_OWNER *
echo "Permissions changed" >> ${TRANSFER_LOGFILE}
ls -ltr >> ${TRANSFER_LOGFILE}

echo "emptied ${TEMP_LOGS}" >> ${TRANSFER_LOGFILE}
rm -rf "${TEMP_LOGS}"

TIMESTAMP=$( timestamp )
echo "${TIMESTAMP} script end" >> ${TRANSFER_LOGFILE}
