# cdc

## Grant permission

```shell
GRANT ALL PRIVILEGES ON *.* TO 'identity'@'172.18.0.1' IDENTIFIED BY 'password' with grant option;
GRANT SELECT, RELOAD, SHOW DATABASES, REPLICATION SLAVE, REPLICATION CLIENT  ON *.* TO 'identity'@'172.18.0.1';
```


## [Show logbin mysql status](https://www.sqlshack.com/learn-mysql-an-overview-of-mysql-binary-logs)
```shell
 show global variables like ‘log_bin’;
 
```

## Build docker 

```shell
docker build -t ndhai/mysql:5.7 .
docker tag ndhai/mysql:5.7 ndhai/mysql:latest
```