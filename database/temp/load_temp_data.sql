\cd data
\copy temp.tmp_disp_mgr_cdu (cdu_id, cdu_number, mac_address, ip_address, title, description, location, notification, refresh, weighting, offline_ind, rag_status, rag_status_date, court_site_id, last_update_date, creation_date, created_by, last_updated_by, version) FROM 'XHB_DISP_MGR_CDU_DATA_TABLE.csv' DELIMITER ',' CSV HEADER

\copy temp.tmp_disp_mgr_court_site (court_site_id, title, page_url, schedule_id, xhibit_court_site_id, last_update_date, creation_date, created_by, last_updated_by, version, rag_status, rag_status_date, notification) FROM 'XHB_DISP_MGR_COURT_SITE_DATA_TABLE.csv' DELIMITER ',' CSV HEADER

\copy temp.tmp_disp_mgr_local_proxy (local_proxy_id, ip_address, hostname, rag_status, rag_status_date, court_site_id, last_update_date, creation_date, created_by, last_updated_by, version) FROM 'XHB_DISP_MGR_LOCAL_PROXY_DATA_TABLE.csv' DELIMITER ',' CSV HEADER

\copy temp.tmp_disp_mgr_mapping (url_id, cdu_id, creation_date, created_by) FROM 'XHB_DISP_MGR_MAPPING_DATA_TABLE.csv' DELIMITER ',' CSV HEADER

\copy temp.tmp_disp_mgr_property (property_id, property_name, property_value, last_update_date, creation_date, created_by, last_updated_by, version) FROM 'XHB_DISP_MGR_PROPERTY_DATA_TABLE.csv' DELIMITER ',' CSV HEADER

\copy temp.tmp_disp_mgr_schedule (schedule_id, schedule_type, title, detail, last_update_date, creation_date, created_by, last_updated_by, version) FROM 'XHB_DISP_MGR_SCHEDULE_DATA_TABLE.csv' DELIMITER ',' CSV HEADER

\copy temp.tmp_disp_mgr_url (url_id, description, url, court_site_id, last_update_date, creation_date, created_by, last_updated_by, version) FROM 'XHB_DISP_MGR_URL_DATA_TABLE.csv' DELIMITER ',' CSV HEADER

\copy temp.tmp_disp_mgr_user_details (user_id, user_name, user_role, last_update_date, creation_date, created_by, last_updated_by, version) FROM 'XHB_DISP_MGR_USER_DETAILS_DATA_TABLE.csv' DELIMITER ',' CSV HEADER
\cd ..
