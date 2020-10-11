set time_zone = '+2:00';
use exhibitionsservlet;


SELECT * FROM exhibition;
SELECT * FROM exhibition_has_hall;
SELECT * FROM exhibition_status;
SELECT * FROM hall;
SELECT * FROM ticket;
SELECT * FROM ticket_status;
SELECT * FROM user_role;
SELECT * FROM usr;

SELECT * FROM ticket LEFT JOIN usr ON ticket.usr_id = usr.id LEFT JOIN exhibition ON ticket.exhibition_id = exhibition.id LEFT JOIN exhibition_has_hall ON exhibition.id = exhibition_has_hall.exhibition_id;
SELECT * FROM ticket LEFT JOIN usr ON ticket.usr_id = usr.id LEFT JOIN exhibition ON ticket.exhibition_id = exhibition.id LEFT JOIN exhibition_has_hall ON exhibition.id = exhibition_has_hall.exhibition_id WHERE ticket.id=1;
SELECT * FROM ticket LEFT JOIN usr ON ticket.usr_id = usr.id LEFT JOIN exhibition ON ticket.exhibition_id = exhibition.id LEFT JOIN exhibition_has_hall ON exhibition.id = exhibition_has_hall.exhibition_id WHERE ticket.exhibition_id=1;
SELECT * FROM ticket LEFT JOIN usr ON ticket.usr_id = usr.id LEFT JOIN exhibition ON ticket.exhibition_id = exhibition.id LEFT JOIN exhibition_has_hall ON exhibition.id = exhibition_has_hall.exhibition_id WHERE ticket.usr_id=1;
SELECT * FROM ticket LEFT JOIN usr ON ticket.usr_id = usr.id LEFT JOIN exhibition ON ticket.exhibition_id = exhibition.id LEFT JOIN exhibition_has_hall ON exhibition.id = exhibition_has_hall.exhibition_id WHERE ticket.ticket_status_id=1;

SELECT * FROM exhibition LEFT JOIN exhibition_has_hall ON exhibition.id = exhibition_has_hall.exhibition_id WHERE ex_name LIKE 'das';
SELECT * FROM exhibition LEFT JOIN exhibition_has_hall ON exhibition.id = exhibition_has_hall.exhibition_id WHERE themes LIKE 'das';
SELECT * FROM exhibition LEFT JOIN exhibition_has_hall ON exhibition.id = exhibition_has_hall.exhibition_id WHERE exhibition_status_id = 1;
SELECT * FROM exhibition LEFT JOIN exhibition_has_hall ON exhibition.id = exhibition_has_hall.exhibition_id WHERE start_date BETWEEN 'sds' AND 'sde' OR end_date BETWEEN 'sds' AND 'sde' OR start_date <='sds' AND end_date >='sde';

SELECT * FROM usr;
SELECT * FROM usr WHERE id=?;
SELECT * FROM usr WHERE username=?;
SELECT * FROM usr WHERE email=?;
SELECT * FROM usr WHERE email=? AND password=?;
SELECT * FROM usr WHERE user_role_id=?;
UPDATE usr SET user_role_id=? WHERE id=?;

UPDATE ticket SET ticket_status_id=? WHERE ticket.id=?;
DELETE FROM ticket WHERE id=?;

INSERT INTO ticket(usr_id, exhibition_id, ticket_status_id) VALUES (?, ?, ?);

INSERT INTO user_role(role) VALUES ('ADMIN'), ('USER'), ('GUEST');
INSERT INTO ticket_status(ticket_status_name) VALUES ('ACTIVE'), ('EXPIRED'), ('WAITING_REFUND'), ('REFUNDED');
INSERT INTO exhibition_status(status_name) VALUES ('ACTIVE'), ('EXPIRED'), ('CANCELED');
INSERT INTO hall(hall_name) VALUES ('SHEVCHENKO'), ('FRANKO'), ('KOSTENKO');
INSERT INTO usr(id, email, password, username, user_role_id) VALUES(1, 'superadmin@exhibitions.servlet', '1234', 'SuperAdmin', 1);

SELECT * FROM exhibition LEFT JOIN exhibition_has_hall ON exhibition.id=exhibition_has_hall.exhibition_id 
WHERE exhibition_has_hall.hall_id = ? 
AND (
exhibition.start_date <= (SELECT start_date FROM exhibition WHERE exhibition.id = ?) AND exhibition.end_date >= (SELECT end_date FROM exhibition WHERE exhibition.id = ?)
OR
exhibition.start_date BETWEEN (SELECT start_date FROM exhibition WHERE exhibition.id = ?) AND (SELECT end_date FROM exhibition WHERE exhibition.id = ?)
OR
exhibition.end_date BETWEEN (SELECT start_date FROM exhibition WHERE exhibition.id = ?) AND (SELECT end_date FROM exhibition WHERE exhibition.id = ?));

DELETE FROM exhibition WHERE id=1;
UPDATE exhibition SET  ex_name=?, start_date=?, end_date=?, open_time=?, close_time=?, description=?, price=?, themes=?, exhibition_status_id=?;
UPDATE usr SET email=?, password=?, username=?, user_role_id=? WHERE id=?;
SELECT * FROM usr WHERE id=?;
INSERT INTO usr(email, password, username, user_role_id) VALUES (?, ?, ?, ?);

DELIMITER $$
CREATE TRIGGER exhibition_status_update
BEFORE UPDATE
ON exhibition FOR EACH ROW
BEGIN
UPDATE exhibition SET exhibition_status_id=2 WHERE (end_date<CURDATE() AND exhibition_status_id=1);
END;$$
DELIMITER ;