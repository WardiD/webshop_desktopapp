#widok skrotu produktu
CREATE VIEW generalproductview AS
SELECT p.product_name, p.id_product, t.type_name, p.price, p.quantity_store FROM product p JOIN product_type t ON p.id_type = t.id_type;

#widok opisu urzadzen mobilnych
CREATE VIEW mobileproductview AS
SELECT p.id_product, p.product_name,d.brand,d.model,d.screen_size,d.CPU_core,d.CPU_speed,d.camera_front,d.camera_back,d.memory_internal,d.memory_external,d.fingerprint_reader,r.width, r.height, p.price FROM description_mobile d JOIN resolution r ON d.id_resolution=r.id_resolution JOIN product p ON p.id_product=d.id_product;


#widok opisu urzadzen typu komputer
CREATE VIEW computerproductview AS
SELECT p.id_product, p.product_name,d.brand,d.model,d.processor,d.CPU_core,d.CPU_speed,d.graphic_card,d.graphic_memory,d.ram,d.hdd,d.ssd,d.dvd,d.screen_size,r.width, r.height, p.price FROM description_computer d JOIN resolution r ON d.id_resolution=r.id_resolution JOIN product p ON p.id_product=d.id_product;


#widok cart
CREATE VIEW cartview AS
SELECT p.id_product,p.product_name,pl.quantity,p.price, c.id_cart, c.id_client FROM product_list pl JOIN cart c ON pl.id_cart=c.id_cart JOIN product p ON pl.id_product=p.id_product WHERE c.ordered = false;

#widok order
CREATE VIEW orderview AS
SELECT t.id_transaction,CONCAT(w.first_name,' ',w.last_name) AS worker_name,s.status_name,t.creation_date, t.realization_date, c.cart_price, c.id_cart, c.id_client, 
FROM transactions t LEFT JOIN cart c ON t.id_cart = c.id_cart LEFT JOIN worker w ON t.id_worker = w.id_worker LEFT JOIN status s on t.id_status = s.id_status WHERE c.ordered=true;


#widok logowania
CREATE VIEW loginview AS
SELECT c.email, cl.password FROM client cl JOIN contact c ON cl.id_client=c.id_client;

#widok koszyka
CREATE VIEW cartview AS
SELECT p.id_product,p.product_name,pl.quantity,p.price, c.id_cart, c.id_client FROM product_list pl JOIN cart c ON pl.id_cart=c.id_cart JOIN product p ON pl.id_product=p.id_product;
