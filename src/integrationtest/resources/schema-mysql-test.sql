
CREATE TABLE IF NOT EXISTS store (
  store_id bigint(20) NOT NULL AUTO_INCREMENT,
  store_name varchar(255) DEFAULT NULL,
  store_postcode varchar(255) DEFAULT NULL,
  store_street_address varchar(255) DEFAULT NULL,
  store_suburb varchar(255) DEFAULT NULL,
  PRIMARY KEY (store_id)
);

INSERT IGNORE INTO  store
SET store_name= "S1",store_postcode= "1111",store_street_address="streetaddress1",store_suburb="suburb1";

INSERT IGNORE INTO  store
SET store_name= "S2",store_postcode= "2222",store_street_address="streetaddress2",store_suburb="suburb2";

INSERT IGNORE INTO  store
SET store_name= "S3",store_postcode= "3333",store_street_address="streetaddress3",store_suburb="suburb3";


INSERT IGNORE INTO  store
SET store_name= "S4",store_postcode= "4444",store_street_address="streetaddress4",store_suburb="suburb4";

INSERT IGNORE INTO  store
SET store_name= "S5",store_postcode= "5555",store_street_address="streetaddress5",store_suburb="suburb5";

INSERT IGNORE INTO  store
SET store_name= "S6",store_postcode= "6666",store_street_address="streetaddress6",store_suburb="suburb6";

