SET client_encoding TO 'UTF8';

SELECT setval('dm_cdu_seq', COALESCE(MAX(cdu_id)+1, 1), FALSE) FROM xhb_disp_mgr_cdu;
SELECT setval('dm_court_site_seq', COALESCE(MAX(court_site_id)+1, 1), FALSE) FROM xhb_disp_mgr_court_site;
SELECT setval('dm_local_proxy_seq', COALESCE(MAX(local_proxy_id)+1, 1), FALSE) FROM xhb_disp_mgr_local_proxy;
SELECT setval('dm_log_seq', COALESCE(MAX(log_id)+1, 1), FALSE) FROM xhb_disp_mgr_log;
SELECT setval('dm_property_seq', COALESCE(MAX(property_id)+1, 1), FALSE) FROM xhb_disp_mgr_property;
SELECT setval('dm_schedule_seq', COALESCE(MAX(schedule_id)+1, 1), FALSE) FROM xhb_disp_mgr_schedule;
SELECT setval('dm_service_audit_seq', COALESCE(MAX(service_audit_id)+1, 1), FALSE) FROM xhb_disp_mgr_service_audit;
SELECT setval('dm_url_seq', COALESCE(MAX(url_id)+1, 1), FALSE) FROM xhb_disp_mgr_url;
SELECT setval('dm_user_details_seq', COALESCE(MAX(user_id)+1, 1), FALSE) FROM xhb_disp_mgr_user_details;
