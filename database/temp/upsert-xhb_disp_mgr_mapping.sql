TRUNCATE temp.tmp_disp_mgr_mapping;
\cd data
\copy temp.tmp_disp_mgr_mapping (url_id, cdu_id, creation_date, created_by) FROM 'XHB_DISP_MGR_MAPPING_DATA_TABLE.csv' DELIMITER ',' CSV HEADER
\cd ..

ALTER TABLE xhb_disp_mgr_mapping DISABLE TRIGGER ALL;

WITH upsert as (
	update public.xhb_disp_mgr_mapping t2
	set creation_date=t1.creation_date,
	created_by=t1.created_by
	from temp.tmp_disp_mgr_mapping t1
	where t2.url_id=t1.url_id
	and t2.cdu_id=t1.cdu_id
	RETURNING t2.*)
insert into public.xhb_disp_mgr_mapping
select p.url_id, p.cdu_id, p.creation_date, p.created_by
from temp.tmp_disp_mgr_mapping p
where not exists (select null from upsert q where q.url_id = p.url_id and q.cdu_id = p.cdu_id);

ALTER TABLE xhb_disp_mgr_mapping ENABLE TRIGGER ALL;
