\copy xhb_disp_mgr_cdu (cdu_id, cdu_number, mac_address, ip_address, title, description, location, notification, refresh, weighting, offline_ind, rag_status, rag_status_date, court_site_id, last_update_date, creation_date, created_by, last_updated_by, version) FROM 'XHB_DISP_MGR_CDU_DATA_TABLE.csv' DELIMITER ',' CSV HEADER

\copy xhb_disp_mgr_court_site (court_site_id, title, page_url, schedule_id, xhibit_court_site_id, last_update_date, creation_date, created_by, last_updated_by, version, rag_status, rag_status_date, notification) FROM 'XHB_DISP_MGR_COURT_SITE_DATA_TABLE.csv' DELIMITER ',' CSV HEADER

\copy xhb_disp_mgr_local_proxy (local_proxy_id, ip_address, hostname, rag_status, rag_status_date, court_site_id, last_update_date, creation_date, created_by, last_updated_by, version) FROM 'XHB_DISP_MGR_LOCAL_PROXY_DATA_TABLE.csv' DELIMITER ',' CSV HEADER

\copy xhb_disp_mgr_mapping (url_id, cdu_id, creation_date, created_by) FROM 'XHB_DISP_MGR_MAPPING_DATA_TABLE.csv' DELIMITER ',' CSV HEADER

\copy xhb_disp_mgr_property (property_id, property_name, property_value, last_update_date, creation_date, created_by, last_updated_by, version) FROM 'XHB_DISP_MGR_PROPERTY_DATA_TABLE.csv' DELIMITER ',' CSV HEADER

\copy xhb_disp_mgr_schedule (schedule_id, schedule_type, title, detail, last_update_date, creation_date, created_by, last_updated_by, version) FROM 'XHB_DISP_MGR_SCHEDULE_DATA_TABLE.csv' DELIMITER ',' CSV HEADER

\copy xhb_disp_mgr_url (url_id, description, url, court_site_id, last_update_date, creation_date, created_by, last_updated_by, version) FROM 'XHB_DISP_MGR_URL_DATA_TABLE.csv' DELIMITER ',' CSV HEADER

\copy xhb_disp_mgr_user_details (user_id, user_name, user_role, last_update_date, creation_date, created_by, last_updated_by, version) FROM 'XHB_DISP_MGR_USER_DETAILS_DATA_TABLE.csv' DELIMITER ',' CSV HEADER

\copy xhb_court_site_welsh (court_site_welsh_id, court_site_name, court_site_id, last_update_date, creation_date, created_by, last_updated_by, version) FROM 'XHB_COURT_SITE_WELSH_DATA_TABLE.csv' DELIMITER ',' CSV HEADER


-- Commented out as has FK dependency on a table with BLOB data which is not being imported at this time
--\copy public.xhb_dm_qz_cron_triggers (sched_name, trigger_name, trigger_group, cron_expression, time_zone_id) FROM 'XHB_DM_QZ_CRON_TRIGGERS_DATA_TABLE.csv' DELIMITER ',' CSV HEADER

-- Contains BLOB column so cannot be migrated using CSV, need to generate a SQL script using ORA2PG
--\copy public.xhb_dm_qz_job_details (sched_name, job_name, job_group, description, job_class_name, is_durable, is_nonconcurrent, is_update_data, requests_recovery, job_data) FROM 'XHB_DM_QZ_JOB_DETAILS_DATA_TABLE.csv' DELIMITER ',' CSV HEADER

-- Commented out as has FK dependency on a table with BLOB data which is not being imported at this time
--\copy public.xhb_dm_qz_locks (sched_name, lock_name) FROM 'XHB_DM_QZ_LOCKS_DATA_TABLE.csv' DELIMITER ',' CSV HEADER

-- Commented out as has FK dependency on a table with BLOB data which is not being imported at this time
--\copy public.xhb_dm_qz_scheduler_state (sched_name, instance_name, last_checkin_time, checkin_interval) FROM 'XHB_DM_QZ_SCHEDULER_STATE_DATA_TABLE.csv' DELIMITER ',' CSV HEADER

-- Commented out as has FK dependency on a table with BLOB data which is not being imported at this time
--\copy public.xhb_dm_qz_simple_triggers (sched_name, trigger_name, trigger_group, repeat_count, repeat_interval, times_triggered) FROM 'XHB_DM_QZ_SIMPLE_TRIGGERS_DATA_TABLE.csv' DELIMITER ',' CSV HEADER

-- Contains BLOB column so cannot be migrated using CSV, need to generate a SQL script using ORA2PG
--\copy public.xhb_dm_qz_triggers (sched_name, trigger_name, trigger_group, job_name, job_group, description, next_fire_time, prev_fire_time, priority, trigger_state, trigger_type, start_time, end_time, calendar_name, misfire_instr, job_data) FROM 'XHB_DM_QZ_TRIGGERS_DATA_TABLE.csv' DELIMITER ',' CSV HEADER

