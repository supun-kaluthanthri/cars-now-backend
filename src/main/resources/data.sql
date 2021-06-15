INSERT INTO oauth_client_details (client_id, client_secret, web_server_redirect_uri, scope, access_token_validity, refresh_token_validity, resource_ids, authorized_grant_types, additional_information) VALUES ('mobile', '{bcrypt}$2a$10$gPhlXZfms0EpNHX0.HHptOhoFD1AoxSr/yUIdTqA8vtjeP4zi0DDu', 'http://localhost:8080/login', 'READ,WRITE', '3600', '10000', 'rent', 'authorization_code,password,refresh_token,implicit', '{}');

INSERT INTO role (NAME) VALUES('ROLE_firm_owner');
INSERT INTO role (NAME) VALUES('ROLE_car_owner');
INSERT INTO role (NAME) VALUES('ROLE_renter');

INSERT ALL
INTO PERMISSION (NAME) VALUES('PERMISSION_CREATE_FEEDBACK')
INTO PERMISSION (NAME) VALUES('PERMISSION_UPDATE_FEEDBACK')
INTO PERMISSION (NAME) VALUES('PERMISSION_DELETE_FEEDBACK')
INTO PERMISSION (NAME) VALUES('PERMISSION_READ_FEEDBACK')
INTO PERMISSION (NAME) VALUES('PERMISSION_CREATE_FIRM_OWNER')
INTO PERMISSION (NAME) VALUES('PERMISSION_UPDATE_FIRM_OWNER')
INTO PERMISSION (NAME) VALUES('PERMISSION_DELETE_FIRM_OWNER')
INTO PERMISSION (NAME) VALUES('PERMISSION_READ_FIRM_OWNER')
INTO PERMISSION (NAME) VALUES('PERMISSION_CREATE_CAR_OWNER')
INTO PERMISSION (NAME) VALUES('PERMISSION_UPDATE_CAR_OWNER')
INTO PERMISSION (NAME) VALUES('PERMISSION_DELETE_CAR_OWNER')
INTO PERMISSION (NAME) VALUES('PERMISSION_READ_CAR_OWNER')
INTO PERMISSION (NAME) VALUES('PERMISSION_CREATE_CAR')
INTO PERMISSION (NAME) VALUES('PERMISSION_UPDATE_CAR')
INTO PERMISSION (NAME) VALUES('PERMISSION_DELETE_CAR')
INTO PERMISSION (NAME) VALUES('PERMISSION_READ_CAR')
INTO PERMISSION (NAME) VALUES('PERMISSION_CREATE_RENTER')
INTO PERMISSION (NAME) VALUES('PERMISSION_UPDATE_RENTER')
INTO PERMISSION (NAME) VALUES('PERMISSION_DELETE_RENTER')
INTO PERMISSION (NAME) VALUES('PERMISSION_READ_RENTER')
INTO PERMISSION (NAME) VALUES('PERMISSION_CREATE_BOOKING')
INTO PERMISSION (NAME) VALUES('PERMISSION_UPDATE_BOOKING')
INTO PERMISSION (NAME) VALUES('PERMISSION_DELETE_BOOKING')
INTO PERMISSION (NAME) VALUES('PERMISSION_READ_BOOKING')
SELECT * FROM dual;

