INSERT INTO authority(authority_name) VALUES ('ROLE_USER');
INSERT INTO authority(authority_name) VALUES ('ROLE_ADMIN');
INSERT INTO authority(authority_name) VALUES ('ROLE_REST_TEST');
INSERT INTO authority(authority_name) VALUES ('ROLE_EMPLOYEE');


INSERT INTO spring_user (id, active, create_date, update_date, active_date, e_mail, full_name, password, username, create_by, update_by)
VALUES (1.00, true, current_date , null, null, 'system@yopmail.com', 'System', 'MTIzNDU2QkhXVEJXUUUoJiNSSEROS0pMU0gmRUhXREpZKkUoR0tKT1lGRFdeRkJTRFNQWUUoKSNSQkhLSkk=', 'system', null, null);

INSERT INTO spring_user (id, active, create_date, update_date, active_date, e_mail, full_name, password, username, create_by, update_by)
VALUES (2.00, true, current_date, null , null, 'lemon@yopmail.com', 'lemon', 'bGVtb25CSFdUQldRRSgmI1JIRE5LSkxTSCZFSFdESlkqRShHS0pPWUZEV15GQlNEU1BZRSgpI1JCSEtKSQ==', 'lemon', null, 1.00);

INSERT INTO spring_user (id, active, create_date, update_date, active_date, e_mail, full_name, password, username, create_by, update_by)
VALUES (3.00, true, current_date, null, null, 'admin@mail.com', 'Admin', 'YWRtaW5CSFdUQldRRSgmI1JIRE5LSkxTSCZFSFdESlkqRShHS0pPWUZEV15GQlNEU1BZRSgpI1JCSEtKSQ==', 'admin', null, 1.00);



INSERT INTO user_authorities (user_id, authority) VALUES (1.00, 'ROLE_ADMIN');
INSERT INTO user_authorities (user_id, authority) VALUES (2.00, 'ROLE_ADMIN');
INSERT INTO user_authorities (user_id, authority) VALUES (3.00, 'ROLE_ADMIN');

INSERT INTO setting (id,setting_key,setting_value) VALUES (1,'SHA-1-FINGERPRINT_CERTIFICATE','Bmce+9aHdOoVtE7fS3B07tfj7Bc=');