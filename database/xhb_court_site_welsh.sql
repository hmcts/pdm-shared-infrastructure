SET client_encoding TO 'UTF8';

\c pdda;

CREATE TABLE xhb_court_site_welsh (
	court_site_welsh_id integer NOT NULL,
	court_site_name varchar(255),
	court_site_id integer NOT NULL,
	last_update_date timestamp NOT NULL,
	creation_date timestamp NOT NULL,
	created_by varchar(30) NOT NULL,
	last_updated_by varchar(30) NOT NULL,
	version integer NOT NULL
) ;
ALTER TABLE xhb_court_site_welsh ADD CONSTRAINT xhb_court_site_welsh_pk PRIMARY KEY (court_site_welsh_id);

ALTER TABLE xhb_court_site_welsh ADD CONSTRAINT court_site_id_welsh_fk FOREIGN KEY (court_site_id) REFERENCES xhb_court_site(court_site_id) ON DELETE 
NO ACTION NOT DEFERRABLE INITIALLY IMMEDIATE;
