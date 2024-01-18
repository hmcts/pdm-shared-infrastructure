TRUNCATE temp.tmp_disp_mgr_user_details;
\cd data
\copy temp.tmp_disp_mgr_user_details (user_id, user_name, user_role, last_update_date, creation_date, created_by, last_updated_by, version) FROM 'XHB_DISP_MGR_USER_DETAILS_DATA_TABLE.csv' DELIMITER ',' CSV HEADER
\cd ..

ALTER TABLE xhb_disp_mgr_user_details DISABLE TRIGGER ALL;

WITH upsert as (
	update public.xhb_disp_mgr_user_details t2
	set user_name=t1.user_name,
	user_role=t1.user_role,
	last_update_date=t1.last_update_date,
	creation_date=t1.creation_date,
	created_by=t1.created_by,
	last_updated_by=t1.last_updated_by,
	version=t1.version
	from temp.tmp_disp_mgr_user_details t1
	where t2.user_id=t1.user_id
	RETURNING t2.*)
insert into public.xhb_disp_mgr_user_details
select p.user_id, p.user_name, p.user_role, p.last_update_date, p.creation_date, p.created_by, p.last_updated_by, p.version
from temp.tmp_disp_mgr_user_details p
where p.user_id not in (select q.user_id from upsert q);

SELECT setval('dm_user_details_seq', COALESCE(MAX(user_id)+1, 1), FALSE) FROM xhb_disp_mgr_user_details;

ALTER TABLE xhb_disp_mgr_user_details ENABLE TRIGGER ALL;
