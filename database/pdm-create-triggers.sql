SET client_encoding TO 'UTF8';

DROP TRIGGER IF EXISTS dm_cdu_bir_tr ON xhb_disp_mgr_cdu CASCADE;
CREATE OR REPLACE FUNCTION trigger_fct_dm_cdu_bir_tr() RETURNS trigger AS $BODY$
BEGIN

	/* If ID not supplied, get next from sequence */

	IF NEW.CDU_ID IS NULL THEN
	  SELECT	nextval('dm_cdu_seq')
	  INTO STRICT		NEW.CDU_ID
	;
	END IF;

	/* If created by and updated by not supplied, set it to session user */

	IF ((NEW.LAST_UPDATED_BY IS NULL)
	   OR (NEW.CREATED_BY IS NULL) ) THEN
	  SELECT	coalesce(current_setting('SESSION_USER', true),'PDM'),
				coalesce(current_setting('SESSION_USER', true),'PDM')
	  INTO STRICT   	NEW.LAST_UPDATED_BY,
				NEW.CREATED_BY
	;
	END IF;

	/* Set created date and updated date to now and version to one */

	SELECT	LOCALTIMESTAMP,
			LOCALTIMESTAMP,
			1
	INTO STRICT	NEW.LAST_UPDATE_DATE,
			NEW.CREATION_DATE,
			NEW.VERSION
	;

RETURN NEW;
END
$BODY$
 LANGUAGE 'plpgsql' SECURITY DEFINER;
-- REVOKE ALL ON FUNCTION trigger_fct_dm_cdu_bir_tr() FROM PUBLIC;

CREATE TRIGGER dm_cdu_bir_tr
	BEFORE INSERT ON xhb_disp_mgr_cdu FOR EACH ROW
	EXECUTE PROCEDURE trigger_fct_dm_cdu_bir_tr();

DROP TRIGGER IF EXISTS dm_cdu_bur_tr ON xhb_disp_mgr_cdu CASCADE;
CREATE OR REPLACE FUNCTION trigger_fct_dm_cdu_bur_tr() RETURNS trigger AS $BODY$
DECLARE

  l_trig_event varchar(1) := NULL;
BEGIN
  BEGIN

	/* Determine whether UPDATING or DELETING */

	IF TG_OP = 'UPDATE' THEN

		l_trig_event := 'U';

		/* If the user is the connection pool user as defined in XHB_SYS_USER_INFORMATION */

		IF (XHB_CUSTOM_PKG.IS_CONNECTION_POOL_USER() = 1) THEN
		  IF (OLD.VERSION != NEW.VERSION) THEN
		    /* Someone has pulled the rug out from below! */

		    RAISE EXCEPTION 'optimistic_lock_prob' USING ERRCODE = '50011';
		  END IF;
		END IF;

		/* Increment version and set updated date to now */

		SELECT OLD.VERSION + 1,
			   LOCALTIMESTAMP
		INTO STRICT   NEW.VERSION,
			   NEW.LAST_UPDATE_DATE
		;

		/* If the user is not the connection pool user as defined in XHB_SYS_USER_INFORMATION */

		IF (XHB_CUSTOM_PKG.IS_CONNECTION_POOL_USER() = 0) THEN
		  SELECT coalesce(current_setting('SESSION_USER', true),'PDM')
		  INTO STRICT   NEW.LAST_UPDATED_BY
		;
		END IF;

	ELSE -- Must be DELETING
		l_trig_event := 'D';

	END IF;

	/* Is Auditing on this table required */

	IF (XHB_CUSTOM_PKG.IS_AUDIT_REQUIRED('XHB_DISP_MGR_CDU') = 1) THEN
		INSERT INTO AUD_DISP_MGR_CDU
		VALUES (OLD.CDU_ID,
				OLD.CDU_NUMBER,
				OLD.MAC_ADDRESS,
				OLD.IP_ADDRESS,
				OLD.TITLE,
				OLD.DESCRIPTION,
				OLD.LOCATION,
				OLD.NOTIFICATION,
				OLD.REFRESH,
				OLD.WEIGHTING,
				OLD.OFFLINE_IND,
				OLD.RAG_STATUS,
				OLD.RAG_STATUS_DATE,
				OLD.COURT_SITE_ID,
				OLD.LAST_UPDATE_DATE,
				OLD.CREATION_DATE,
				OLD.CREATED_BY,
				OLD.LAST_UPDATED_BY,
				OLD.VERSION,
				l_trig_event);
	END IF;

  END;
IF TG_OP = 'DELETE' THEN
	RETURN OLD;
ELSE
	RETURN NEW;
END IF;

END
$BODY$
 LANGUAGE 'plpgsql' SECURITY DEFINER;
-- REVOKE ALL ON FUNCTION trigger_fct_dm_cdu_bur_tr() FROM PUBLIC;

CREATE TRIGGER dm_cdu_bur_tr
	BEFORE UPDATE OR DELETE ON xhb_disp_mgr_cdu FOR EACH ROW
	EXECUTE PROCEDURE trigger_fct_dm_cdu_bur_tr();

