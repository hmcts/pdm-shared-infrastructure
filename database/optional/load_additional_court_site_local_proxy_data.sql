\copy xhb_disp_mgr_court_site (court_site_id, title, page_url, schedule_id, xhibit_court_site_id, last_update_date, creation_date, created_by, last_updated_by, version, rag_status, rag_status_date, notification) FROM 'ADDITIONAL_XHB_DISP_MGR_COURT_SITE_DATA_TABLE.csv' DELIMITER ',' CSV HEADER

\copy xhb_disp_mgr_local_proxy (local_proxy_id, ip_address, hostname, rag_status, rag_status_date, court_site_id, last_update_date, creation_date, created_by, last_updated_by, version) FROM 'ADDITIONAL_XHB_DISP_MGR_LOCAL_PROXY_DATA_TABLE.csv' DELIMITER ',' CSV HEADER
