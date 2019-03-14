create table Calendar(
   listing_id int,
   date date,
   available boolean,
   price money,
   PRIMARY KEY (listing_id, date)
);