DROP TRIGGER IF EXISTS dm_court_site_bir_tr ON xhb_disp_mgr_court_site CASCADE;
CREATE OR REPLACE FUNCTION trigger_fct_dm_court_site_bir_tr() RETURNS trigger AS $BODY$
BEGIN

	/* If ID not supplied, get next from sequence */

	IF NEW.COURT_SITE_ID IS NULL THEN
	  SELECT	nextval('dm_court_site_seq')
	  INTO STRICT		NEW.COURT_SITE_ID
	;
	END IF;

	/* If created by and updated by not supplied, set it to session user */

	IF ((NEW.LAST_UPDATED_BY IS NULL)
	   OR (NEW.CREATED_BY IS NULL) ) THEN
	  SELECT	coalesce(current_setting('SESSION_USER', true),'PDM'),
				coalesce(current_setting('SESSION_USER', true),'PDM')
	  INTO STRICT   	NEW.LAST_UPDATED_BY,
				NEW.CREATED_BY
	;
	END IF;

	/* Set created date and updated date to now and version to one */

	SELECT	LOCALTIMESTAMP,
			LOCALTIMESTAMP,
			1
	INTO STRICT	NEW.LAST_UPDATE_DATE,
			NEW.CREATION_DATE,
			NEW.VERSION
	;

RETURN NEW;
END
$BODY$
 LANGUAGE 'plpgsql' SECURITY DEFINER;
-- REVOKE ALL ON FUNCTION trigger_fct_dm_court_site_bir_tr() FROM PUBLIC;

CREATE TRIGGER dm_court_site_bir_tr
	BEFORE INSERT ON xhb_disp_mgr_court_site FOR EACH ROW
	EXECUTE PROCEDURE trigger_fct_dm_court_site_bir_tr();

DROP TRIGGER IF EXISTS dm_court_site_bur_tr ON xhb_disp_mgr_court_site CASCADE;
CREATE OR REPLACE FUNCTION trigger_fct_dm_court_site_bur_tr() RETURNS trigger AS $BODY$
DECLARE

  l_trig_event varchar(1) := NULL;
BEGIN
  BEGIN

	/* Determine whether UPDATING or DELETING */

	IF TG_OP = 'UPDATE' THEN

		l_trig_event := 'U';

		/* If the user is the connection pool user as defined in XHB_SYS_USER_INFORMATION */

		IF (XHB_CUSTOM_PKG.IS_CONNECTION_POOL_USER() = 1) THEN
		  IF (OLD.VERSION != NEW.VERSION) THEN
		    /* Someone has pulled the rug out from below! */

		    RAISE EXCEPTION 'optimistic_lock_prob' USING ERRCODE = '50011';
		  END IF;
		END IF;

		/* Increment version and set updated date to now */

		SELECT OLD.VERSION + 1,
			   LOCALTIMESTAMP
		INTO STRICT   NEW.VERSION,
			   NEW.LAST_UPDATE_DATE
		;

		/* If the user is not the connection pool user as defined in XHB_SYS_USER_INFORMATION */

		IF (XHB_CUSTOM_PKG.IS_CONNECTION_POOL_USER() = 0) THEN
		  SELECT coalesce(current_setting('SESSION_USER', true),'PDM')
		  INTO STRICT   NEW.LAST_UPDATED_BY
		;
		END IF;

	ELSE -- Must be DELETING
		l_trig_event := 'D';

	END IF;

	/* Is Auditing on this table required */

	IF (XHB_CUSTOM_PKG.IS_AUDIT_REQUIRED('XHB_DISP_MGR_COURT_SITE') = 1) THEN
		INSERT INTO AUD_DISP_MGR_COURT_SITE
		VALUES (OLD.COURT_SITE_ID,
				OLD.TITLE,
				OLD.PAGE_URL,
				OLD.SCHEDULE_ID,
				OLD.XHIBIT_COURT_SITE_ID,
				OLD.LAST_UPDATE_DATE,
				OLD.CREATION_DATE,
				OLD.CREATED_BY,
				OLD.LAST_UPDATED_BY,
				OLD.VERSION,
				l_trig_event,
				OLD.RAG_STATUS,
				OLD.RAG_STATUS_DATE,
				OLD.NOTIFICATION);
	END IF;

  END;
IF TG_OP = 'DELETE' THEN
	RETURN OLD;
ELSE
	RETURN NEW;
END IF;

END
$BODY$
 LANGUAGE 'plpgsql' SECURITY DEFINER;
-- REVOKE ALL ON FUNCTION trigger_fct_dm_court_site_bur_tr() FROM PUBLIC;

CREATE TRIGGER dm_court_site_bur_tr
	BEFORE UPDATE OR DELETE ON xhb_disp_mgr_court_site FOR EACH ROW
	EXECUTE PROCEDURE trigger_fct_dm_court_site_bur_tr();

