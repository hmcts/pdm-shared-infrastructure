SET client_encoding TO 'UTF8';


CREATE TABLE xhb_dm_qz_simple_triggers (
	sched_name varchar(120) NOT NULL,
	trigger_name varchar(200) NOT NULL,
	trigger_group varchar(200) NOT NULL,
	repeat_count bigint NOT NULL,
	repeat_interval bigint NOT NULL,
	times_triggered bigint NOT NULL
) ;
ALTER TABLE xhb_dm_qz_simple_triggers ADD CONSTRAINT xhb_dm_qz_simple_triggers_pk PRIMARY KEY (sched_name,trigger_name,trigger_group);


CREATE TABLE xhb_dm_qz_paused_trigger_grps (
	sched_name varchar(120) NOT NULL,
	trigger_group varchar(200) NOT NULL
) ;
ALTER TABLE xhb_dm_qz_paused_trigger_grps ADD CONSTRAINT xhb_dm_qz_paused_trigger_grps_pk PRIMARY KEY (sched_name,trigger_group);


CREATE TABLE xhb_dm_qz_calendars (
	sched_name varchar(120) NOT NULL,
	calendar_name varchar(200) NOT NULL,
	calendar bytea NOT NULL
) ;
ALTER TABLE xhb_dm_qz_calendars ADD CONSTRAINT xhb_dm_qz_calendars_pk PRIMARY KEY (sched_name,calendar_name);


CREATE TABLE xhb_disp_mgr_local_proxy (
	local_proxy_id integer NOT NULL,
	ip_address varchar(100) NOT NULL,
	hostname varchar(100) NOT NULL,
	rag_status varchar(1),
	rag_status_date timestamp,
	court_site_id integer NOT NULL,
	last_update_date timestamp NOT NULL,
	creation_date timestamp NOT NULL,
	created_by varchar(30) NOT NULL,
	last_updated_by varchar(30) NOT NULL,
	version integer NOT NULL
) ;
ALTER TABLE xhb_disp_mgr_local_proxy ADD UNIQUE (hostname);
ALTER TABLE xhb_disp_mgr_local_proxy ADD CONSTRAINT xhb_disp_mgr_local_proxy_pk PRIMARY KEY (local_proxy_id);
ALTER TABLE xhb_disp_mgr_local_proxy ADD UNIQUE (ip_address);
ALTER TABLE xhb_disp_mgr_local_proxy ADD CONSTRAINT dm_local_proxy_rag_status_chk CHECK (rag_status IN ('R','A','G') OR rag_status IS NULL);


CREATE TABLE xhb_disp_mgr_schedule (
	schedule_id integer NOT NULL,
	schedule_type varchar(30) NOT NULL,
	title varchar(30) NOT NULL,
	detail varchar(2000) NOT NULL,
	last_update_date timestamp NOT NULL,
	creation_date timestamp NOT NULL,
	created_by varchar(30) NOT NULL,
	last_updated_by varchar(30) NOT NULL,
	version integer NOT NULL
) ;
ALTER TABLE xhb_disp_mgr_schedule ADD CONSTRAINT xhb_disp_mgr_schedule_pk PRIMARY KEY (schedule_id);


CREATE TABLE xhb_disp_mgr_log (
	log_id integer NOT NULL,
	court_site_id integer NOT NULL,
	cdu_id integer,
	last_update_date timestamp NOT NULL,
	creation_date timestamp NOT NULL,
	created_by varchar(30) NOT NULL,
	last_updated_by varchar(30) NOT NULL,
	version integer NOT NULL
) ;
ALTER TABLE xhb_disp_mgr_log ADD CONSTRAINT xhb_disp_mgr_log_pk PRIMARY KEY (log_id);


