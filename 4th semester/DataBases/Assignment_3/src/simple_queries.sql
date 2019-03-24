
--QUERIES WITHOUT JOINS
---------------------------------------------------------------------------------------------------------------------
/* shows listings by criteria the number of max nights must be more than 5 an the min nights must be 
between 1 and 3. The listings are ordered from the one with the most nights you can stay to the onw with the least*/
SELECT Listings.id, Listings.minimum_nights,  Listings.maximum_nights
FROM Listings 
WHERE  Listings.maximum_nights > 5 AND Listings.minimum_nights BETWEEN 1 AND 3
ORDER BY Listings.maximum_nights DESC;

--Rows: 8362
---------------------------------------------------------------------------------------------------------------------


---------------------------------------------------------------------------------------------------------------------
/* the purpose of this is to show top listings that have some really good reviews, people always consider going to  
a place with good revies rather than avergage, it counts how many "good", "great" reviews listings have(for a listing
to be show in  this it needs to have at least onev"good", "great" review) */
SELECT Count(Reviews.comments), Reviews.listing_id 
FROM Reviews
WHERE comments LIKE '%good%' OR comments LIKE '%super%'
Group by (Reviews.listing_id)
ORDER BY Count(Reviews.comments) Desc;

--Rows: 3933
----------------------------------------------------------------------------------------------------------------------


-------------------------------------------------------------------------------------------------------------------------
/* Show all building that their price is higher than mean price . 
This is a cartesian product between Listings table and a table we created at this time Statistic_table
So we understand that only 2911/ % is above average*/

Select Listings.id, Listings.listing_url, Listings.price::numeric as Actual_price, StatisticsTable.Average_money_you_need_to_rent_a_building
FROM Listings, (SELECT ROUND(AVG(Listings.price::numeric), 2) as Average_money_you_need_to_rent_a_building,
	MAX(Listings.price::numeric) AS Max_price_for_renting_a_building,
	MIN(Listings.price::numeric) AS Min_price_for_renting_a_building
	FROM Listings
	WHERE not (price is null)) AS StatisticsTable
WHERE Listings.price::numeric > StatisticsTable.Average_money_you_need_to_rent_a_building
ORDER BY Actual_price Desc;

--Rows: 2911 
-------------------------------------------------------------------------------------------------------------------------




--OUTER JOINS(LEFT, RIGHT, FULL)
 ---------------------------------------------------------------------------------------------------------------------
/* Checks whether there are any neigbourhoods that are not covered by any listings*/

SELECT COUNT(*) AS number_of_neighbourhoods_with_no_listings
FROM (
SELECT N.neighbourhood, L.id
FROM  Neighbourhoods N  LEFT JOIN  Listings L on N.neighbourhood = L.neighbourhood_cleansed) AS KA
WHERE KA.id = null ;

--Rows: 1
----------------------------------------------------------------------------------------------------------------------


----------------------------------------------------------------------------------------------------------------------
/*Combines Calendar and Listings tables and it sorts them with respect to date, bedrooms.
Needless to say, listings that have null values in price,square_feet are not taken into consideration
(This query could be easily found in case a user wants a building that meets the above conditions)*/
SELECT Calendar.*, Listings.bedrooms, Listings.square_feet , Listings.listing_url
FROM Calendar
LEFT OUTER JOIN Listings ON Listings.id = Calendar.listing_id
WHERE not (Calendar.price is NULL) AND not (Listings.square_feet is NULL) 
ORDER BY Calendar.date asc, Listings.bedrooms desc
LIMIT 100;

--Rows: 70931 if you remove the limit of 100 we put. We used the result of this query faster, cause if we hadnt it 
--takes a long time to show all 70931 rows
----------------------------------------------------------------------------------------------------------------------




/*INNNER JOINS*/
---------------------------------------------------------------------------------------------------------------------
/*Inner Join
  between tables: Listings,Reviews
  --> It presents  listings ordered by reviewer_name with their comments and reviewer name */
