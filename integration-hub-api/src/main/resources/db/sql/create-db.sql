-- DROP TABLE app_id_lookup;

CREATE TABLE app_id_lookup
(
  id serial NOT NULL,
  eid text,
  app_name text,
  app_id text,
  instance_name text
) ;

