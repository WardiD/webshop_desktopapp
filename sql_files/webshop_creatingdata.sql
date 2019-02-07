INSERT INTO product_type (type_name) VALUES ('PC');
INSERT INTO product_type (type_name) VALUES ('Notebook');
INSERT INTO product_type (type_name) VALUES ('Smartphone');
INSERT INTO product_type (type_name) VALUES ('Tablet');


INSERT INTO status (id_status,status_name) VALUES (1,'order placed'), (2,'in realization'), (3, 'shipped');


INSERT INTO resolution (width, height) VALUES (1280,720);
INSERT INTO resolution (width, height) VALUES (1366,768);
INSERT INTO resolution (width, height) VALUES (1920,1080);
INSERT INTO resolution (width, height) VALUES (2560,1440);
INSERT INTO resolution (width, height) VALUES (1440,2960);
INSERT INTO resolution (width, height) VALUES (2560,1440);
INSERT INTO resolution (width, height) VALUES (1242,2688);

# ------------------------------------------------------------------------


INSERT INTO administration_panel (login, password) VALUES ('admin','admin'), ('dariusz','worker');
INSERT INTO worker (id_log,first_name,last_name,email,phone_number,superadmin) VALUES (1,'Super','Admin','admin@admin.pl',100000000,true),
    (2,'Dariusz','Wardega','dariusz@webshop.pl',123123123,false);


INSERT INTO address (street_address, apartment, zip_code, city, country) VALUES ('Kryspinow 282',NULL,32060,'Liszki','Poland');
INSERT INTO address (street_address, apartment, zip_code, city, country) VALUES ('Czarnowiejska 12',3,34560,'Krakow','Poland');
INSERT INTO address (street_address, apartment, zip_code, city, country) VALUES ('Os. Komandosow 2',121,34341,'Krakow','Poland');


INSERT INTO contact (phone_number,email) VALUES (600700800,'dariusz@poczta.pl');
INSERT INTO contact (phone_number,email) VALUES (NULL,'marcin@poczta.pl');
INSERT INTO contact (phone_number,email) VALUES (NULL,'didi@gmail.com');


INSERT INTO client (id_address, id_contact, first_name, last_name, password, money) VALUES (1,1,'Dariusz','Kopytko','qwerty',2500);
INSERT INTO client (id_address, id_contact, first_name, last_name, password, money) VALUES (2,2,'Marcin','Warzywko','asdfgh',0);
INSERT INTO client (id_address, id_contact, first_name, last_name, password, money) VALUES (3,3,'Dominika','Jelonek','zxcvbn',7000);


INSERT INTO product (price,quantity_store,product_name,id_type) VALUES (3000.00,10,'Samsung Note 9', 3);
INSERT INTO product (price,quantity_store,product_name,id_type) VALUES (4000.00,10,'iPhone XS Max', 3);
INSERT INTO product (price,quantity_store,product_name,id_type) VALUES (5699.00,5,'CHALLENGER H5570 PurePC Edition', 1);
INSERT INTO product (price,quantity_store,product_name,id_type) VALUES (3160.00,3,'Lenovo yoga 900s', 2);


INSERT INTO description_mobile (id_product, id_resolution, brand, model, screen_size, cpu_core, cpu_speed, camera_front, camera_back, memory_internal, memory_external, fingerprint_reader) 
VALUES (1,5,'Samsung','Galaxy Note 9',6.4,8,2.3,12,12,512,512,'true'),
(2, 7,'Apple','iPhone XS Max', 6.5,8,2.2,12,12,256,0,'true');


INSERT INTO description_computer (id_product, id_resolution, brand, model, processor, cpu_core, cpu_speed, graphic_card, graphic_memory, ram, hdd, ssd, dvd, screen_size)
VALUES (3, 3,'CHALLENGER','H5570','i5-8600K',6,3.6,'GeForce GTX 1070',8,16,2000,480,'true',NULL),
(4, 6,'Lenovo','yoga 900s','m5-6y54',2,1.1,'HD 515',2,8,0,256,'false',12.5);