DROP TRIGGER IF EXISTS dm_local_proxy_bir_tr ON xhb_disp_mgr_local_proxy CASCADE;
CREATE OR REPLACE FUNCTION trigger_fct_dm_local_proxy_bir_tr() RETURNS trigger AS $BODY$
BEGIN

	/* If ID not supplied, get next from sequence */

	IF NEW.LOCAL_PROXY_ID IS NULL THEN
	  SELECT	nextval('dm_local_proxy_seq')
	  INTO STRICT		NEW.LOCAL_PROXY_ID
	;
	END IF;

	/* If created by and updated by not supplied, set it to session user */

	IF ((NEW.LAST_UPDATED_BY IS NULL)
	   OR (NEW.CREATED_BY IS NULL) ) THEN
	  SELECT	coalesce(current_setting('SESSION_USER', true),'PDM'),
				coalesce(current_setting('SESSION_USER', true),'PDM')
	  INTO STRICT   	NEW.LAST_UPDATED_BY,
				NEW.CREATED_BY
	;
	END IF;

	/* Set created date and updated date to now and version to one */

	SELECT	LOCALTIMESTAMP,
			LOCALTIMESTAMP,
			1
	INTO STRICT	NEW.LAST_UPDATE_DATE,
			NEW.CREATION_DATE,
			NEW.VERSION
	;

RETURN NEW;
END
$BODY$
 LANGUAGE 'plpgsql' SECURITY DEFINER;
-- REVOKE ALL ON FUNCTION trigger_fct_dm_local_proxy_bir_tr() FROM PUBLIC;

CREATE TRIGGER dm_local_proxy_bir_tr
	BEFORE INSERT ON xhb_disp_mgr_local_proxy FOR EACH ROW
	EXECUTE PROCEDURE trigger_fct_dm_local_proxy_bir_tr();

DROP TRIGGER IF EXISTS dm_local_proxy_bur_tr ON xhb_disp_mgr_local_proxy CASCADE;
CREATE OR REPLACE FUNCTION trigger_fct_dm_local_proxy_bur_tr() RETURNS trigger AS $BODY$
DECLARE

  l_trig_event varchar(1) := NULL;
BEGIN
  BEGIN

	/* Determine whether UPDATING or DELETING */

	IF TG_OP = 'UPDATE' THEN

		l_trig_event := 'U';

		/* If the user is the connection pool user as defined in XHB_SYS_USER_INFORMATION */

		IF (XHB_CUSTOM_PKG.IS_CONNECTION_POOL_USER() = 1) THEN
		  IF (OLD.VERSION != NEW.VERSION) THEN
		    /* Someone has pulled the rug out from below! */

		    RAISE EXCEPTION 'optimistic_lock_prob' USING ERRCODE = '50011';
		  END IF;
		END IF;

		/* Increment version and set updated date to now */

		SELECT OLD.VERSION + 1,
			   LOCALTIMESTAMP
		INTO STRICT   NEW.VERSION,
			   NEW.LAST_UPDATE_DATE
		;

		/* If the user is not the connection pool user as defined in XHB_SYS_USER_INFORMATION */

		IF (XHB_CUSTOM_PKG.IS_CONNECTION_POOL_USER() = 0) THEN
		  SELECT coalesce(current_setting('SESSION_USER', true),'PDM')
		  INTO STRICT   NEW.LAST_UPDATED_BY
		;
		END IF;

	ELSE -- Must be DELETING
		l_trig_event := 'D';

	END IF;

	/* Is Auditing on this table required */

	IF (XHB_CUSTOM_PKG.IS_AUDIT_REQUIRED('XHB_DISP_MGR_LOCAL_PROXY') = 1) THEN
		INSERT INTO AUD_DISP_MGR_LOCAL_PROXY
		VALUES (OLD.LOCAL_PROXY_ID,
				OLD.IP_ADDRESS,
				OLD.HOSTNAME,
				OLD.RAG_STATUS,
				OLD.RAG_STATUS_DATE,
				OLD.COURT_SITE_ID,
				OLD.LAST_UPDATE_DATE,
				OLD.CREATION_DATE,
				OLD.CREATED_BY,
				OLD.LAST_UPDATED_BY,
				OLD.VERSION,
				l_trig_event);
	END IF;

  END;
IF TG_OP = 'DELETE' THEN
	RETURN OLD;
ELSE
	RETURN NEW;
END IF;

END
$BODY$
 LANGUAGE 'plpgsql' SECURITY DEFINER;
-- REVOKE ALL ON FUNCTION trigger_fct_dm_local_proxy_bur_tr() FROM PUBLIC;

CREATE TRIGGER dm_local_proxy_bur_tr
	BEFORE UPDATE OR DELETE ON xhb_disp_mgr_local_proxy FOR EACH ROW
	EXECUTE PROCEDURE trigger_fct_dm_local_proxy_bur_tr();

DROP TRIGGER IF EXISTS dm_log_bir_tr ON xhb_disp_mgr_log CASCADE;
CREATE OR REPLACE FUNCTION trigger_fct_dm_log_bir_tr() RETURNS trigger AS $BODY$
BEGIN

	/* If ID not supplied, get next from sequence */

	IF NEW.LOG_ID IS NULL THEN
	  SELECT	nextval('dm_log_seq')
	  INTO STRICT		NEW.LOG_ID
	;
	END IF;

	/* If created by and updated by not supplied, set it to session user */

	IF ((NEW.LAST_UPDATED_BY IS NULL)
	   OR (NEW.CREATED_BY IS NULL) ) THEN
	  SELECT	coalesce(current_setting('SESSION_USER', true),'PDM'),
				coalesce(current_setting('SESSION_USER', true),'PDM')
	  INTO STRICT   	NEW.LAST_UPDATED_BY,
				NEW.CREATED_BY
	;
	END IF;

	/* Set created date and updated date to now and version to one */

	SELECT	LOCALTIMESTAMP,
			LOCALTIMESTAMP,
			1
	INTO STRICT	NEW.LAST_UPDATE_DATE,
			NEW.CREATION_DATE,
			NEW.VERSION
	;

