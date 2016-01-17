## Installationsanleitung

### Voraussetzungen:
* Docker
* Maven
* Git

### Git Repository clonen
```
git clone https://github.com/thi-enterprisejava/manga-benesch.git
```

### Postgres Datenbank starten
```
docker run --name postgres -e POSTGRES_PASSWORD=mysecretpassword -d postgres
```

### Datenbank anlegen
Auf die Datenbank verbinden (So in die Konsole kopieren)
```
docker run -it --link postgres:postgres --rm postgres sh -c 'exec psql -h "$POSTGRES_PORT_5432_TCP_ADDR" -p "$POSTGRES_PORT_5432_TCP_PORT" -U postgres'
```
Passwort wie oben: mysecretpassword

Datenbank Manga anlegen
```
create database manga;
```
Mit \q verlassen

### IP Adresse der Datenbank holen
```
docker inspect -f '{{ .NetworkSettings.IPAddress }}' postgres
```
Die IP Adresse unter *src/main/docker/commands.cli* bei *--connection-url* anpassen

### Das Projekt bauen und als Docker Container hinzufügen
```
mvn package -DskipTests=true docker:build
```

### Server im interaktiven Modus starten
```
docker run -p 8080:8080 -ti --link postgres:postgres enterprisejava2015/mangas:1.0
```

### Die Startseite ist erreichbar unter:
*localhost:8080/index.xhtml*