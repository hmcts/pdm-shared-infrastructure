TRUNCATE temp.tmp_disp_mgr_court_site;
\cd data
\copy temp.tmp_disp_mgr_court_site (court_site_id, title, page_url, schedule_id, xhibit_court_site_id, last_update_date, creation_date, created_by, last_updated_by, version, rag_status, rag_status_date, notification) FROM 'XHB_DISP_MGR_COURT_SITE_DATA_TABLE.csv' DELIMITER ',' CSV HEADER
\cd ..

ALTER TABLE xhb_disp_mgr_court_site DISABLE TRIGGER ALL;

WITH upsert as (
	update public.xhb_disp_mgr_court_site t2
	set title=t1.title,
	page_url=t1.page_url,
	schedule_id=t1.schedule_id,
	xhibit_court_site_id=t1.xhibit_court_site_id,
	last_update_date=t1.last_update_date,
	creation_date=t1.creation_date,
	created_by=t1.created_by,
	last_updated_by=t1.last_updated_by,
	version=t1.version,
	rag_status=t1.rag_status,
	rag_status_date=t1.rag_status_date,
	notification=t1.notification
	from temp.tmp_disp_mgr_court_site t1
	where t2.court_site_id=t1.court_site_id
	RETURNING t2.*)
insert into public.xhb_disp_mgr_court_site
select p.court_site_id, p.title, p.page_url, p.schedule_id, p.xhibit_court_site_id, p.last_update_date, p.creation_date, p.created_by, p.last_updated_by, p.version, p.rag_status, p.rag_status_date, p.notification
from temp.tmp_disp_mgr_court_site p
where p.court_site_id not in (select q.court_site_id from upsert q);

SELECT setval('dm_court_site_seq', COALESCE(MAX(court_site_id)+1, 1), FALSE) FROM xhb_disp_mgr_court_site;

ALTER TABLE xhb_disp_mgr_court_site ENABLE TRIGGER ALL;
