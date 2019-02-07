
CREATE SEQUENCE public.resolution_id_resolution_seq_2;

CREATE TABLE public.Resolution (
                id_resolution INTEGER NOT NULL DEFAULT nextval('public.resolution_id_resolution_seq_2'),
                width INTEGER NOT NULL,
                height INTEGER NOT NULL,
                CONSTRAINT resolution_pk PRIMARY KEY (id_resolution)
);


ALTER SEQUENCE public.resolution_id_resolution_seq_2 OWNED BY public.Resolution.id_resolution;

CREATE SEQUENCE public.status_id_status_seq_1;

CREATE TABLE public.Status (
                id_status INTEGER NOT NULL DEFAULT nextval('public.status_id_status_seq_1'),
                status_name VARCHAR NOT NULL,
                CONSTRAINT status_pk PRIMARY KEY (id_status)
);


ALTER SEQUENCE public.status_id_status_seq_1 OWNED BY public.Status.id_status;

CREATE SEQUENCE public.administration_panel_id_log_seq_1;

CREATE TABLE public.Administration_panel (
                id_log INTEGER NOT NULL DEFAULT nextval('public.administration_panel_id_log_seq_1'),
                login VARCHAR NOT NULL,
                password VARCHAR NOT NULL,
                CONSTRAINT administration_panel_pk PRIMARY KEY (id_log)
);


ALTER SEQUENCE public.administration_panel_id_log_seq_1 OWNED BY public.Administration_panel.id_log;

CREATE SEQUENCE public.worker_id_worker_seq_1;

CREATE TABLE public.Worker (
                id_worker INTEGER NOT NULL DEFAULT nextval('public.worker_id_worker_seq_1'),
                id_log INTEGER NOT NULL,
                first_name VARCHAR NOT NULL,
                last_name VARCHAR NOT NULL,
                email VARCHAR NOT NULL,
                phone_number INTEGER NOT NULL,
                superAdmin BOOLEAN NOT NULL DEFAULT FALSE,
                CONSTRAINT worker_pk PRIMARY KEY (id_worker)
);


ALTER SEQUENCE public.worker_id_worker_seq_1 OWNED BY public.Worker.id_worker;

CREATE SEQUENCE public.product_type_id_type_seq_1;

CREATE TABLE public.Product_type (
                id_type INTEGER NOT NULL DEFAULT nextval('public.product_type_id_type_seq_1'),
                type_name VARCHAR NOT NULL,
                CONSTRAINT product_type_pk PRIMARY KEY (id_type)
);


ALTER SEQUENCE public.product_type_id_type_seq_1 OWNED BY public.Product_type.id_type;

CREATE SEQUENCE public.product_id_product_seq_1;

CREATE TABLE public.Product (
                id_product INTEGER NOT NULL DEFAULT nextval('public.product_id_product_seq_1'),
                price REAL NOT NULL,
                quantity_store INTEGER NOT NULL,
                product_name VARCHAR NOT NULL,
                id_type INTEGER NOT NULL,
                CONSTRAINT product_pk PRIMARY KEY (id_product)
);


ALTER SEQUENCE public.product_id_product_seq_1 OWNED BY public.Product.id_product;

CREATE TABLE public.Description_mobile (
                id_product INTEGER NOT NULL,
                id_resolution INTEGER NOT NULL,
                brand VARCHAR NOT NULL,
                model VARCHAR NOT NULL,
                Screen_size DOUBLE PRECISION NOT NULL,
                CPU_core INTEGER NOT NULL,
                CPU_speed REAL NOT NULL,
                camera_front INTEGER NOT NULL,
                camera_back INTEGER NOT NULL,
                memory_internal INTEGER NOT NULL,
                memory_external INTEGER NOT NULL,
                fingerprint_reader BOOLEAN NOT NULL,
                CONSTRAINT description_mobile_pk PRIMARY KEY (id_product)
);


CREATE TABLE public.Description_computer (
                id_product INTEGER NOT NULL,
                id_resolution INTEGER NOT NULL,
                brand VARCHAR NOT NULL,
                model VARCHAR NOT NULL,
                processor VARCHAR NOT NULL,
                CPU_core INTEGER NOT NULL,
                CPU_speed REAL NOT NULL,
                graphic_card VARCHAR NOT NULL,
                graphic_memory DOUBLE PRECISION NOT NULL,
                RAM INTEGER NOT NULL,
                HDD INTEGER NOT NULL,
                SSD INTEGER NOT NULL,
                DVD BOOLEAN NOT NULL,
                screen_size DOUBLE PRECISION,
                CONSTRAINT description_computer_pk PRIMARY KEY (id_product)
);


CREATE SEQUENCE public.address_id_address_seq_1;

CREATE TABLE public.Address (
                id_address INTEGER NOT NULL DEFAULT nextval('public.address_id_address_seq_1'),
                street_address VARCHAR NOT NULL,
                apartment VARCHAR,
                zip_code INTEGER NOT NULL,
                city VARCHAR NOT NULL,
                country VARCHAR NOT NULL,
                CONSTRAINT address_pk PRIMARY KEY (id_address)
);


ALTER SEQUENCE public.address_id_address_seq_1 OWNED BY public.Address.id_address;

