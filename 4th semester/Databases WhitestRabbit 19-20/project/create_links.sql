create table Links(
   movieId int,
   imdbId int,
   tmdbId int --in all other tables it is simply id
);

delete from Links where movieId in (select movieId from (select movieId, ROW_NUMBER() over (partition by tmdbId order by movieId) as row_num from Links) t where t.row_num > 1);
delete from Links where tmdbId in (select tmdbId from Links where tmdbId not in (select id from Movies_Metadata));
delete from Links where imdbId not in (select imdb_id from Movies_Metadata join Links on imdb_id = imdbId);

ALTER TABLE Links ADD PRIMARY KEY (movieId);

ALTER TABLE Links 
ADD CONSTRAINT fkl1 FOREIGN KEY (tmdbId) REFERENCES Movies_Metadata (id);

ALTER TABLE Links
ADD CONSTRAINT fkl2 FOREIGN KEY (imdbId) REFERENCES Movies_Metadata (imdb_id);