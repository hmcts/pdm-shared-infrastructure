#!/bin/bash

# script name without extension
scriptname=`basename "$0" .sh`

# setup environment variables
. $HOME/.bash_profile
. ${COMMON_VAR}/.common-variables

# delete log files older than 30 days
find ${LOG_DIR} -name ${scriptname}.*.txt -mtime +30 -type f | xargs rm -f

# execute stored procedure which performs the housekeeping
${SQLPLUS} -s ${ORA} >> ${LOG_DIR}/${scriptname}.${LOGFILE}<<endsql
set serveroutput on
WHENEVER SQLERROR EXIT SQL.SQLCODE
exec XHIBIT.XHB_DISP_MGR_HOUSEKEEPING_PKG.initiate_run;
endsql