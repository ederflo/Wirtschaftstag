# Wirtschaftstag

## DB-Credentials
```
- MYSQL_ROOT_PASSWORD=password
- MYSQL_DATABASE=db
- MYSQL_USER=usr
- MYSQL_PASSWORD=password
```

## Buildscripts
Buildscripts sind vorhanden.

### Webservice
Beim Webserive muss zum Builden und dem darauffolgenden Ausführen **./build.sh** ausgeführt werden. Nach dem builden kann **./run.sh** ausgeführt werden, um die gebuildeten Docker-Container zu starten, ohne das Webservice neu zu builden.

### Android App
Um die Android App auszuführen, müssen zwei Terminals geöffnet werden. Als erstes muss **./runEmulator.sh** ausgeführt werden und darauffolgend im zweiten Terminal **./buildAndInstall.sh**, um die App am Emulator zu builden und anschließend zu installieren.

## HTML URL
Die HTML URL Seite ist unter **http://localhost:8080** erreichbar.