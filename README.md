MyService
=========

This is server application Account Service (service for test task). It works with TestClient app.

Service working with data from postgres database from heroku and cashing data in memory.
It can get amounts for some id from database or add some amounts for some id in DB. Feauteres:
- For adding: if id does not exist in DB - service creates in DB its id and value;
- For adding: if id already exist in DB - service adds value to existing value in DB;
- For getting: if id does not exist in DB - service sends 0 to client.

Also service collects statistics for every type of requests (get or add) per timeunit (1 min) and total statistic (for all time) and writes that statistics in logfile.
Connecting with DB configured in db_config.txt file. Port configured in port_config.txt.

Service commands:
"help" - show commands of service;
"stadd" - get statistic for getAmount() method;
"stget" - get statistic for addAmount() method;
"rstst" - reset statistic of service;
"stop" - stop service.

Have fun.
