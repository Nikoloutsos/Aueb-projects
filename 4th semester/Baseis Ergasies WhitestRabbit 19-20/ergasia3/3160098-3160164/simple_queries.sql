------------------------------------------------------------
--FIND LISTINGS THAT I CAN STAY FOR LONGER THAN 5 NIGHTS WITH MY GROUP OF 4+ PEOPLE
select Listings.id, Listings.maximum_nights, Listings.listing_url
from Listings
where  Listings.maximum_nights > 5 AND Listings.accommodates > 3
order by Listings.maximum_nights;

--6724 ROWS
------------------------------------------------------------
--FIND CALENDAR RECORDS FROM OLDEST TO NEWEST AND ACCORDINGLY TO HOW MANY BEDROOMS THEY HAVE THAT INCLUDE PRICE AND SQUARE FEET
select Calendar.*, Listings.bedrooms, Listings.square_feet , Listings.listing_url
from Calendar left outer join Listings 
on Listings.id = Calendar.listing_id
where not (Calendar.price is NULL) and not (Listings.square_feet is NULL) 
order by Calendar.date asc, Listings.bedrooms desc
limit 100;

--35406 ROWS IF LIMIT IS NOT USED, OTHERWISE 100
------------------------------------------------------------
--FIND LISTINGS THAT HAVE NO REVIEWS
select Listings.id from Listings	
except
select Listings.id from Listings inner join Reviews 
on Reviews.listing_id = Listings.id;

--2559 ROWS
------------------------------------------------------------
--FIND REVIEWERS WITH MOST RECORDED REVIEWS
select count(reviewer_name), reviewer_name
from Reviews inner join Listings
on Listings.id = Reviews.reviewer_id
group by reviewer_name
order by count(reviewer_name) desc;

--32 ROWS
------------------------------------------------------------
--FIND HOSTS THAT ARE ALSO REVIEWERS
select distinct reviews.reviewer_name 
from reviews inner join summary_listings 
on reviews.reviewer_name = summary_listings.host_name 
order by reviews.reviewer_name;

--1708 ROWS
------------------------------------------------------------
--FIND HOW MANY REVIEWS EACH
select summary_listings.name, count(*) from summary_listings
join summary_reviews on 
summary_reviews.listing_id = summary_listings.id
group by summary_listings.name
order by summary_listings.name;

--8889 ROWS
-----------------------------------------------------------
--FIND HOW MANY REVIEWS ARE RECORDED DAILY
select count(Summary_Reviews.listing_id), Summary_Reviews.date from Summary_Reviews
group by Summary_Reviews.date
order by Summary_Reviews.date desc;

--2985 ROWS
-----------------------------------------------------------
--FIND THE REVIEWS ON LISTINGS THAT HAVE FUTON BEDS
select count(Reviews.comments), Listings.id
from Reviews join Listings on Reviews.listing_id = Listings.id
where Listings.bed_type = 'Futon'
group by Listings.id;

--5 ROWS
-----------------------------------------------------------
--FIND HOW MANY LISTINGS ARE ON EACH NEIGHBOURHOOD
select neighbourhoods.neighbourhood, count(listings.id)
from neighbourhoods left join listings 
on neighbourhoods.neighbourhood = listings.neighbourhood_cleansed
group by neighbourhoods.neighbourhood
order by count(listings.id) desc;

--45 ROWS
-----------------------------------------------------------
--FIND THE COMMENTS, THE URL AND THE DAILY PRICE OF THE LISTINGS THAT HAVE GOOD REVIEWS AND THEIR WEEKLY PRICE IS 100 DOLLARS
select Reviews.comments, Listings.listing_url, Listings.price
from Reviews join Listings
on Reviews.listing_id = Listings.id
where Reviews.comments like '%great%' and Listings.weekly_price like '$100.00'
order by Listings.price;

--41 ROWS
-----------------------------------------------------------
-- FIND THE SUMMARISED DATA OF LISTINGS LOCATED IN NEIGHBOURHOODS THAT START WITH 'ΑΓΙΟΣ' 
select Summary_Listings.* 
from Summary_Listings left outer join Geolocation
on Geolocation.properties_neighbourhood = Summary_Listings.neighbourhood
where Geolocation.properties_neighbourhood like '%ΑΓΙΟΣ%';

--847 ROWS
-----------------------------------------------------------
-- FIND AVERAGE, MINIMUM AND MAXIMUM PRICE FOR LISTINGS THAT HAVE A NON ZERO PRICE
select round(avg(translate(Listings.price, ',$', '')::numeric),2), min(translate(Listings.price, ',$', '')::numeric), max(translate(Listings.price, ',$', '')::numeric)
FROM Listings,Summary_Listings
where Listings.id= Summary_Listings.id and translate(Listings.price, ',$', '')::numeric > 0 and not (Listings.price is null);

-- 1 ROW
----------------------------------------------------------- 