INSERT ALL
INTO PERMISSION_ROLE (PERMISSION_ID, ROLE_ID) VALUES(1,3)
INTO PERMISSION_ROLE (PERMISSION_ID, ROLE_ID) VALUES(2,3)
INTO PERMISSION_ROLE (PERMISSION_ID, ROLE_ID) VALUES(3,3)
INTO PERMISSION_ROLE (PERMISSION_ID, ROLE_ID) VALUES(4,3)
INTO PERMISSION_ROLE (PERMISSION_ID, ROLE_ID) VALUES(4,1)
INTO PERMISSION_ROLE (PERMISSION_ID, ROLE_ID) VALUES(4,2)
INTO PERMISSION_ROLE (PERMISSION_ID, ROLE_ID) VALUES(5,1)
INTO PERMISSION_ROLE (PERMISSION_ID, ROLE_ID) VALUES(6,1)
INTO PERMISSION_ROLE (PERMISSION_ID, ROLE_ID) VALUES(7,1)
INTO PERMISSION_ROLE (PERMISSION_ID, ROLE_ID) VALUES(8,1)
INTO PERMISSION_ROLE (PERMISSION_ID, ROLE_ID) VALUES(9,1)
INTO PERMISSION_ROLE (PERMISSION_ID, ROLE_ID) VALUES(10,1)
INTO PERMISSION_ROLE (PERMISSION_ID, ROLE_ID) VALUES(10,2)
INTO PERMISSION_ROLE (PERMISSION_ID, ROLE_ID) VALUES(11,1)
INTO PERMISSION_ROLE (PERMISSION_ID, ROLE_ID) VALUES(12,1)
INTO PERMISSION_ROLE (PERMISSION_ID, ROLE_ID) VALUES(12,2)
INTO PERMISSION_ROLE (PERMISSION_ID, ROLE_ID) VALUES(12,3)
INTO PERMISSION_ROLE (PERMISSION_ID, ROLE_ID) VALUES(13,1)
INTO PERMISSION_ROLE (PERMISSION_ID, ROLE_ID) VALUES(14,1)
INTO PERMISSION_ROLE (PERMISSION_ID, ROLE_ID) VALUES(15,1)
INTO PERMISSION_ROLE (PERMISSION_ID, ROLE_ID) VALUES(16,1)
INTO PERMISSION_ROLE (PERMISSION_ID, ROLE_ID) VALUES(16,2)
INTO PERMISSION_ROLE (PERMISSION_ID, ROLE_ID) VALUES(16,3)
INTO PERMISSION_ROLE (PERMISSION_ID, ROLE_ID) VALUES(17,1)
INTO PERMISSION_ROLE (PERMISSION_ID, ROLE_ID) VALUES(17,3)
INTO PERMISSION_ROLE (PERMISSION_ID, ROLE_ID) VALUES(18,1)
INTO PERMISSION_ROLE (PERMISSION_ID, ROLE_ID) VALUES(18,3)
INTO PERMISSION_ROLE (PERMISSION_ID, ROLE_ID) VALUES(19,1)
INTO PERMISSION_ROLE (PERMISSION_ID, ROLE_ID) VALUES(19,3)
INTO PERMISSION_ROLE (PERMISSION_ID, ROLE_ID) VALUES(20,1)
INTO PERMISSION_ROLE (PERMISSION_ID, ROLE_ID) VALUES(20,2)
INTO PERMISSION_ROLE (PERMISSION_ID, ROLE_ID) VALUES(20,3)
INTO PERMISSION_ROLE (PERMISSION_ID, ROLE_ID) VALUES(21,1)
INTO PERMISSION_ROLE (PERMISSION_ID, ROLE_ID) VALUES(21,3)
INTO PERMISSION_ROLE (PERMISSION_ID, ROLE_ID) VALUES(22,1)
INTO PERMISSION_ROLE (PERMISSION_ID, ROLE_ID) VALUES(23,1)
INTO PERMISSION_ROLE (PERMISSION_ID, ROLE_ID) VALUES(23,3)
INTO PERMISSION_ROLE (PERMISSION_ID, ROLE_ID) VALUES(24,1)
INTO PERMISSION_ROLE (PERMISSION_ID, ROLE_ID) VALUES(24,2)
INTO PERMISSION_ROLE (PERMISSION_ID, ROLE_ID) VALUES(24,3)
INTO PERMISSION_ROLE (PERMISSION_ID, ROLE_ID) VALUES(13,2)
SELECT * FROM dual;
