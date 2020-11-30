create table Reviews(
   listing_id int,
   id int,
   date date,
   reviewer_id int,
   reviewer_name varchar(50),
   comments text,
   constraint PK_Reviews primary key (id, date)
);