------------------------------------------------------------
-- find how many amenities each listing that accommodates more than 4 people has
select Room_to_Amenity.listing_id, count(Room_to_Amenity.listing_id) from Room_to_Amenity
join Room on Room_to_Amenity.listing_id = Room.listing_id
where Room.accommodates > 4
group by Room_to_Amenity.listing_id;

--3058 rows
------------------------------------------------------------
--find how many times each amenity appears in the listed rooms
select Amenity.amenity_name, count(Room_to_Amenity.amenity_id) from Room_to_Amenity
join Amenity on Room_to_Amenity.amenity_id = Amenity.amenity_id
group by Amenity.amenity_name
order by count(Room_to_Amenity.amenity_id) desc;

--197 rows
------------------------------------------------------------
--find the requested columns of listings that have at least two bedrooms and 4 beds
select Room.listing_id, Room.beds, Room.bedrooms, Listing.listing_url from Room
join Listing on Listing.id = Room.listing_id
join Host on Host.id = Listing.host_id
group by Room.listing_id, Room.beds, Room.bedrooms, Listing.listing_url
having Room.beds>=4 and Room.bedrooms>=2
order by Room.beds asc;

--1375 rows
------------------------------------------------------------
-- find the number of listings in each neighbourhood, whose owners own more than 2 listings
select Location.neighbourhood_cleansed,count(Listing.id) from Location 
join Listing on Location.listing_id = Listing.id
join Host on Host.id = Listing.host_id
where Host.listings_count>2
group by Location.neighbourhood_cleansed;

--44 rows
------------------------------------------------------------
--show me all the listings ordered by their average prices as shown on the calendar table
select Room.listing_id, temp.avg_price from Room  join (select listing_id,ROUND(avg(price),2) as avg_price from Calendar
	where price is not null
	group by listing_id) as temp
on Room.listing_id = temp.listing_id
order by temp.avg_price;

--11541 rows
------------------------------------------------------------