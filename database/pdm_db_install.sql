\i 'pdm-create-tables.sql'
\i 'pdm-create-audit-tables.sql'
\i 'xhb_court_site_welsh.sql'
\cd data
\i 'load_ref_data.sql'
\cd ..
\i 'pdm-create-indexes.sql'
\i 'pdm-create-fk-constraints.sql'
\i 'pdm-create-sequences.sql'
\i 'pdm-reset-sequences.sql'
\i 'pdm-create-triggers.sql'
\cd packages
\i 'pdm-xhb_custom_pkg.sql'
\i 'pdm-xhb_disp_mgr_housekeeping_pkg.sql'
\i 'pdm-xhb_disp_mgr_pkg.sql'

ANALYZE;
