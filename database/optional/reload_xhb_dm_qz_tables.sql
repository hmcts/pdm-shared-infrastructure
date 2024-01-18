-- Clears out and recreates the XHB_DM_QZ_* tables for Quartz Scheduler
SET client_encoding TO 'UTF8';

\c pdda;

DROP TABLE IF EXISTS xhb_dm_qz_fired_triggers;
DROP TABLE IF EXISTS xhb_dm_qz_paused_trigger_grps;
DROP TABLE IF EXISTS xhb_dm_qz_scheduler_state;
DROP TABLE IF EXISTS xhb_dm_qz_locks;
DROP TABLE IF EXISTS xhb_dm_qz_simple_triggers;
DROP TABLE IF EXISTS xhb_dm_qz_cron_triggers;
DROP TABLE IF EXISTS xhb_dm_qz_simprop_triggers;
DROP TABLE IF EXISTS xhb_dm_qz_blob_triggers;
DROP TABLE IF EXISTS xhb_dm_qz_triggers;
DROP TABLE IF EXISTS xhb_dm_qz_job_details;
DROP TABLE IF EXISTS xhb_dm_qz_calendars;


CREATE TABLE xhb_dm_qz_simple_triggers (
	sched_name varchar(120) NOT NULL,
	trigger_name varchar(200) NOT NULL,
	trigger_group varchar(200) NOT NULL,
	repeat_count bigint NOT NULL,
	repeat_interval bigint NOT NULL,
	times_triggered bigint NOT NULL
) ;
ALTER TABLE xhb_dm_qz_simple_triggers ADD CONSTRAINT xhb_dm_qz_simple_triggers_pk PRIMARY KEY 
(sched_name,trigger_name,trigger_group);


CREATE TABLE xhb_dm_qz_paused_trigger_grps (
	sched_name varchar(120) NOT NULL,
	trigger_group varchar(200) NOT NULL
) ;
ALTER TABLE xhb_dm_qz_paused_trigger_grps ADD CONSTRAINT xhb_dm_qz_paused_trigger_grps_pk PRIMARY KEY 
(sched_name,trigger_group);


CREATE TABLE xhb_dm_qz_calendars (
	sched_name varchar(120) NOT NULL,
	calendar_name varchar(200) NOT NULL,
	calendar bytea NOT NULL
) ;
ALTER TABLE xhb_dm_qz_calendars ADD CONSTRAINT xhb_dm_qz_calendars_pk PRIMARY KEY (sched_name,calendar_name);


CREATE TABLE xhb_dm_qz_cron_triggers (
	sched_name varchar(120) NOT NULL,
	trigger_name varchar(200) NOT NULL,
	trigger_group varchar(200) NOT NULL,
	cron_expression varchar(120) NOT NULL,
	time_zone_id varchar(80)
) ;
ALTER TABLE xhb_dm_qz_cron_triggers ADD CONSTRAINT xhb_dm_qz_cron_triggers_pk PRIMARY KEY 
(sched_name,trigger_name,trigger_group);


CREATE TABLE xhb_dm_qz_fired_triggers (
	sched_name varchar(120) NOT NULL,
	entry_id varchar(95) NOT NULL,
	trigger_name varchar(200) NOT NULL,
	trigger_group varchar(200) NOT NULL,
	instance_name varchar(200) NOT NULL,
	fired_time bigint NOT NULL,
	sched_time bigint NOT NULL,
	priority integer NOT NULL,
	state varchar(16) NOT NULL,
	job_name varchar(200),
	job_group varchar(200),
	is_nonconcurrent bool,
	requests_recovery bool
) ;
ALTER TABLE xhb_dm_qz_fired_triggers ADD CONSTRAINT xhb_dm_qz_fired_triggers_pk PRIMARY KEY 
(sched_name,entry_id);


CREATE TABLE xhb_dm_qz_blob_triggers (
	sched_name varchar(120) NOT NULL,
	trigger_name varchar(200) NOT NULL,
	trigger_group varchar(200) NOT NULL,
	blob_data bytea
) ;
ALTER TABLE xhb_dm_qz_blob_triggers ADD CONSTRAINT xhb_dm_qz_blob_triggers_pk PRIMARY KEY 
(sched_name,trigger_name,trigger_group);


CREATE TABLE xhb_dm_qz_job_details (
	sched_name varchar(120) NOT NULL,
	job_name varchar(200) NOT NULL,
	job_group varchar(200) NOT NULL,
	description varchar(250),
	job_class_name varchar(250) NOT NULL,
	is_durable bool NOT NULL,
	is_nonconcurrent bool NOT NULL,
	is_update_data bool NOT NULL,
	requests_recovery bool NOT NULL,
	job_data bytea
) ;
ALTER TABLE xhb_dm_qz_job_details ADD CONSTRAINT xhb_dm_qz_job_details_pk PRIMARY KEY 
(sched_name,job_name,job_group);


CREATE TABLE xhb_dm_qz_simprop_triggers (
	sched_name varchar(120) NOT NULL,
	trigger_name varchar(200) NOT NULL,
	trigger_group varchar(200) NOT NULL,
	str_prop_1 varchar(512),
	str_prop_2 varchar(512),
	str_prop_3 varchar(512),
	int_prop_1 int,
	int_prop_2 int,
	long_prop_1 bigint,
	long_prop_2 bigint,
	dec_prop_1 numeric(13, 4),
	dec_prop_2 numeric(13, 4),
	bool_prop_1 bool,
	bool_prop_2 bool
) ;
ALTER TABLE xhb_dm_qz_simprop_triggers ADD CONSTRAINT xhb_dm_qz_simprop_triggers_pk PRIMARY KEY 
(sched_name,trigger_name,trigger_group);


CREATE TABLE xhb_dm_qz_scheduler_state (
	sched_name varchar(120) NOT NULL,
	instance_name varchar(200) NOT NULL,
	last_checkin_time bigint NOT NULL,
	checkin_interval bigint NOT NULL
) ;
ALTER TABLE xhb_dm_qz_scheduler_state ADD CONSTRAINT xhb_dm_qz_scheduler_state_pk PRIMARY KEY 
(sched_name,instance_name);


CREATE TABLE xhb_dm_qz_locks (
	sched_name varchar(120) NOT NULL,
	lock_name varchar(40) NOT NULL
) ;
ALTER TABLE xhb_dm_qz_locks ADD CONSTRAINT xhb_dm_qz_locks_pk PRIMARY KEY (sched_name,lock_name);


CREATE TABLE xhb_dm_qz_triggers (
	sched_name varchar(120) NOT NULL,
	trigger_name varchar(200) NOT NULL,
	trigger_group varchar(200) NOT NULL,
	job_name varchar(200) NOT NULL,
	job_group varchar(200) NOT NULL,
	description varchar(250),
	next_fire_time bigint,
	prev_fire_time bigint,
	priority integer,
	trigger_state varchar(16) NOT NULL,
	trigger_type varchar(8) NOT NULL,
	start_time bigint NOT NULL,
	end_time bigint,
	calendar_name varchar(200),
	misfire_instr smallint,
	job_data bytea
) ;
ALTER TABLE xhb_dm_qz_triggers ADD CONSTRAINT xhb_dm_qz_triggers_pk PRIMARY KEY 
(sched_name,trigger_name,trigger_group);
