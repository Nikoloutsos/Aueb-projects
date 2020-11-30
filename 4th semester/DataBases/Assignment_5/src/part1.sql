--ΜΕΡΟΣ Α
--1 convert response_rate to numeric type
UPDATE  Host
SET 
response_rate = REPLACE(response_rate,'%','');

UPDATE  Host
SET 
response_rate = REPLACE(response_rate,'N/A','NaN');

alter table Host
alter Column response_rate type numeric(10,2) using response_rate::numeric; 

UPDATE  Host
SET 
response_rate = response_rate/100.0;
---------------------------------------------------------------------------------------------------------------------------------------

--2 replacing column location from table host with columns city, state, country and then delete location

alter table host
add column city text,
add column state text,
add column country text;

UPDATE host SET city = split_part(location, ',', 1);
UPDATE host SET state = split_part(location, ',', 2);
UPDATE host SET country = split_part(location, ',', 3);


alter table host 
drop column location;
---------------------------------------------------------------------------------------------------------------------------------------

--3 creating table Amenity and the oneother table to have the desired conection with table Listing
CREATE TABLE Amenity AS
(SELECT distinct unnest(amenities::text[]) as amenity_name from listing );
 
ALTER  TABLE Amenity
ADD COLUMN id SERIAL PRIMARY KEY;

CREATE TABLE Listing_Amenity_Connection AS
(SELECT temp.listing_id , amenity.id as amenity_id from amenity,
   (SELECT listing.id as listing_id, unnest(amenities::text[]) as amenity_name from listing ) AS temp
  where temp.amenity_name = amenity.amenity_name);

ALTER TABLE listing_amenity_connection
ADD PRIMARY KEY(listing_id, amenity_id);

 ALTER TABLE Listing_Amenity_Connection
 add foreign key(listing_id) REFERENCES Listing(id),
 add foreign key(amenity_id) REFERENCES Amenity(id);


 ALTER TABLE Listing
 drop column amenities;