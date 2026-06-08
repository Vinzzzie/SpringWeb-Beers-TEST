# Gebruik de officiële MySQL 8.0 image
FROM mysql:9.7.0

# Stel de database naam in (optioneel, maar handig)
ENV MYSQL_DATABASE=beers
ENV MYSQL_ROOT_PASSWORD=rootpwd

# Kopieer jouw lokale SQL-bestand naar de speciale initiatie-map in de container
# Vervang 'jouw_bestand.sql' door de echte naam van je bestand
COPY /beers.sql /docker-entrypoint-initdb.d/