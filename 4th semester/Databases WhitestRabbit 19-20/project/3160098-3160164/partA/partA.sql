create table Movies_Metadata(
   adult boolean,
   belongs_to_collection varchar(190),
   budget int,
   genres varchar(270),
   homepage varchar(250),
   id int,--in the links table it is called tmbdId
   imdb_id varchar(10),
   original_language varchar(10),
   original_title varchar(110),
   overview varchar(1000),
   popularity numeric,
   poster_path varchar(40),
   production_companies varchar(1260),
   production_countries varchar(1040),
   release_date date,
   revenue bigint,
   runtime numeric,
   spoken_languages varchar(770),
   status varchar(20),
   tagline varchar(300),
   title varchar(110),
   video boolean,
   vote_average numeric,
   vote_count int
);

create table Ratings(
   userId int,
   movieId int, -- this references the exact movieId in the links table
   rating numeric,
   timestamp int
);

create table Credits(
   full_cast text,
   crew text,
   id int -- in the links table it is called tmbdId
);

create table Keywords(
   id int,--in the links table it is called tmbdId
   keywords text
);

create table Links(
   movieId int,
   imdbId int,
   tmdbId int --in all other tables it is simply id
);

/*
Table alterations, duplicate removals, removal of movies not in Movies_Metadata. 
These are essential to create primary keys and foreign key references, since PSQL demands fields with unique records for keys.
We used two methods for duplicate removals, an intermediate table and the use of partition.
To delete the movie records that were not in Movies_Metadata, we used nested select and join.
*/

--alteration of the imdb_id column to align with the integer type used for movie Imdb links in the rest of the tables.
update movies_metadata set imdb_id = replace(imdb_id, 'tt', '');
alter table movies_metadata alter column imdb_id type int using imdb_id::int;


--alteration of the genres column so that they can be used for the select queries of part B.
UPDATE 
   movies_metadata
SET 
   genres = REPLACE(genres,E"'", '"'); -- we ran the command genres = REPLACE(genres,E''', '"') we just modified in this file to keep the syntax of the code intact.

alter table movies_metadata
alter column genres type jsonb USING genres::jsonb;

--a series of statements we used to isolate the records with distinct ids on Movies_Metadata so we could remove all of its duplicates.
create table temp as (select distinct on (id) id,adult,belongs_to_collection,budget,genres,homepage,imdb_id,original_language,original_title,overview,popularity,poster_path,production_companies,production_countries,release_date,revenue,runtime,spoken_languages,status,tagline,title,video,vote_average,vote_count from Movies_Metadata);
drop table Movies_Metadata;
alter table temp rename to Movies_Metadata;

--the statements for duplicate removals for tables Credits, Keywords and Links
delete from Credits where crew in (select crew from (select crew, ROW_NUMBER() over (partition by id order by crew) as row_num from Credits) t where t.row_num > 1);

delete from Keywords where keywords in (select keywords from (select keywords, ROW_NUMBER() over (partition by id order by keywords) as row_num from Keywords) t where t.row_num > 1);

delete from Links where movieId in (select movieId from (select movieId, ROW_NUMBER() over (partition by tmdbId order by movieId) as row_num from Links) t where t.row_num > 1);

--the statements for removal of movies that are not in table Movies_Metadata.
delete from Links where tmdbId in (select tmdbId from Links where tmdbId not in (select id from Movies_Metadata));

delete from Links where imdbId not in (select imdb_id from Movies_Metadata join Links on imdb_id = imdbId);

delete from Ratings where movieId in (select movieId from Ratings where movieId not in (select movieId from Links));

--Declaration of primary and foreign keys.

ALTER TABLE Movies_Metadata ADD PRIMARY KEY (id);

ALTER TABLE Credits ADD PRIMARY KEY (id);
ALTER TABLE Credits ADD CONSTRAINT fkc1 FOREIGN KEY (id) REFERENCES Movies_Metadata (id);

ALTER TABLE Keywords ADD PRIMARY KEY (id);
ALTER TABLE Keywords ADD CONSTRAINT fkk1 FOREIGN KEY (id) REFERENCES Movies_Metadata (id);

ALTER TABLE Links ADD PRIMARY KEY (movieId);
ALTER TABLE Links ADD CONSTRAINT fkl1 FOREIGN KEY (tmdbId) REFERENCES Movies_Metadata (id);
ALTER TABLE Links ADD CONSTRAINT fkl2 FOREIGN KEY (imdbId) REFERENCES Movies_Metadata (imdb_id);

ALTER TABLE Ratings ADD CONSTRAINT fkr1 FOREIGN KEY (movieId) REFERENCES Links (movieId);