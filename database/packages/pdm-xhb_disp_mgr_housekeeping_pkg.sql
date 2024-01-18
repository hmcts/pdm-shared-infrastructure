SET client_encoding TO 'UTF8';

DROP SCHEMA IF EXISTS xhb_disp_mgr_housekeeping_pkg CASCADE;
CREATE SCHEMA IF NOT EXISTS xhb_disp_mgr_housekeeping_pkg;

CREATE OR REPLACE PROCEDURE xhb_disp_mgr_housekeeping_pkg.initiate_run () AS $body$
DECLARE
rowcount int;
BEGIN

	-- High Record Throughput Tables, to delete daily for automated XHIBIT updates and after 30 days for all users
	DELETE FROM XHB_DISP_MGR_SERVICE_AUDIT WHERE (LAST_UPDATE_DATE <= date_trunc('day', clock_timestamp()) - make_interval(days=>1) AND LAST_UPDATED_BY = 'XHIBIT');
	GET DIAGNOSTICS rowcount = ROW_COUNT;

	RAISE NOTICE 'XHB_DISP_MGR_SERVICE_AUDIT xhibit rows deleted: %',  rowcount;
	DELETE FROM XHB_DISP_MGR_SERVICE_AUDIT WHERE (LAST_UPDATE_DATE <= date_trunc('day', clock_timestamp()) - make_interval(days=>30));
	GET DIAGNOSTICS rowcount = ROW_COUNT;

	RAISE NOTICE 'XHB_DISP_MGR_SERVICE_AUDIT user rows deleted: %',  rowcount;
	DELETE FROM AUD_DISP_MGR_CDU WHERE (LAST_UPDATE_DATE <= date_trunc('day', clock_timestamp()) - make_interval(days=>1) AND LAST_UPDATED_BY = 'XHIBIT');
	GET DIAGNOSTICS rowcount = ROW_COUNT;

	RAISE NOTICE 'AUD_DISP_MGR_CDU xhibit rows deleted: %',  rowcount;
	DELETE FROM AUD_DISP_MGR_CDU WHERE (LAST_UPDATE_DATE <= date_trunc('day', clock_timestamp()) - make_interval(days=>30));
	GET DIAGNOSTICS rowcount = ROW_COUNT;

	RAISE NOTICE 'AUD_DISP_MGR_CDU user rows deleted: %',  rowcount;
	DELETE FROM AUD_DISP_MGR_COURT_SITE WHERE (LAST_UPDATE_DATE <= date_trunc('day', clock_timestamp()) - make_interval(days=>1) AND LAST_UPDATED_BY = 'XHIBIT');
	GET DIAGNOSTICS rowcount = ROW_COUNT;

	RAISE NOTICE 'AUD_DISP_MGR_COURT_SITE xhibit rows deleted: %',  rowcount;
	DELETE FROM AUD_DISP_MGR_COURT_SITE WHERE (LAST_UPDATE_DATE <= date_trunc('day', clock_timestamp()) - make_interval(days=>30));
	GET DIAGNOSTICS rowcount = ROW_COUNT;

	RAISE NOTICE 'AUD_DISP_MGR_COURT_SITE user rows deleted: %',  rowcount;
	DELETE FROM AUD_DISP_MGR_LOCAL_PROXY WHERE (LAST_UPDATE_DATE <= date_trunc('day', clock_timestamp()) - make_interval(days=>1) AND LAST_UPDATED_BY = 'XHIBIT');
	GET DIAGNOSTICS rowcount = ROW_COUNT;

	RAISE NOTICE 'AUD_DISP_MGR_LOCAL_PROXY xhibit rows deleted: %',  rowcount;
	DELETE FROM AUD_DISP_MGR_LOCAL_PROXY WHERE (LAST_UPDATE_DATE <= date_trunc('day', clock_timestamp()) - make_interval(days=>30));
	GET DIAGNOSTICS rowcount = ROW_COUNT;

	RAISE NOTICE 'AUD_DISP_MGR_LOCAL_PROXY user rows deleted: %',  rowcount;

	-- Low Record Throughput Tables, delete after 30 days for all users
	DELETE FROM AUD_DISP_MGR_LOG WHERE (LAST_UPDATE_DATE <= date_trunc('day', clock_timestamp()) - make_interval(days=>30));
	GET DIAGNOSTICS rowcount = ROW_COUNT;

	RAISE NOTICE 'AUD_DISP_MGR_LOG rows deleted: %',  rowcount;
	DELETE FROM AUD_DISP_MGR_PROPERTY WHERE (LAST_UPDATE_DATE <= date_trunc('day', clock_timestamp()) - make_interval(days=>30));
	GET DIAGNOSTICS rowcount = ROW_COUNT;

	RAISE NOTICE 'AUD_DISP_MGR_PROPERTY rows deleted: %',  rowcount;
	DELETE FROM AUD_DISP_MGR_SCHEDULE WHERE (LAST_UPDATE_DATE <= date_trunc('day', clock_timestamp()) - make_interval(days=>30));
	GET DIAGNOSTICS rowcount = ROW_COUNT;

	RAISE NOTICE 'AUD_DISP_MGR_SCHEDULE rows deleted: %',  rowcount;
	DELETE FROM AUD_DISP_MGR_URL WHERE (LAST_UPDATE_DATE <= date_trunc('day', clock_timestamp()) - make_interval(days=>30));
	GET DIAGNOSTICS rowcount = ROW_COUNT;

	RAISE NOTICE 'AUD_DISP_MGR_URL rows deleted: %',  rowcount;
	DELETE FROM AUD_DISP_MGR_USER_DETAILS WHERE (LAST_UPDATE_DATE <= date_trunc('day', clock_timestamp()) - make_interval(days=>30));
	GET DIAGNOSTICS rowcount = ROW_COUNT;

	RAISE NOTICE 'AUD_DISP_MGR_USER_DETAILS rows deleted: %',  rowcount;

	-- Delete on Creation Date and not Update date
	DELETE FROM AUD_DISP_MGR_MAPPING WHERE (CREATION_DATE <= date_trunc('day', clock_timestamp()) - make_interval(days=>30));
	GET DIAGNOSTICS rowcount = ROW_COUNT;

	RAISE NOTICE 'AUD_DISP_MGR_MAPPING rows deleted: %',  rowcount;

  END;

$body$
LANGUAGE PLPGSQL
SECURITY DEFINER
;
-- REVOKE ALL ON PROCEDURE xhb_disp_mgr_housekeeping_pkg.initiate_run () FROM PUBLIC;
-- End of Oracle package 'XHB_DISP_MGR_HOUSEKEEPING_PKG' declaration
