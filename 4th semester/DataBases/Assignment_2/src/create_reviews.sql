create table Reviews(
   listing_id int,
   id int,
   date date,
   reviewer_id int,
   reviewer_name varchar(40),
   comments text,
   Primary Key(id)
);