RETURN NEW;
END
$BODY$
 LANGUAGE 'plpgsql' SECURITY DEFINER;
-- REVOKE ALL ON FUNCTION trigger_fct_dm_log_bir_tr() FROM PUBLIC;

CREATE TRIGGER dm_log_bir_tr
	BEFORE INSERT ON xhb_disp_mgr_log FOR EACH ROW
	EXECUTE PROCEDURE trigger_fct_dm_log_bir_tr();

DROP TRIGGER IF EXISTS dm_log_bur_tr ON xhb_disp_mgr_log CASCADE;
CREATE OR REPLACE FUNCTION trigger_fct_dm_log_bur_tr() RETURNS trigger AS $BODY$
DECLARE

  l_trig_event varchar(1) := NULL;
BEGIN
  BEGIN

	/* Determine whether UPDATING or DELETING */

	IF TG_OP = 'UPDATE' THEN

		l_trig_event := 'U';

		/* If the user is the connection pool user as defined in XHB_SYS_USER_INFORMATION */

		IF (XHB_CUSTOM_PKG.IS_CONNECTION_POOL_USER() = 1) THEN
		  IF (OLD.VERSION != NEW.VERSION) THEN
		    /* Someone has pulled the rug out from below! */

		    RAISE EXCEPTION 'optimistic_lock_prob' USING ERRCODE = '50011';
		  END IF;
		END IF;

		/* Increment version and set updated date to now */

		SELECT OLD.VERSION + 1,
			   LOCALTIMESTAMP
		INTO STRICT   NEW.VERSION,
			   NEW.LAST_UPDATE_DATE
		;

		/* If the user is not the connection pool user as defined in XHB_SYS_USER_INFORMATION */

		IF (XHB_CUSTOM_PKG.IS_CONNECTION_POOL_USER() = 0) THEN
		  SELECT coalesce(current_setting('SESSION_USER', true),'PDM')
		  INTO STRICT   NEW.LAST_UPDATED_BY
		;
		END IF;

	ELSE -- Must be DELETING
		l_trig_event := 'D';

	END IF;

	/* Is Auditing on this table required */

	IF (XHB_CUSTOM_PKG.IS_AUDIT_REQUIRED('XHB_DISP_MGR_LOG') = 1) THEN
		INSERT INTO AUD_DISP_MGR_LOG
		VALUES (OLD.LOG_ID,
				OLD.COURT_SITE_ID,
				OLD.CDU_ID,
				OLD.LAST_UPDATE_DATE,
				OLD.CREATION_DATE,
				OLD.CREATED_BY,
				OLD.LAST_UPDATED_BY,
				OLD.VERSION,
				l_trig_event);
	END IF;

  END;
IF TG_OP = 'DELETE' THEN
	RETURN OLD;
ELSE
	RETURN NEW;
END IF;

END
$BODY$
 LANGUAGE 'plpgsql' SECURITY DEFINER;
-- REVOKE ALL ON FUNCTION trigger_fct_dm_log_bur_tr() FROM PUBLIC;

CREATE TRIGGER dm_log_bur_tr
	BEFORE UPDATE OR DELETE ON xhb_disp_mgr_log FOR EACH ROW
	EXECUTE PROCEDURE trigger_fct_dm_log_bur_tr();

DROP TRIGGER IF EXISTS dm_mapping_bdr_tr ON xhb_disp_mgr_mapping CASCADE;
CREATE OR REPLACE FUNCTION trigger_fct_dm_mapping_bdr_tr() RETURNS trigger AS $BODY$
BEGIN


	/* Is Auditing on this table required */

	IF (XHB_CUSTOM_PKG.IS_AUDIT_REQUIRED('XHB_DISP_MGR_MAPPING') = 1) THEN
		INSERT INTO AUD_DISP_MGR_MAPPING
		VALUES (OLD.URL_ID,
				OLD.CDU_ID,
				OLD.CREATION_DATE,
				OLD.CREATED_BY,
				'D');
	END IF;

RETURN OLD;
END
$BODY$
 LANGUAGE 'plpgsql' SECURITY DEFINER;
-- REVOKE ALL ON FUNCTION trigger_fct_dm_mapping_bdr_tr() FROM PUBLIC;

CREATE TRIGGER dm_mapping_bdr_tr
	BEFORE DELETE ON xhb_disp_mgr_mapping FOR EACH ROW
	EXECUTE PROCEDURE trigger_fct_dm_mapping_bdr_tr();

DROP TRIGGER IF EXISTS dm_mapping_bir_tr ON xhb_disp_mgr_mapping CASCADE;
CREATE OR REPLACE FUNCTION trigger_fct_dm_mapping_bir_tr() RETURNS trigger AS $BODY$
BEGIN

	/* If created by not supplied, set it to session user */

	IF (NEW.CREATED_BY IS NULL) THEN
	  SELECT	coalesce(current_setting('SESSION_USER', true),'PDM')
	  INTO STRICT
				NEW.CREATED_BY
	;
	END IF;

	/* Set created date */

	SELECT	LOCALTIMESTAMP
	INTO STRICT
			NEW.CREATION_DATE
	;

