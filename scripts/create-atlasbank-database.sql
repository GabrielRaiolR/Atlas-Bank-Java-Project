-- Rode UMA VEZ como superuser (geralmente o usuário "postgres").
-- psql (Windows, no PATH do Postgres):
--   psql -U postgres -h localhost -f scripts/create-atlasbank-database.sql
--
-- Ou no pgAdmin: Query Tool → colar e executar.

CREATE DATABASE atlasbank
  WITH OWNER = CURRENT_USER
  ENCODING 'UTF8'
  LC_COLLATE = 'en_US.utf8'
  LC_CTYPE = 'en_US.utf8'
  TEMPLATE = template0;

-- Se o seu Postgres no Windows reclamar de LC_* (locale), use em vez disso:
-- CREATE DATABASE atlasbank WITH OWNER = CURRENT_USER ENCODING 'UTF8' TEMPLATE = template0;
