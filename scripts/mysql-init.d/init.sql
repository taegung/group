DROP USER IF EXISTS 'projectlinker'@'localhost';
DROP USER IF EXISTS 'projectlinker'@'%';

CREATE
    USER 'projectlinker'@'localhost' IDENTIFIED BY 'projectlinker';
CREATE
    USER 'projectlinker'@'%' IDENTIFIED BY 'projectlinker';

GRANT ALL PRIVILEGES ON *.* TO
    'projectlinker'@'localhost';
GRANT ALL PRIVILEGES ON *.* TO
    'projectlinker'@'%';