create SCHEMA IF NOT EXISTS shorturl;

create table if NOT EXISTS shorturl.urls(
  id          SERIAL PRIMARY KEY,
  code        VARCHAR(6) NOT NULL UNIQUE,
  org_url     VARCHAR(512) NOT NULL ,
  create_time VARCHAR(19) NOT NULL DEFAULT to_char(now(), 'YYYY-MM-DD HH24:MI:SS' :: TEXT),
  update_time VARCHAR(19)        NOT NULL DEFAULT to_char(now(), 'YYYY-MM-DD HH24:MI:SS' :: TEXT),
  status      VARCHAR(10) NOT NULL  DEFAULT 'OK'
)