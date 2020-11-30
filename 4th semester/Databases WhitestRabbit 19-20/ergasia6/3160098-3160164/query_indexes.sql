--QUERY 1:
create index ind1 on Room(listing_id)
	where Room.accommodates > 4;

create index ind1b on Room_to_Amenity(listing_id);

drop index ind1;
drop index ind1b;

--QUERY 2:
create index ind2 on Amenity(amenity_id);
create index ind2b on Room_to_Amenity(amenity_id);

drop index ind2;
drop index ind2b;
--QUERY 3:
create index ind3 on Room(listing_id, beds, bedrooms);
create index ind3b on Listing(id,host_id);

drop index ind3;
drop index ind3b;

--QUERY 4:
create index ind4 on Location(neighbourhood_cleansed);
create index ind4b on Listing(id,host_id);

drop index ind4;
drop index ind4b;

--QUERY 5:
create index ind5 on Calendar(listing_id,price)
	where price is not null;

drop index ind5;