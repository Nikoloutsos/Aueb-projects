/*it counts how many amenities each listing has and orders them from the one with the most to the one with the list.
The more amenities a listing has, most likely it is to be booked*/
SELECT count(amenity_id), listing_id  FROM listing_amenity_connection
GROUP BY listing_id
ORDER BY count(amenity_id) DESC;
---------------------------------------------------------------------------------------------------------------------------------------------

/*inner join columns from tables listing_amenity_connection and  amenity
for each listing show its amenity id plus the amenity, order by listing_id
Rows: 171995*/
select listing_amenity_connection.listing_id, listing_amenity_connection.amenity_id, amenity.amenity_name
from listing_amenity_connection, amenity
where listing_amenity_connection.amenity_id = amenity.id
ORDER BY(listing_amenity_connection.listing_id);
---------------------------------------------------------------------------------------------------------------------------------------------

/*inner join. for every listing show its host id and its location which we now devided in three columns, city, state, country. Order by host id 
so that listings that belong to the same person are shown one after the other
Rows: 9663*/
select listing.id, host.id, host.city, host.state, host.country 
from listing, host
where listing.host_id = host.id
order by(host.id);
---------------------------------------------------------------------------------------------------------------------------------------------

/*inner join tables listing and host on host id. Shows all the listings that their weekly price is between 500 and 1000 dollars
and there are 4 to 6 beds. Orders them from chepest to most expensive.
This info will be useful for families on a budget*/
select listing.id as listing_id, host.id as host_id, listing.listing_url, listing.weekly_price, listing.beds from listing
inner join host on host.id = listing.host_id
group by listing.id, host.id , listing.listing_url
having listing.weekly_price >=500 and listing.weekly_price <=1000 and listing.beds>=4 and listing.beds<=6
order by listing.weekly_price desc;
---------------------------------------------------------------------------------------------------------------------------------------------

/*this querie has statistics for all neighbourhoods. Basicly it shows the minimum, average and maximum price to book
any listing in a neighbourhood. But the  statistics come only from listings  that have  property type house, the location
score is the best possible and there are olny good beds, not couch type beds. Another requirement is that maxprice is more than
 2000 dollars. This settings could be used by someone who doesn't mind spending lots of many to find the perfect place for holidays
 and wants to choose a nice neighbourhood*/

select neighbourhood_cleansed,  neighborhood, country, max(weekly_price) as max_wekkly_price, min(weekly_price) as  min_wekkly_price 
from listing
where property_type = 'House' and review_scores_location = '10' and bed_type = 'Real Bed' and  neighborhood is not null
group by neighbourhood_cleansed, country, neighborhood
having max( weekly_price) >2000
order by max(weekly_price)desc ;
---------------------------------------------------------------------------------------------------------------------------------------------

/*inner join tables listing and temp on listing id. Sow all the listings with their average price and in addition to that 
show picture url and house rules. order by the one with the lower average price. The average price here
is taken from calendar and if there are listings that have price as null do not take them in consideration
Rows: 7717*/
select listing.id, temp.avg_price, listing.picture_url, listing.house_rules from listing,
(select listing_id,ROUND(avg(price),2) as avg_price  from calendar
	 where price is not null
group by listing_id) as temp
where listing.id = temp.listing_id  order by temp.avg_price;