RETURN NEW;
END
$BODY$
 LANGUAGE 'plpgsql' SECURITY DEFINER;
-- REVOKE ALL ON FUNCTION trigger_fct_dm_mapping_bir_tr() FROM PUBLIC;

CREATE TRIGGER dm_mapping_bir_tr
	BEFORE INSERT ON xhb_disp_mgr_mapping FOR EACH ROW
	EXECUTE PROCEDURE trigger_fct_dm_mapping_bir_tr();

DROP TRIGGER IF EXISTS dm_property_bir_tr ON xhb_disp_mgr_property CASCADE;
CREATE OR REPLACE FUNCTION trigger_fct_dm_property_bir_tr() RETURNS trigger AS $BODY$
BEGIN

	/* If ID not supplied, get next from sequence */

	IF NEW.PROPERTY_ID IS NULL THEN
	  SELECT	nextval('dm_property_seq')
	  INTO STRICT		NEW.PROPERTY_ID
	;
	END IF;

	/* If created by and updated by not supplied, set it to session user */

	IF ((NEW.LAST_UPDATED_BY IS NULL)
	   OR (NEW.CREATED_BY IS NULL) ) THEN
	  SELECT	coalesce(current_setting('SESSION_USER', true),'PDM'),
				coalesce(current_setting('SESSION_USER', true),'PDM')
	  INTO STRICT   	NEW.LAST_UPDATED_BY,
				NEW.CREATED_BY
	;
	END IF;

	/* Set created date and updated date to now and version to one */

	SELECT	LOCALTIMESTAMP,
			LOCALTIMESTAMP,
			1
	INTO STRICT	NEW.LAST_UPDATE_DATE,
			NEW.CREATION_DATE,
			NEW.VERSION
	;

RETURN NEW;
END
$BODY$
 LANGUAGE 'plpgsql' SECURITY DEFINER;
-- REVOKE ALL ON FUNCTION trigger_fct_dm_property_bir_tr() FROM PUBLIC;

CREATE TRIGGER dm_property_bir_tr
	BEFORE INSERT ON xhb_disp_mgr_property FOR EACH ROW
	EXECUTE PROCEDURE trigger_fct_dm_property_bir_tr();

DROP TRIGGER IF EXISTS dm_property_bur_tr ON xhb_disp_mgr_property CASCADE;
CREATE OR REPLACE FUNCTION trigger_fct_dm_property_bur_tr() RETURNS trigger AS $BODY$
DECLARE

  l_trig_event varchar(1) := NULL;
BEGIN
  BEGIN

	/* Determine whether UPDATING or DELETING */

	IF TG_OP = 'UPDATE' THEN

		l_trig_event := 'U';

		/* If the user is the connection pool user as defined in XHB_SYS_USER_INFORMATION */

		IF (XHB_CUSTOM_PKG.IS_CONNECTION_POOL_USER() = 1) THEN
		  IF (OLD.VERSION != NEW.VERSION) THEN
		    /* Someone has pulled the rug out from below! */

		    RAISE EXCEPTION 'optimistic_lock_prob' USING ERRCODE = '50011';
		  END IF;
		END IF;

		/* Increment version and set updated date to now */

		SELECT OLD.VERSION + 1,
			   LOCALTIMESTAMP
		INTO STRICT   NEW.VERSION,
			   NEW.LAST_UPDATE_DATE
		;

		/* If the user is not the connection pool user as defined in XHB_SYS_USER_INFORMATION */

		IF (XHB_CUSTOM_PKG.IS_CONNECTION_POOL_USER() = 0) THEN
		  SELECT coalesce(current_setting('SESSION_USER', true),'PDM')
		  INTO STRICT   NEW.LAST_UPDATED_BY
		;
		END IF;

	ELSE -- Must be DELETING
		l_trig_event := 'D';

	END IF;

	/* Is Auditing on this table required */

	IF (XHB_CUSTOM_PKG.IS_AUDIT_REQUIRED('XHB_DISP_MGR_PROPERTY') = 1) THEN
		INSERT INTO AUD_DISP_MGR_PROPERTY
		VALUES (OLD.PROPERTY_ID,
				OLD.PROPERTY_NAME,
				OLD.PROPERTY_VALUE,
				OLD.LAST_UPDATE_DATE,
				OLD.CREATION_DATE,
				OLD.CREATED_BY,
				OLD.LAST_UPDATED_BY,
				OLD.VERSION,
				l_trig_event);
	END IF;

  END;
IF TG_OP = 'DELETE' THEN
	RETURN OLD;
ELSE
	RETURN NEW;
END IF;

END
$BODY$
 LANGUAGE 'plpgsql' SECURITY DEFINER;
-- REVOKE ALL ON FUNCTION trigger_fct_dm_property_bur_tr() FROM PUBLIC;

CREATE TRIGGER dm_property_bur_tr
	BEFORE UPDATE OR DELETE ON xhb_disp_mgr_property FOR EACH ROW
	EXECUTE PROCEDURE trigger_fct_dm_property_bur_tr();