CREATE TABLE xhb_dm_qz_cron_triggers (
	sched_name varchar(120) NOT NULL,
	trigger_name varchar(200) NOT NULL,
	trigger_group varchar(200) NOT NULL,
	cron_expression varchar(120) NOT NULL,
	time_zone_id varchar(80)
) ;
ALTER TABLE xhb_dm_qz_cron_triggers ADD CONSTRAINT xhb_dm_qz_cron_triggers_pk PRIMARY KEY (sched_name,trigger_name,trigger_group);


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
ALTER TABLE xhb_dm_qz_fired_triggers ADD CONSTRAINT xhb_dm_qz_fired_triggers_pk PRIMARY KEY (sched_name,entry_id);


CREATE TABLE xhb_disp_mgr_court_site (
	court_site_id integer NOT NULL,
	title varchar(255) NOT NULL,
	page_url varchar(500) NOT NULL,
	schedule_id integer NOT NULL,
	xhibit_court_site_id integer,
	last_update_date timestamp NOT NULL,
	creation_date timestamp NOT NULL,
	created_by varchar(30) NOT NULL,
	last_updated_by varchar(30) NOT NULL,
	version integer NOT NULL,
	rag_status varchar(1),
	rag_status_date timestamp,
	notification varchar(500)
) ;
ALTER TABLE xhb_disp_mgr_court_site ADD CONSTRAINT xhb_disp_mgr_court_site_pk PRIMARY KEY (court_site_id);
ALTER TABLE xhb_disp_mgr_court_site ADD CONSTRAINT dm_court_site_rag_status_chk CHECK (rag_status IN ('R','A','G') OR rag_status IS NULL);


CREATE TABLE xhb_disp_mgr_cdu (
	cdu_id integer NOT NULL,
	cdu_number varchar(50) NOT NULL,
	mac_address varchar(50) NOT NULL,
	ip_address varchar(100) NOT NULL,
	title varchar(30),
	description varchar(500),
	location varchar(50) NOT NULL,
	notification varchar(500),
	refresh bigint NOT NULL,
	weighting smallint NOT NULL,
	offline_ind varchar(1) NOT NULL,
	rag_status varchar(1),
	rag_status_date timestamp,
	court_site_id integer NOT NULL,
	last_update_date timestamp NOT NULL,
	creation_date timestamp NOT NULL,
	created_by varchar(30) NOT NULL,
	last_updated_by varchar(30) NOT NULL,
	version integer NOT NULL
) ;
ALTER TABLE xhb_disp_mgr_cdu ADD UNIQUE (court_site_id,ip_address);
ALTER TABLE xhb_disp_mgr_cdu ADD UNIQUE (cdu_number);
ALTER TABLE xhb_disp_mgr_cdu ADD CONSTRAINT xhb_disp_mgr_cdu_pk PRIMARY KEY (cdu_id);
ALTER TABLE xhb_disp_mgr_cdu ADD UNIQUE (mac_address);
ALTER TABLE xhb_disp_mgr_cdu ADD CONSTRAINT dm_cdu_offline_ind_chk CHECK (offline_ind IN ('Y','N') AND offline_ind IS NOT NULL);
ALTER TABLE xhb_disp_mgr_cdu ADD CONSTRAINT dm_cdu_rag_status_chk CHECK (rag_status IN ('R','A','G') OR rag_status IS NULL);
ALTER TABLE xhb_disp_mgr_cdu ADD CONSTRAINT dm_cdu_weighting_chk CHECK (weighting IN (1, 2) AND weighting IS NOT NULL);


CREATE TABLE xhb_disp_mgr_mapping (
	url_id integer NOT NULL,
	cdu_id integer NOT NULL,
	creation_date timestamp NOT NULL,
	created_by varchar(30) NOT NULL
) ;
ALTER TABLE xhb_disp_mgr_mapping ADD CONSTRAINT xhb_disp_mgr_mapping_pk PRIMARY KEY (url_id,cdu_id);


