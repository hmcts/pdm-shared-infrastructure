TRUNCATE temp.tmp_disp_mgr_url;
\cd data
\copy temp.tmp_disp_mgr_url (url_id, description, url, court_site_id, last_update_date, creation_date, created_by, last_updated_by, version) FROM 'XHB_DISP_MGR_URL_DATA_TABLE.csv' DELIMITER ',' CSV HEADER
\cd ..

ALTER TABLE xhb_disp_mgr_url DISABLE TRIGGER ALL;

WITH upsert as (
	update public.xhb_disp_mgr_url t2
	set description=t1.description,
	url=t1.url,
	court_site_id=t1.court_site_id,
	last_update_date=t1.last_update_date,
	creation_date=t1.creation_date,
	created_by=t1.created_by,
	last_updated_by=t1.last_updated_by,
	version=t1.version
	from temp.tmp_disp_mgr_url t1
	where t2.url_id=t1.url_id
	RETURNING t2.*)
insert into public.xhb_disp_mgr_url
select p.url_id, p.description, url, p.court_site_id, p.last_update_date, p.creation_date, p.created_by, p.last_updated_by, p.version
from temp.tmp_disp_mgr_url p
where p.url_id not in (select q.url_id from upsert q);

SELECT setval('dm_url_seq', COALESCE(MAX(url_id)+1, 1), FALSE) FROM xhb_disp_mgr_url;

ALTER TABLE xhb_disp_mgr_url ENABLE TRIGGER ALL;