DROP TRIGGER IF EXISTS dm_schedule_bir_tr ON xhb_disp_mgr_schedule CASCADE;
CREATE OR REPLACE FUNCTION trigger_fct_dm_schedule_bir_tr() RETURNS trigger AS $BODY$
BEGIN

	/* If ID not supplied, get next from sequence */

	IF NEW.SCHEDULE_ID IS NULL THEN
	  SELECT	nextval('dm_schedule_seq')
	  INTO STRICT		NEW.SCHEDULE_ID
	;
	END IF;

	/* If created by and updated by not supplied, set it to session user */

	IF ((NEW.LAST_UPDATED_BY IS NULL)
	   OR (NEW.CREATED_BY IS NULL) ) THEN
	  SELECT	coalesce(current_setting('SESSION_USER', true),'PDM'),
				coalesce(current_setting('SESSION_USER', true),'PDM')
	  INTO STRICT   	NEW.LAST_UPDATED_BY,
				NEW.CREATED_BY
	;
	END IF;

	/* Set created date and updated date to now and version to one */

	SELECT	LOCALTIMESTAMP,
			LOCALTIMESTAMP,
			1
	INTO STRICT	NEW.LAST_UPDATE_DATE,
			NEW.CREATION_DATE,
			NEW.VERSION
	;

RETURN NEW;
END
$BODY$
 LANGUAGE 'plpgsql' SECURITY DEFINER;
-- REVOKE ALL ON FUNCTION trigger_fct_dm_schedule_bir_tr() FROM PUBLIC;

CREATE TRIGGER dm_schedule_bir_tr
	BEFORE INSERT ON xhb_disp_mgr_schedule FOR EACH ROW
	EXECUTE PROCEDURE trigger_fct_dm_schedule_bir_tr();

DROP TRIGGER IF EXISTS dm_schedule_bur_tr ON xhb_disp_mgr_schedule CASCADE;
CREATE OR REPLACE FUNCTION trigger_fct_dm_schedule_bur_tr() RETURNS trigger AS $BODY$
DECLARE

  l_trig_event varchar(1) := NULL;
BEGIN
  BEGIN

	/* Determine whether UPDATING or DELETING */

	IF TG_OP = 'UPDATE' THEN

		l_trig_event := 'U';

		/* If the user is the connection pool user as defined in XHB_SYS_USER_INFORMATION */

		IF (XHB_CUSTOM_PKG.IS_CONNECTION_POOL_USER() = 1) THEN
		  IF (OLD.VERSION != NEW.VERSION) THEN
		    /* Someone has pulled the rug out from below! */

		    RAISE EXCEPTION 'optimistic_lock_prob' USING ERRCODE = '50011';
		  END IF;
		END IF;

		/* Increment version and set updated date to now */

		SELECT OLD.VERSION + 1,
			   LOCALTIMESTAMP
		INTO STRICT   NEW.VERSION,
			   NEW.LAST_UPDATE_DATE
		;

		/* If the user is not the connection pool user as defined in XHB_SYS_USER_INFORMATION */

		IF (XHB_CUSTOM_PKG.IS_CONNECTION_POOL_USER() = 0) THEN
		  SELECT coalesce(current_setting('SESSION_USER', true),'PDM')
		  INTO STRICT   NEW.LAST_UPDATED_BY
		;
		END IF;

	ELSE -- Must be DELETING
		l_trig_event := 'D';

	END IF;

	/* Is Auditing on this table required */

	IF (XHB_CUSTOM_PKG.IS_AUDIT_REQUIRED('XHB_DISP_MGR_SCHEDULE') = 1) THEN
		INSERT INTO AUD_DISP_MGR_SCHEDULE
		VALUES (OLD.SCHEDULE_ID,
				OLD.SCHEDULE_TYPE,
				OLD.TITLE,
			    OLD.DETAIL,
				OLD.LAST_UPDATE_DATE,
				OLD.CREATION_DATE,
				OLD.CREATED_BY,
				OLD.LAST_UPDATED_BY,
				OLD.VERSION,
				l_trig_event);
	END IF;

  END;
IF TG_OP = 'DELETE' THEN
	RETURN OLD;
ELSE
	RETURN NEW;
END IF;

END
$BODY$
 LANGUAGE 'plpgsql' SECURITY DEFINER;
-- REVOKE ALL ON FUNCTION trigger_fct_dm_schedule_bur_tr() FROM PUBLIC;

CREATE TRIGGER dm_schedule_bur_tr
	BEFORE UPDATE OR DELETE ON xhb_disp_mgr_schedule FOR EACH ROW
	EXECUTE PROCEDURE trigger_fct_dm_schedule_bur_tr();

