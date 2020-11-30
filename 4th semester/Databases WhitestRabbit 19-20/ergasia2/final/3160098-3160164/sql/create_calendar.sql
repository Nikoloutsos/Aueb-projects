create table Calendar(
   listing_id int,
   date date,
   available boolean,
   price varchar(10),
   adjusted_price varchar(10),
   minimum_nights int,
   maximum_nights int,
   CONSTRAINT PK_Calendar PRIMARY KEY (listing_id, date)
);