TRUNCATE temp.tmp_disp_mgr_property;
\cd data
\copy temp.tmp_disp_mgr_property (property_id, property_name, property_value, last_update_date, creation_date, created_by, last_updated_by, version) FROM 'XHB_DISP_MGR_PROPERTY_DATA_TABLE.csv' DELIMITER ',' CSV HEADER
\cd ..

ALTER TABLE xhb_disp_mgr_property DISABLE TRIGGER ALL;

WITH upsert as (
	update public.xhb_disp_mgr_property t2
	set property_name=t1.property_name,
	property_value=t1.property_value,
	last_update_date=t1.last_update_date,
	creation_date=t1.creation_date,
	created_by=t1.created_by,
	last_updated_by=t1.last_updated_by,
	version=t1.version
	from temp.tmp_disp_mgr_property t1
	where t2.property_id=t1.property_id
	RETURNING t2.*)
insert into public.xhb_disp_mgr_property
select p.property_id, p.property_name, p.property_value, p.last_update_date, p.creation_date, p.created_by, p.last_updated_by, p.version
from temp.tmp_disp_mgr_property p
where p.property_id not in (select q.property_id from upsert q);

SELECT setval('dm_property_seq', COALESCE(MAX(property_id)+1, 1), FALSE) FROM xhb_disp_mgr_property;

ALTER TABLE xhb_disp_mgr_property ENABLE TRIGGER ALL;
