SET client_encoding TO 'UTF8';

DROP SCHEMA IF EXISTS xhb_disp_mgr_pkg CASCADE;
CREATE SCHEMA IF NOT EXISTS xhb_disp_mgr_pkg;

CREATE OR REPLACE FUNCTION xhb_disp_mgr_pkg.get_ip_host_number (pi_ip_address xhb_disp_mgr_cdu.ip_address%TYPE) RETURNS bigint AS $body$
BEGIN
	RETURN SUBSTR(pi_ip_address, -(LENGTH(pi_ip_address) - INSTR(pi_ip_address,'.',-1)));
EXCEPTION
	WHEN SQLSTATE '50011' THEN
		RETURN NULL;
END;

$body$
LANGUAGE PLPGSQL
SECURITY DEFINER
;
-- REVOKE ALL ON FUNCTION xhb_disp_mgr_pkg.get_ip_host_number (pi_ip_address xhb_disp_mgr_cdu.ip_address%TYPE) FROM PUBLIC;


CREATE OR REPLACE FUNCTION xhb_disp_mgr_pkg.host_exists_yn (pi_court_site_id xhb_disp_mgr_cdu.court_site_id%TYPE, pi_host bigint) RETURNS char AS $body$
DECLARE

	l_exists_YN char(1);
	c_host_check CURSOR FOR
		SELECT 'Y'
		FROM xhb_disp_mgr_cdu cdu
		WHERE cdu.court_site_id = pi_court_site_id
		AND xhb_disp_mgr_pkg.get_ip_host_number(cdu.ip_address) = pi_host;

BEGIN
	OPEN c_host_check;
	FETCH c_host_check INTO l_exists_YN;
	CLOSE c_host_check;
	RETURN coalesce(l_exists_YN,'N');
END;

$body$
LANGUAGE PLPGSQL
SECURITY DEFINER
 STABLE;
-- REVOKE ALL ON FUNCTION xhb_disp_mgr_pkg.host_exists_yn (pi_court_site_id xhb_disp_mgr_cdu.court_site_id%TYPE, pi_host bigint) FROM PUBLIC;


CREATE OR REPLACE PROCEDURE xhb_disp_mgr_pkg.get_next_ip_host (pi_court_site_id xhb_disp_mgr_cdu.court_site_id%TYPE, pi_min bigint, pi_max bigint, po_next_host INOUT bigint) AS $body$
DECLARE

	c_next_host CURSOR FOR
		SELECT qry.next_host
		FROM (	SELECT xhb_disp_mgr_pkg.get_ip_host_number(cdu.ip_address)+1 next_host
				FROM xhb_disp_mgr_cdu cdu
				WHERE cdu.court_site_id = pi_court_site_id
				AND NOT EXISTS (SELECT 1 FROM xhb_disp_mgr_cdu nxt_cdu
								WHERE nxt_cdu.court_site_id = cdu.court_site_id
								AND xhb_disp_mgr_pkg.get_ip_host_number(nxt_cdu.ip_address) =
									xhb_disp_mgr_pkg.get_ip_host_number(cdu.ip_address)+1)) qry
		WHERE qry.next_host BETWEEN pi_min AND pi_max
		ORDER BY 1;

BEGIN
	-- Check if the minimum value exists, if not use that
	IF xhb_disp_mgr_pkg.host_exists_yn(pi_court_site_id => pi_court_site_id,
									   pi_host          => pi_min) = 'N' THEN
		po_next_host := pi_min;
	ELSE
		-- Check for the next available host
		OPEN c_next_host;
		FETCH c_next_host INTO po_next_host;
		CLOSE c_next_host;
	END IF;
END;

$body$
LANGUAGE PLPGSQL
SECURITY DEFINER
;
-- REVOKE ALL ON PROCEDURE xhb_disp_mgr_pkg.get_next_ip_host (pi_court_site_id xhb_disp_mgr_cdu.court_site_id%TYPE, pi_min bigint, pi_max bigint, po_next_host INOUT bigint) FROM PUBLIC;
-- End of Oracle package 'XHB_DISP_MGR_PKG' declaration
