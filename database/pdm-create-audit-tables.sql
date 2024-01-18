SET client_encoding TO 'UTF8';

CREATE TABLE aud_disp_mgr_url (
	url_id integer NOT NULL,
	description varchar(500) NOT NULL,
	url varchar(255) NOT NULL,
	court_site_id integer,
	last_update_date timestamp NOT NULL,
	creation_date timestamp NOT NULL,
	created_by varchar(30) NOT NULL,
	last_updated_by varchar(30) NOT NULL,
	version integer NOT NULL,
	insert_event varchar(1) NOT NULL
) ;
ALTER TABLE aud_disp_mgr_url ADD CONSTRAINT aud_disp_mgr_url_pk PRIMARY KEY (url_id, last_update_date);


CREATE TABLE aud_disp_mgr_property (
	property_id integer NOT NULL,
	property_name varchar(200) NOT NULL,
	property_value varchar(200) NOT NULL,
	last_update_date timestamp NOT NULL,
	creation_date timestamp NOT NULL,
	created_by varchar(30) NOT NULL,
	last_updated_by varchar(30) NOT NULL,
	version integer NOT NULL,
	insert_event varchar(1) NOT NULL
) ;
ALTER TABLE aud_disp_mgr_property ADD CONSTRAINT aud_disp_mgr_property_pk PRIMARY KEY (property_id, last_update_date);



CREATE TABLE aud_disp_mgr_court_site (
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
	insert_event varchar(1) NOT NULL,
	rag_status varchar(1),
	rag_status_date timestamp,
	notification varchar(500)
) ;
ALTER TABLE aud_disp_mgr_court_site ADD CONSTRAINT aud_disp_mgr_court_site_pk PRIMARY KEY (court_site_id, last_update_date);


CREATE TABLE aud_disp_mgr_user_details (
	user_id integer NOT NULL,
	user_name varchar(30) NOT NULL,
	user_role varchar(30) NOT NULL,
	last_update_date timestamp NOT NULL,
	creation_date timestamp NOT NULL,
	created_by varchar(30) NOT NULL,
	last_updated_by varchar(30) NOT NULL,
	version integer NOT NULL,
	insert_event varchar(1) NOT NULL
) ;
ALTER TABLE aud_disp_mgr_user_details ADD CONSTRAINT aud_disp_mgr_user_details_pk PRIMARY KEY (user_id, last_update_date);


CREATE TABLE aud_disp_mgr_log (
	log_id integer NOT NULL,
	court_site_id integer NOT NULL,
	cdu_id integer,
	last_update_date timestamp NOT NULL,
	creation_date timestamp NOT NULL,
	created_by varchar(30) NOT NULL,
	last_updated_by varchar(30) NOT NULL,
	version integer NOT NULL,
	insert_event varchar(1) NOT NULL
) ;
ALTER TABLE aud_disp_mgr_log ADD CONSTRAINT aud_disp_mgr_log_pk PRIMARY KEY (log_id, last_update_date);


CREATE TABLE aud_disp_mgr_mapping (
	url_id integer NOT NULL,
	cdu_id integer NOT NULL,
	creation_date timestamp NOT NULL,
	created_by varchar(30) NOT NULL,
	insert_event varchar(1) NOT NULL
) ;
ALTER TABLE aud_disp_mgr_mapping ADD CONSTRAINT aud_disp_mgr_mapping_pk PRIMARY KEY (url_id, cdu_id);



CREATE TABLE aud_disp_mgr_cdu (
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
	version integer NOT NULL,
	insert_event varchar(1) NOT NULL
) ;
ALTER TABLE aud_disp_mgr_cdu ADD CONSTRAINT aud_disp_mgr_cdu_pk PRIMARY KEY (cdu_id, last_update_date);


CREATE TABLE aud_disp_mgr_schedule (
	schedule_id integer NOT NULL,
	schedule_type varchar(30) NOT NULL,
	title varchar(30) NOT NULL,
	detail varchar(2000) NOT NULL,
	last_update_date timestamp NOT NULL,
	creation_date timestamp NOT NULL,
	created_by varchar(30) NOT NULL,
	last_updated_by varchar(30) NOT NULL,
	version integer NOT NULL,
	insert_event varchar(1) NOT NULL
) ;
ALTER TABLE aud_disp_mgr_schedule ADD CONSTRAINT aud_disp_mgr_schedule_pk PRIMARY KEY (schedule_id, last_update_date);



CREATE TABLE aud_disp_mgr_local_proxy (
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
	version integer NOT NULL,
	insert_event varchar(1) NOT NULL
) ;
ALTER TABLE aud_disp_mgr_local_proxy ADD CONSTRAINT aud_disp_mgr_local_proxy_pk PRIMARY KEY (local_proxy_id, last_update_date);
