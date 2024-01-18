TRUNCATE temp.tmp_disp_mgr_local_proxy;
\cd data
\copy temp.tmp_disp_mgr_local_proxy (local_proxy_id, ip_address, hostname, rag_status, rag_status_date, court_site_id, last_update_date, creation_date, created_by, last_updated_by, version) FROM 'XHB_DISP_MGR_LOCAL_PROXY_DATA_TABLE.csv' DELIMITER ',' CSV HEADER
\cd ..

ALTER TABLE xhb_disp_mgr_local_proxy DISABLE TRIGGER ALL;

WITH upsert as (
	update public.xhb_disp_mgr_local_proxy t2
	set ip_address=t1.ip_address,
	hostname=t1.hostname,
	rag_status=t1.rag_status,
	rag_status_date=t1.rag_status_date,
	court_site_id=t1.court_site_id,
	last_update_date=t1.last_update_date,
	creation_date=t1.creation_date,
	created_by=t1.created_by,
	last_updated_by=t1.last_updated_by,
	version=t1.version
	from temp.tmp_disp_mgr_local_proxy t1
	where t2.local_proxy_id=t1.local_proxy_id
	RETURNING t2.*)
insert into public.xhb_disp_mgr_local_proxy
select p.local_proxy_id, p.ip_address, p.hostname, p.rag_status, p.rag_status_date, p.court_site_id, p.last_update_date, p.creation_date, p.created_by, p.last_updated_by, p.version
from temp.tmp_disp_mgr_local_proxy p
where p.local_proxy_id not in (select q.local_proxy_id from upsert q);

SELECT setval('dm_local_proxy_seq', COALESCE(MAX(local_proxy_id)+1, 1), FALSE) FROM xhb_disp_mgr_local_proxy;

ALTER TABLE xhb_disp_mgr_local_proxy ENABLE TRIGGER ALL;