CREATE SEQUENCE public.contact_id_contact_seq;

CREATE TABLE public.Contact (
                id_contact INTEGER NOT NULL DEFAULT nextval('public.contact_id_contact_seq'),
                phone_number INTEGER,
                email VARCHAR NOT NULL,
                CONSTRAINT contact_pk PRIMARY KEY (id_contact)
);


ALTER SEQUENCE public.contact_id_contact_seq OWNED BY public.Contact.id_contact;

CREATE SEQUENCE public.client_id_client_seq;

CREATE TABLE public.Client (
                id_client INTEGER NOT NULL DEFAULT nextval('public.client_id_client_seq'),
                id_address INTEGER NOT NULL,
                id_contact INTEGER NOT NULL,
                first_name VARCHAR NOT NULL,
                last_name VARCHAR NOT NULL,
                password VARCHAR NOT NULL,
                money DOUBLE PRECISION NOT NULL,
                CONSTRAINT client_pk PRIMARY KEY (id_client)
);


ALTER SEQUENCE public.client_id_client_seq OWNED BY public.Client.id_client;

CREATE SEQUENCE public.cart_id_cart_seq;

CREATE TABLE public.Cart (
                id_cart INTEGER NOT NULL DEFAULT nextval('public.cart_id_cart_seq'),
                id_client INTEGER NOT NULL,
                cart_price REAL DEFAULT 0,
		ordered BOOLEAN NOT NULL DEFAULT FALSE,
                CONSTRAINT cart_pk PRIMARY KEY (id_cart)
);


ALTER SEQUENCE public.cart_id_cart_seq OWNED BY public.Cart.id_cart;

CREATE SEQUENCE public.transactions_id_transaction_seq;

CREATE TABLE public.Transactions (
                id_transaction INTEGER NOT NULL DEFAULT nextval('public.transactions_id_transaction_seq'),
                id_worker INTEGER DEFAULT NULL,
                id_client INTEGER NOT NULL,
                id_status INTEGER NOT NULL DEFAULT 1,
                id_cart INTEGER NOT NULL,
                creation_date DATE,
                realization_date DATE,
                CONSTRAINT transactions_pk PRIMARY KEY (id_transaction)
);


ALTER SEQUENCE public.transactions_id_transaction_seq OWNED BY public.Transactions.id_transaction;

CREATE SEQUENCE public.product_list_id_list_seq;

CREATE TABLE public.Product_list (
                id_list INTEGER NOT NULL DEFAULT nextval('public.product_list_id_list_seq'),
                id_cart INTEGER NOT NULL,
                id_product INTEGER NOT NULL,
                quantity INTEGER DEFAULT 1 NOT NULL,
                CONSTRAINT product_list_pk PRIMARY KEY (id_list)
);


ALTER SEQUENCE public.product_list_id_list_seq OWNED BY public.Product_list.id_list;

ALTER TABLE public.Description_computer ADD CONSTRAINT resolution_description_computer_fk
FOREIGN KEY (id_resolution)
REFERENCES public.Resolution (id_resolution)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.Description_mobile ADD CONSTRAINT resolution_description_mobile_fk
FOREIGN KEY (id_resolution)
REFERENCES public.Resolution (id_resolution)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.Transactions ADD CONSTRAINT status_transaction_fk
FOREIGN KEY (id_status)
REFERENCES public.Status (id_status)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.Worker ADD CONSTRAINT administration_panel_worker_fk
FOREIGN KEY (id_log)
REFERENCES public.Administration_panel (id_log)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.Transactions ADD CONSTRAINT worker_transaction_fk
FOREIGN KEY (id_worker)
REFERENCES public.Worker (id_worker)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.Product ADD CONSTRAINT product_type_product_fk
FOREIGN KEY (id_type)
REFERENCES public.Product_type (id_type)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.Product_list ADD CONSTRAINT product_product_list_fk
FOREIGN KEY (id_product)
REFERENCES public.Product (id_product)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.Description_computer ADD CONSTRAINT product_description_notebook_fk
FOREIGN KEY (id_product)
REFERENCES public.Product (id_product)
ON DELETE NO ACTION
ON UPDATE NO ACTION
DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE public.Description_mobile ADD CONSTRAINT product_description_mobile_fk
FOREIGN KEY (id_product)
REFERENCES public.Product (id_product)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.Client ADD CONSTRAINT address_client_fk
FOREIGN KEY (id_address)
REFERENCES public.Address (id_address)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.Client ADD CONSTRAINT contact_client_fk
FOREIGN KEY (id_contact)
REFERENCES public.Contact (id_contact)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.Cart ADD CONSTRAINT client_cart_fk
FOREIGN KEY (id_client)
REFERENCES public.Client (id_client)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.Transactions ADD CONSTRAINT client_transaction_fk
FOREIGN KEY (id_client)
REFERENCES public.Client (id_client)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.Product_list ADD CONSTRAINT cart_product_list_fk
FOREIGN KEY (id_cart)
REFERENCES public.Cart (id_cart)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.Transactions ADD CONSTRAINT cart_transaction_fk
FOREIGN KEY (id_cart)
REFERENCES public.Cart (id_cart)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;
