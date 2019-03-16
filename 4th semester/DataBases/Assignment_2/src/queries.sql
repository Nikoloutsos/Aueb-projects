
/*Inner Join 
  between tables: Listings,Reviews
  --> It presents 100 listings ordered by reviewer_name with their comments and reviewer name */
SELECT Listings.id, Reviews.reviewer_id, Reviews.reviewer_name, Reviews.comments
FROM Listings, Reviews
WHERE Listings.id = Reviews.listing_id
ORDER BY Reviews.reviewer_name
LIMIT 100;


SELECT Listings.id, Listings.minimum_nights,  Listings.maximum_nights
FROM Listings 
WHERE  Listings.maximum_nights > 5 AND Listings.minimum_nights BETWEEN 1 AND 3
ORDER BY Listings.maximum_nights DESC
LIMIT 100;


