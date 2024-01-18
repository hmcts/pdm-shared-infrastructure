TRUNCATE temp.tmp_disp_mgr_schedule;
\cd data
\copy temp.tmp_disp_mgr_schedule (schedule_id, schedule_type, title, detail, last_update_date, creation_date, created_by, last_updated_by, version) FROM 'XHB_DISP_MGR_SCHEDULE_DATA_TABLE.csv' DELIMITER ',' CSV HEADER
\cd ..

ALTER TABLE xhb_disp_mgr_schedule DISABLE TRIGGER ALL;

WITH upsert as (
	update public.xhb_disp_mgr_schedule t2
	set schedule_type=t1.schedule_type,
	title=t1.title,
	detail=t1.detail,
	last_update_date=t1.last_update_date,
	creation_date=t1.creation_date,
	created_by=t1.created_by,
	last_updated_by=t1.last_updated_by,
	version=t1.version
	from temp.tmp_disp_mgr_schedule t1
	where t2.schedule_id=t1.schedule_id
	RETURNING t2.*)
insert into public.xhb_disp_mgr_schedule
select p.schedule_id, p.schedule_type, p.title, p.detail, p.last_update_date, p.creation_date, p.created_by, p.last_updated_by, p.version
from temp.tmp_disp_mgr_schedule p
where p.schedule_id not in (select q.schedule_id from upsert q);

SELECT setval('dm_schedule_seq', COALESCE(MAX(schedule_id)+1, 1), FALSE) FROM xhb_disp_mgr_schedule;

ALTER TABLE xhb_disp_mgr_schedule ENABLE TRIGGER ALL;
