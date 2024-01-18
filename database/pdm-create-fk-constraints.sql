SET client_encoding TO 'UTF8';

ALTER TABLE xhb_disp_mgr_cdu ADD CONSTRAINT dm_cdu_court_site_id_fk FOREIGN KEY (court_site_id) REFERENCES xhb_disp_mgr_court_site(court_site_id) ON DELETE NO ACTION NOT DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE xhb_disp_mgr_court_site ADD CONSTRAINT dm_court_site_schedule_id_fk FOREIGN KEY (schedule_id) REFERENCES xhb_disp_mgr_schedule(schedule_id) ON DELETE NO ACTION NOT DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE xhb_disp_mgr_court_site ADD CONSTRAINT dm_xhibit_court_site_id_fk FOREIGN KEY (xhibit_court_site_id) REFERENCES xhb_court_site(court_site_id) ON DELETE SET NULL NOT DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE xhb_disp_mgr_local_proxy ADD CONSTRAINT dm_local_proxy_site_id_fk FOREIGN KEY (court_site_id) REFERENCES xhb_disp_mgr_court_site(court_site_id) ON DELETE NO ACTION NOT DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE xhb_disp_mgr_log ADD CONSTRAINT dm_log_cdu_id_fk FOREIGN KEY (cdu_id) REFERENCES xhb_disp_mgr_cdu(cdu_id) ON DELETE SET NULL NOT DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE xhb_disp_mgr_log ADD CONSTRAINT dm_log_court_site_id_fk FOREIGN KEY (court_site_id) REFERENCES xhb_disp_mgr_court_site(court_site_id) ON DELETE NO ACTION NOT DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE xhb_disp_mgr_mapping ADD CONSTRAINT dm_cdu_id_fk FOREIGN KEY (cdu_id) REFERENCES xhb_disp_mgr_cdu(cdu_id) ON DELETE NO ACTION NOT DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE xhb_disp_mgr_mapping ADD CONSTRAINT dm_url_id_fk FOREIGN KEY (url_id) REFERENCES xhb_disp_mgr_url(url_id) ON DELETE NO ACTION NOT DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE xhb_disp_mgr_url ADD CONSTRAINT dm_url_court_site_id_fk FOREIGN KEY (court_site_id) REFERENCES xhb_court_site(court_site_id) ON DELETE SET NULL NOT DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE xhb_dm_qz_blob_triggers ADD CONSTRAINT dm_qz_blob_trig_to_trig_fk FOREIGN KEY (sched_name,trigger_name,trigger_group) REFERENCES xhb_dm_qz_triggers(sched_name,trigger_name,trigger_group) ON DELETE NO ACTION NOT DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE xhb_dm_qz_cron_triggers ADD CONSTRAINT dm_qz_cron_trig_to_trig_fk FOREIGN KEY (sched_name,trigger_name,trigger_group) REFERENCES xhb_dm_qz_triggers(sched_name,trigger_name,trigger_group) ON DELETE NO ACTION NOT DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE xhb_dm_qz_simple_triggers ADD CONSTRAINT dm_qz_simple_trig_to_trig_fk FOREIGN KEY (sched_name,trigger_name,trigger_group) REFERENCES xhb_dm_qz_triggers(sched_name,trigger_name,trigger_group) ON DELETE NO ACTION NOT DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE xhb_dm_qz_simprop_triggers ADD CONSTRAINT dm_qz_simprop_trig_to_trig_fk FOREIGN KEY (sched_name,trigger_name,trigger_group) REFERENCES xhb_dm_qz_triggers(sched_name,trigger_name,trigger_group) ON DELETE NO ACTION NOT DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE xhb_dm_qz_triggers ADD CONSTRAINT dm_qz_trig_to_jobs_fk FOREIGN KEY (sched_name,job_name,job_group) REFERENCES xhb_dm_qz_job_details(sched_name,job_name,job_group) ON DELETE NO ACTION NOT DEFERRABLE INITIALLY IMMEDIATE;