DROP TRIGGER IF EXISTS dm_service_audit_bir_tr ON xhb_disp_mgr_service_audit CASCADE;
CREATE OR REPLACE FUNCTION trigger_fct_dm_service_audit_bir_tr() RETURNS trigger AS $BODY$
BEGIN

	/* If ID not supplied, get next from sequence */

	IF NEW.SERVICE_AUDIT_ID IS NULL THEN
	  SELECT	nextval('dm_service_audit_seq')
	  INTO STRICT		NEW.SERVICE_AUDIT_ID
	;
	END IF;

	/* If created by and updated by not supplied, set it to session user */

	IF ((NEW.LAST_UPDATED_BY IS NULL)
	   OR (NEW.CREATED_BY IS NULL) ) THEN
	  SELECT	coalesce(current_setting('SESSION_USER', true),'PDM'),
				coalesce(current_setting('SESSION_USER', true),'PDM')
	  INTO STRICT   	NEW.LAST_UPDATED_BY,
				NEW.CREATED_BY
	;
	END IF;

	/* Set created date and updated date to now and version to one */

	SELECT	LOCALTIMESTAMP,
			LOCALTIMESTAMP,
			1
	INTO STRICT	NEW.LAST_UPDATE_DATE,
			NEW.CREATION_DATE,
			NEW.VERSION
	;

RETURN NEW;
END
$BODY$
 LANGUAGE 'plpgsql' SECURITY DEFINER;
-- REVOKE ALL ON FUNCTION trigger_fct_dm_service_audit_bir_tr() FROM PUBLIC;

CREATE TRIGGER dm_service_audit_bir_tr
	BEFORE INSERT ON xhb_disp_mgr_service_audit FOR EACH ROW
	EXECUTE PROCEDURE trigger_fct_dm_service_audit_bir_tr();

DROP TRIGGER IF EXISTS dm_url_bir_tr ON xhb_disp_mgr_url CASCADE;
CREATE OR REPLACE FUNCTION trigger_fct_dm_url_bir_tr() RETURNS trigger AS $BODY$
BEGIN

	/* If ID not supplied, get next from sequence */

	IF NEW.URL_ID IS NULL THEN
	  SELECT	nextval('dm_url_seq')
	  INTO STRICT		NEW.URL_ID
	;
	END IF;

	/* If created by and updated by not supplied, set it to session user */

	IF ((NEW.LAST_UPDATED_BY IS NULL)
	   OR (NEW.CREATED_BY IS NULL) ) THEN
	  SELECT	coalesce(current_setting('SESSION_USER', true),'PDM'),
				coalesce(current_setting('SESSION_USER', true),'PDM')
	  INTO STRICT   	NEW.LAST_UPDATED_BY,
				NEW.CREATED_BY
	;
	END IF;

	/* Set created date and updated date to now and version to one */

	SELECT	LOCALTIMESTAMP,
			LOCALTIMESTAMP,
			1
	INTO STRICT	NEW.LAST_UPDATE_DATE,
			NEW.CREATION_DATE,
			NEW.VERSION
	;

RETURN NEW;
END
$BODY$
 LANGUAGE 'plpgsql' SECURITY DEFINER;
-- REVOKE ALL ON FUNCTION trigger_fct_dm_url_bir_tr() FROM PUBLIC;

CREATE TRIGGER dm_url_bir_tr
	BEFORE INSERT ON xhb_disp_mgr_url FOR EACH ROW
	EXECUTE PROCEDURE trigger_fct_dm_url_bir_tr();

DROP TRIGGER IF EXISTS dm_url_bur_tr ON xhb_disp_mgr_url CASCADE;
CREATE OR REPLACE FUNCTION trigger_fct_dm_url_bur_tr() RETURNS trigger AS $BODY$
DECLARE

  l_trig_event varchar(1) := NULL;
BEGIN
  BEGIN

	/* Determine whether UPDATING or DELETING */

	IF TG_OP = 'UPDATE' THEN

		l_trig_event := 'U';

		/* If the user is the connection pool user as defined in XHB_SYS_USER_INFORMATION */

		IF (XHB_CUSTOM_PKG.IS_CONNECTION_POOL_USER() = 1) THEN
		  IF (OLD.VERSION != NEW.VERSION) THEN
		    /* Someone has pulled the rug out from below! */

		    RAISE EXCEPTION 'optimistic_lock_prob' USING ERRCODE = '50011';
		  END IF;
		END IF;

		/* Increment version and set updated date to now */

		SELECT OLD.VERSION + 1,
			   LOCALTIMESTAMP
		INTO STRICT   NEW.VERSION,
			   NEW.LAST_UPDATE_DATE
		;

		/* If the user is not the connection pool user as defined in XHB_SYS_USER_INFORMATION */

		IF (XHB_CUSTOM_PKG.IS_CONNECTION_POOL_USER() = 0) THEN
		  SELECT coalesce(current_setting('SESSION_USER', true),'PDM')
		  INTO STRICT   NEW.LAST_UPDATED_BY
		;
		END IF;

	ELSE -- Must be DELETING
		l_trig_event := 'D';

	END IF;

	/* Is Auditing on this table required */

	IF (XHB_CUSTOM_PKG.IS_AUDIT_REQUIRED('XHB_DISP_MGR_URL') = 1) THEN
		INSERT INTO AUD_DISP_MGR_URL
		VALUES (OLD.URL_ID,
				OLD.DESCRIPTION,
				OLD.URL,
				OLD.COURT_SITE_ID,
				OLD.LAST_UPDATE_DATE,
				OLD.CREATION_DATE,
				OLD.CREATED_BY,
				OLD.LAST_UPDATED_BY,
				OLD.VERSION,
				l_trig_event);
	END IF;

  END;
