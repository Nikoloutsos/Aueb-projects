create table Summary_Listings(
   id int,
   name varchar(100),
   host_id int,
   host_name varchar(40),
   neighbourhood_group varchar(20),
   neighbourhood varchar(80),
   latitude varchar(10),
   longitude varchar(10),
   room_type varchar(20),
   price int,
   minimum_nights int,
   number_of_reviews int,
   last_review varchar(10),
   reviews_per_month varchar(10),
   calculated_host_listings_count int,
   availability_365 int,
   constraint PK_Sum_L primary key (id, name)
);