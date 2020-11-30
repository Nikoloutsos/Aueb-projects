create table Ratings(
   userId int,
   movieId int, -- this references the exact movieId in the links table
   rating numeric,--could be float
   timestamp int
);