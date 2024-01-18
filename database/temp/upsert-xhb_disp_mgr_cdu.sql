TRUNCATE temp.tmp_disp_mgr_cdu;
\cd data
\copy temp.tmp_disp_mgr_cdu (cdu_id, cdu_number, mac_address, ip_address, title, description, location, notification, refresh, weighting, offline_ind, rag_status, rag_status_date, court_site_id, last_update_date, creation_date, created_by, last_updated_by, version) FROM 'XHB_DISP_MGR_CDU_DATA_TABLE.csv' DELIMITER ',' CSV HEADER
\cd ..

ALTER TABLE xhb_disp_mgr_cdu DISABLE TRIGGER ALL;

WITH upsert as (
	update public.xhb_disp_mgr_cdu t2
	set cdu_number=t1.cdu_number,
	mac_address=t1.mac_address,
	ip_address=t1.ip_address,
	title=t1.title,
	description=t1.description,
	location=t1.location,
	notification=t1.notification,
	refresh=t1.refresh,
	weighting=t1.weighting,
	offline_ind=t1.offline_ind,
	rag_status=t1.rag_status,
	rag_status_date=t1.rag_status_date,
	court_site_id=t1.court_site_id,
	last_update_date=t1.last_update_date,
	creation_date=t1.creation_date,
	created_by=t1.created_by,
	last_updated_by=t1.last_updated_by,
	version=t1.version
	from temp.tmp_disp_mgr_cdu t1
	where t2.cdu_id=t1.cdu_id
	RETURNING t2.*)
insert into public.xhb_disp_mgr_cdu
select p.cdu_id, p.cdu_number, p.mac_address, p.ip_address, p.title, p.description, p.location, p.notification, p.refresh, p.weighting, p.offline_ind, p.rag_status, p.rag_status_date, p.court_site_id, p.last_update_date, p.creation_date, p.created_by, p.last_updated_by, p.version
from temp.tmp_disp_mgr_cdu p
where p.cdu_id not in (select q.cdu_id from upsert q);

SELECT setval('dm_cdu_seq', COALESCE(MAX(cdu_id)+1, 1), FALSE) FROM xhb_disp_mgr_cdu;

ALTER TABLE xhb_disp_mgr_cdu ENABLE TRIGGER ALL;
