SET client_encoding TO 'UTF8';

DROP SCHEMA IF EXISTS xhb_custom_pkg CASCADE;
CREATE SCHEMA IF NOT EXISTS xhb_custom_pkg;

CREATE OR REPLACE FUNCTION xhb_custom_pkg.is_connection_pool_user () RETURNS bigint AS $body$
DECLARE

    l_conn_user  varchar(255);
    l_curr_user  varchar(255);

BEGIN

    SELECT current_setting('SESSION_USER', true)
    INTO STRICT   l_curr_user;

    IF (l_curr_user IS NULL) THEN
		RAISE EXCEPTION 'no_sess_user' USING ERRCODE = '50009';
    END IF;

    SELECT connection_pool_user_name
    INTO STRICT   l_conn_user
    FROM   xhb_sys_user_information;

    IF (l_conn_user IS NULL) THEN
		RAISE EXCEPTION 'no_data' USING ERRCODE = '50008';
    END IF;

    IF ( l_conn_user != l_curr_user) THEN
		/* Not Conection Pool User */
		RETURN 0;
    ELSE
		/* Connection Pool User */
		RETURN 1;
    END IF;

EXCEPTION
	WHEN SQLSTATE '50009' THEN
		RAISE NOTICE 'CURRENT_SETTING DID NOT RETURN SESSION_USER - %', sqlerrm;
        RETURN 1;

	WHEN SQLSTATE '50008' THEN
		RAISE NOTICE 'XHB_SYS_USER_INFORMATION IS EMPTY - %', sqlerrm;
		RETURN 1;

	WHEN others THEN
		RAISE NOTICE 'ERROR ENCOUNTERED IN IS_CONNECTION_POOL_USER - %', sqlerrm;
		RETURN 1;

END;

$body$
LANGUAGE PLPGSQL
SECURITY DEFINER
 STABLE;
-- REVOKE ALL ON FUNCTION xhb_custom_pkg.is_connection_pool_user () FROM PUBLIC;


CREATE OR REPLACE FUNCTION xhb_custom_pkg.is_audit_required (table_name text) RETURNS bigint AS $body$
DECLARE

	c_audit CURSOR FOR
	SELECT auditable
	FROM   xhb_sys_audit
	WHERE  table_to_audit = table_name;

	l_audit varchar(1);

BEGIN

	IF EXISTS(SELECT * FROM pg_cursors WHERE name = 'c_audit') THEN
		CLOSE c_audit;
	END IF;

    OPEN c_audit;
    FETCH c_audit
    INTO  l_audit;
    CLOSE c_audit;

	IF l_audit = 'Y' THEN
		/* Audit is Required */
		RETURN 1;
	ELSE
		/* Audit is not Required */
		RETURN 0;
	END IF;

END;


$body$
LANGUAGE PLPGSQL
SECURITY DEFINER
;
-- REVOKE ALL ON FUNCTION xhb_custom_pkg.is_audit_required (table_name text) FROM PUBLIC;
-- End of Oracle package 'XHB_CUSTOM_PKG' declaration