IF TG_OP = 'DELETE' THEN
	RETURN OLD;
ELSE
	RETURN NEW;
END IF;

END
$BODY$
 LANGUAGE 'plpgsql' SECURITY DEFINER;
-- REVOKE ALL ON FUNCTION trigger_fct_dm_url_bur_tr() FROM PUBLIC;

CREATE TRIGGER dm_url_bur_tr
	BEFORE UPDATE OR DELETE ON xhb_disp_mgr_url FOR EACH ROW
	EXECUTE PROCEDURE trigger_fct_dm_url_bur_tr();

DROP TRIGGER IF EXISTS dm_user_details_bir_tr ON xhb_disp_mgr_user_details CASCADE;
CREATE OR REPLACE FUNCTION trigger_fct_dm_user_details_bir_tr() RETURNS trigger AS $BODY$
BEGIN

	/* If ID not supplied, get next from sequence */

	IF NEW.USER_ID IS NULL THEN
	  SELECT	nextval('dm_user_details_seq')
	  INTO STRICT		NEW.USER_ID
	;
	END IF;

	/* If created by and updated by not supplied, set it to session user */

	IF ((NEW.LAST_UPDATED_BY IS NULL)
	   OR (NEW.CREATED_BY IS NULL) ) THEN
	  SELECT	coalesce(current_setting('SESSION_USER', true),'PDM'),
				coalesce(current_setting('SESSION_USER', true),'PDM')
	  INTO STRICT   	NEW.LAST_UPDATED_BY,
				NEW.CREATED_BY
	;
	END IF;

	/* Set created date and updated date to now and version to one */

	SELECT	LOCALTIMESTAMP,
			LOCALTIMESTAMP,
			1
	INTO STRICT	NEW.LAST_UPDATE_DATE,
			NEW.CREATION_DATE,
			NEW.VERSION
	;

RETURN NEW;
END
$BODY$
 LANGUAGE 'plpgsql' SECURITY DEFINER;
-- REVOKE ALL ON FUNCTION trigger_fct_dm_user_details_bir_tr() FROM PUBLIC;

CREATE TRIGGER dm_user_details_bir_tr
	BEFORE INSERT ON xhb_disp_mgr_user_details FOR EACH ROW
	EXECUTE PROCEDURE trigger_fct_dm_user_details_bir_tr();

DROP TRIGGER IF EXISTS dm_user_details_bur_tr ON xhb_disp_mgr_user_details CASCADE;
CREATE OR REPLACE FUNCTION trigger_fct_dm_user_details_bur_tr() RETURNS trigger AS $BODY$
DECLARE

  l_trig_event varchar(1) := NULL;
BEGIN
  BEGIN

	/* Determine whether UPDATING or DELETING */

	IF TG_OP = 'UPDATE' THEN

		l_trig_event := 'U';

		/* If the user is the connection pool user as defined in XHB_SYS_USER_INFORMATION */

		IF (XHB_CUSTOM_PKG.IS_CONNECTION_POOL_USER() = 1) THEN
		  IF (OLD.VERSION != NEW.VERSION) THEN
		    /* Someone has pulled the rug out from below! */

		    RAISE EXCEPTION 'optimistic_lock_prob' USING ERRCODE = '50011';
		  END IF;
		END IF;

		/* Increment version and set updated date to now */

		SELECT OLD.VERSION + 1,
			   LOCALTIMESTAMP
		INTO STRICT   NEW.VERSION,
			   NEW.LAST_UPDATE_DATE
		;

		/* If the user is not the connection pool user as defined in XHB_SYS_USER_INFORMATION */

		IF (XHB_CUSTOM_PKG.IS_CONNECTION_POOL_USER() = 0) THEN
		  SELECT coalesce(current_setting('SESSION_USER', true),'PDM')
		  INTO STRICT   NEW.LAST_UPDATED_BY
		;
		END IF;

	ELSE -- Must be DELETING
		l_trig_event := 'D';

	END IF;

	/* Is Auditing on this table required */

	IF (XHB_CUSTOM_PKG.IS_AUDIT_REQUIRED('XHB_DISP_MGR_USER_DETAILS') = 1) THEN
		INSERT INTO AUD_DISP_MGR_USER_DETAILS
		VALUES (OLD.USER_ID,
				OLD.USER_NAME,
				OLD.USER_ROLE,
				OLD.LAST_UPDATE_DATE,
				OLD.CREATION_DATE,
				OLD.CREATED_BY,
				OLD.LAST_UPDATED_BY,
				OLD.VERSION,
				l_trig_event);
	END IF;

  END;
IF TG_OP = 'DELETE' THEN
	RETURN OLD;
ELSE
	RETURN NEW;
END IF;

END
$BODY$
 LANGUAGE 'plpgsql' SECURITY DEFINER;
-- REVOKE ALL ON FUNCTION trigger_fct_dm_user_details_bur_tr() FROM PUBLIC;

CREATE TRIGGER dm_user_details_bur_tr
	BEFORE UPDATE OR DELETE ON xhb_disp_mgr_user_details FOR EACH ROW
	EXECUTE PROCEDURE trigger_fct_dm_user_details_bur_tr();
