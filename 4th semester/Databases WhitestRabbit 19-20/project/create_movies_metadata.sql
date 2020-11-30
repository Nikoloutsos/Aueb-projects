create table Movies_Metadata(
   adult boolean,--could be boolean
   belongs_to_collection varchar(190),
   budget int,
   genres varchar(270),
   homepage varchar(250),
   id int,--in the links table it is called tmbdId
   imdb_id varchar(10),--if tt is removed, it can be turned to int-> links table(imdb_id)
   original_language varchar(10),
   original_title varchar(110),
   overview varchar(1000),
   popularity numeric,--could be float
   poster_path varchar(40),
   production_companies varchar(1260),
   production_countries varchar(1040),
   release_date date,
   revenue bigint,
   runtime numeric,--could be float
   spoken_languages varchar(770),
   status varchar(20),
   tagline varchar(300),
   title varchar(110),
   video boolean,--could be boolean
   vote_average numeric,--could be float
   vote_count int
);

update movies_metadata set imdb_id = replace(imdb_id, 'tt', '');
alter table movies_metadata alter column imdb_id type int using imdb_id::int;


create table temp as (select distinct on (id) id,adult,belongs_to_collection,budget,genres,homepage,imdb_id,original_language,original_title,overview,popularity,poster_path,production_companies,production_countries,release_date,revenue,runtime,spoken_languages,status,tagline,title,video,vote_average,vote_count from Movies_Metadata);
drop table Movies_Metadata;
alter table temp rename to Movies_Metadata;

ALTER TABLE Movies_Metadata ADD PRIMARY KEY (id);