#!/bin/bash
set -e

# Drop database if it exists
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" -c "DROP DATABASE IF EXISTS $POSTGRES_DB"

# Recreate database
createdb -U "$POSTGRES_USER" -e "$POSTGRES_DB"

# Create role for the database
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" -c "CREATE ROLE $POSTGRES_DB"

# Configure the role's privileges
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" -c "ALTER ROLE $POSTGRES_DB WITH NOSUPERUSER INHERIT NOCREATEROLE NOCREATEDB LOGIN NOREPLICATION NOBYPASSRLS PASSWORD 'md5fa21bcf5dc4fb7713b858cc15489febd'"

# Restore database from dump
pg_restore -d "cmdbuild" -U "$POSTGRES_USER" -v /docker-entrypoint-initdb.d/cohesive-db.dump