CREATE TABLE xhb_dm_qz_blob_triggers (
	sched_name varchar(120) NOT NULL,
	trigger_name varchar(200) NOT NULL,
	trigger_group varchar(200) NOT NULL,
	blob_data bytea
) ;
ALTER TABLE xhb_dm_qz_blob_triggers ADD CONSTRAINT xhb_dm_qz_blob_triggers_pk PRIMARY KEY (sched_name,trigger_name,trigger_group);


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
ALTER TABLE xhb_dm_qz_job_details ADD CONSTRAINT xhb_dm_qz_job_details_pk PRIMARY KEY (sched_name,job_name,job_group);


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
ALTER TABLE xhb_dm_qz_simprop_triggers ADD CONSTRAINT xhb_dm_qz_simprop_triggers_pk PRIMARY KEY (sched_name,trigger_name,trigger_group);


CREATE TABLE xhb_disp_mgr_property (
	property_id integer NOT NULL,
	property_name varchar(200) NOT NULL,
	property_value varchar(200) NOT NULL,
	last_update_date timestamp NOT NULL,
	creation_date timestamp NOT NULL,
	created_by varchar(30) NOT NULL,
	last_updated_by varchar(30) NOT NULL,
	version integer NOT NULL
) ;
ALTER TABLE xhb_disp_mgr_property ADD UNIQUE (property_name);
ALTER TABLE xhb_disp_mgr_property ADD CONSTRAINT xhb_disp_mgr_property_pk PRIMARY KEY (property_id);


CREATE TABLE xhb_dm_qz_scheduler_state (
	sched_name varchar(120) NOT NULL,
	instance_name varchar(200) NOT NULL,
	last_checkin_time bigint NOT NULL,
	checkin_interval bigint NOT NULL
) ;
ALTER TABLE xhb_dm_qz_scheduler_state ADD CONSTRAINT xhb_dm_qz_scheduler_state_pk PRIMARY KEY (sched_name,instance_name);


CREATE TABLE xhb_disp_mgr_service_audit (
	service_audit_id integer NOT NULL,
	from_endpoint varchar(30),
	to_endpoint varchar(30),
	service varchar(30),
	url varchar(255),
	message_id varchar(100),
	message_status varchar(30),
	message_request text,
	message_response text,
	last_update_date timestamp NOT NULL,
	creation_date timestamp NOT NULL,
	created_by varchar(30) NOT NULL,
	last_updated_by varchar(30) NOT NULL,
	version integer NOT NULL
) ;
ALTER TABLE xhb_disp_mgr_service_audit ADD CONSTRAINT xhb_disp_mgr_service_audit_pk PRIMARY KEY (service_audit_id);


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
ALTER TABLE xhb_dm_qz_triggers ADD CONSTRAINT xhb_dm_qz_triggers_pk PRIMARY KEY (sched_name,trigger_name,trigger_group);


CREATE TABLE xhb_disp_mgr_user_details (
	user_id integer NOT NULL,
	user_name varchar(30) NOT NULL,
	user_role varchar(30) NOT NULL,
	last_update_date timestamp NOT NULL,
	creation_date timestamp NOT NULL,
	created_by varchar(30) NOT NULL,
	last_updated_by varchar(30) NOT NULL,
	version integer NOT NULL
) ;
ALTER TABLE xhb_disp_mgr_user_details ADD CONSTRAINT xhb_disp_mgr_user_details_pk PRIMARY KEY (user_id);
ALTER TABLE xhb_disp_mgr_user_details ADD UNIQUE (user_name);


CREATE TABLE xhb_disp_mgr_url (
	url_id integer NOT NULL,
	description varchar(500) NOT NULL,
	url varchar(255) NOT NULL,
	court_site_id integer,
	last_update_date timestamp NOT NULL,
	creation_date timestamp NOT NULL,
	created_by varchar(30) NOT NULL,
	last_updated_by varchar(30) NOT NULL,
	version integer NOT NULL
) ;
ALTER TABLE xhb_disp_mgr_url ADD CONSTRAINT xhb_disp_mgr_url_pk PRIMARY KEY (url_id);
