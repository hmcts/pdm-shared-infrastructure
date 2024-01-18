-- Drop foreign key constraints to prevent violations during data load
\i 'pdm-drop-fk-constraints.sql'

-- Handle 'upsert' of each table.  Each script handles loading of data into staging tables,
-- disabling triggers and merge of data from staging tables into the main tables before
-- re-enabling the triggers.
\cd temp
\i 'upsert-xhb_disp_mgr_cdu.sql'
\i 'upsert-xhb_disp_mgr_court_site.sql'
\i 'upsert-xhb_disp_mgr_local_proxy.sql'
\i 'upsert-xhb_disp_mgr_mapping.sql'
\i 'upsert-xhb_disp_mgr_property.sql'
\i 'upsert-xhb_disp_mgr_schedule.sql'
\i 'upsert-xhb_disp_mgr_url.sql'
\i 'upsert-xhb_disp_mgr_user_details.sql'
\cd ..

-- Re-instate foreign key constraints
\i 'pdm-create-fk-constraints.sql'

-- Analyze to refresh the database statistics
ANALYZE;