SELECT Listings.id, Listings.listing_url, Reviews.reviewer_id, Reviews.reviewer_name, Reviews.comments
FROM Listings, Reviews
WHERE Listings.id = Reviews.listing_id
ORDER BY Reviews.reviewer_name
LIMIT 100;

--Rows:  134550 if limit was not used
---------------------------------------------------------------------------------------------------------------------


---------------------------------------------------------------------------------------------------------------------
/*Returns a row with a number which specifies the number of listings_id that are not in table Summary_Listings.
We check if there are any listings that are not summarized in Summary_Listings */
SELECT Count(Listings.id)
FROM (
	SELECT Listings.id
	FROM Listings
EXCEPT
	SELECT Listings.id 
	FROM Listings
	INNER JOIN Summary_Listings ON Listings.id = Summary_Listings.id  ) AS Listings;

--Rows: 1
 ---------------------------------------------------------------------------------------------------------------------


 ---------------------------------------------------------------------------------------------------------------------
/*It shows for 2 specific listings the following:
	1) Reviews colums
	2) Total number of reviews for each listings (fetched from Listing table) */
SELECT Reviews.*, Listings.number_of_reviews AS Total_number_of_reviews_for_this_listing
FROM Listings
INNER JOIN Reviews ON Listings.id = Reviews.listing_id 
WHERE Listings.id = 1078 OR Listings.id = 10451710 ;

--Rows: 276
---------------------------------------------------------------------------------------------------------------------


-----------------------------------------------------------------------------------------------------------------------
/*It shows reviews data plus an image url for the listing */

SELECT Reviews.*, Listings.picture_url
FROM REVIEWS
INNER JOIN Listings ON Listings.id = Reviews.listing_id
LIMIT 100;

--Rows: 134550 if limit was not used
----------------------------------------------------------------------------------------------------------------------


-------------------------------------------------------------------------------------------------------------------------
/*Statistics with money
Run this and you will find Average_money_you_need_to_rent_a_building, Max_price_for_renting_a_building, 
Min_price_for_renting_a_building  */


SELECT ROUND(AVG(Listings.price::numeric), 2) as Average_money_you_need_to_rent_a_building,
MAX(Listings.price::numeric) AS Max_price_for_renting_a_building,
MIN(Listings.price::numeric) AS Min_price_for_renting_a_building
FROM Listings,summary_listings
where Listings.id= Summary_Listings.id and 
 not (Listings.price is null);

 --Rows: 1
-------------------------------------------------------------------------------------------------------------------------


-------------------------------------------------------------------------------------------------------------------------
/*For a certain range of days which buildings were available in a specified neighbourhood and their price was
 no more than 100$.
*/
SELECT Listings.id, Listings.listing_url, Listings.price
FROM Listings
INNER JOIN (SELECT DISTINCT Calendar.listing_id
	FROM Calendar
	WHERE Calendar.available = 't' AND Calendar.date between '2017-07-01' AND '2017-07-10') AS NewTable 
ON Listings.id = NewTable.listing_id
WHERE Listings.price::numeric <= 100 AND Listings.neighbourhood_cleansed = 78701;

--Rows: 24 
-------------------------------------------------------------------------------------------------------------------------


-------------------------------------------------------------------------------------------------------------------------
/*shows the people with most reviews in descending order. This may be important for those who are in search for a airBnb and will take 
in concideration only comments coming from people who have more reviews, more reviews usually mean people who 
have stayed in many airbnbs and can therefor compare listings with oneanother or people who are that interested
 in one specific listing and wrote may reviews about it or visited it many times*/
SELECT Count(Reviews.reviewer_id), Reviews.reviewer_id, Reviews.reviewer_name
From Listings ,Reviews
where Listings.id = Reviews.listing_id 
Group By Reviews.reviewer_id, Reviews.reviewer_name
Order by Count(Reviews.reviewer_id ) Desc;

--Rows: 117591 (there are this many reviewers)
-------------------------------------------------------------------------------------------------------------------------
