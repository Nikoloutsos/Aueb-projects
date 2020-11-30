--creation of table Amenity
create table Amenity as (select distinct unnest(amenities::text[]) as amenity_name from Room);

--add auto-increment id column
alter table Amenity
add column amenity_id serial primary key;

--creating a table to relate tables Room and Amenity, depending on the listing_id and the amenity_id of the amenities they provide.
--We use the 'middle' select query to connect the listing_ids with each amenity_id.
create table Room_to_Amenity as 
   (SELECT middle.listing_id, amenity.amenity_id as amenity_id 
	from Amenity, (SELECT listing_id as listing_id, unnest(amenities::text[]) as amenity_name from Room ) AS middle
 	where middle.amenity_name = amenity.amenity_name);

--we use foreign keys to refer to the Room and Amenity tables
 alter table Room_to_Amenity
 add foreign key(listing_id) REFERENCES Room(listing_id),
 add foreign key(amenity_id) REFERENCES Amenity(amenity_id);

--drop the column amenities from Room table
alter table Room--
drop column amenities;--

/*We used the unnest function with the amenities field cast as a text array to pass every different amenity, both in the creation
of the amenity table and in the room_to_amenity one. We could have used replace and regex_split_to_table, for example in the 
amenity table, after creating an empty table:

create table Amenity (
	amenity_id serial primary key,
	amenity_name varchar(100)
);

--Then run these UPDATE statements to firstly remove the curly brackets:

update Room
	set amenities = replace(amenities, '{', '');
update Room
	set amenities = replace(amenities, '}', '');

--And insert the amenities distinctly in the amenity table with INSERT INTO SELECT:

insert into Amenity (amenity_name)
	select distinct(regex_split_to_table(amenities, ',')) from Room;
*/