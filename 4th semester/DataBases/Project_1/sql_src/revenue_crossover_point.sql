/*ΤΖΕΝΗ ΜΠΟΛΕΝΑ 3170117
ΚΩΝΣΤΑΝΤΙΝΟΣ ΝΙΚΟΛΟΥΤΣΟΣ 3170122*/
/*ROWS FOR EACH CITY
PORTLAND total rows: 338
AUSTIN total rows: 627
DENVER total rows: 416
BOSTON total rows: 884*/




--------------------------------------------------------------------------------------------
--CREATE VIEW FOR CITY PORTLAND
--total rows: 338
CREATE view portland_v_revenue_crossover AS
(
    
            SELECT
                airbnbdata.*,
                zmrp.price AS zillow_monthly_price,
                zmrp.city,
                zmrp.price / airbnbdata.median_per_day AS revenue_crossover_point 
            FROM
                (
                    SELECT
                        a.zipcode,
                        a.DATE,
                        a.bedrooms,
                        PERCENTILE_CONT(0.5) WITHIN GROUP ( 
                    ORDER BY
                        a.price ) AS median_per_day 
                    FROM
                        (
                            SELECT
                                filted_listing.neighbourhood_cleansed AS zipcode,
                                date_trunc('month', calendar.DATE) AS DATE,
                                filted_listing.bedrooms,
                                CASE
                                    WHEN
                                        calendar.price IS NULL 
                                    THEN
                                        filted_listing.price 
                                    ELSE
                                        calendar.price 
                                END
                            FROM
                                (
                                    SELECT
                                        * 
                                    FROM
                                        listing 
                                    WHERE
                                        room_type = 'Entire home/apt' 
                                        AND bedrooms > 0 
                                        AND bedrooms IS NOT NULL 
                                        AND last_scraped IS NOT NULL 
                                        AND neighbourhood_cleansed IS NOT NULL 
                                        AND UPPER(city) = 'PORTLAND'
                                )
                                AS filted_listing,
                                calendar 
                            WHERE
                                filted_listing.id = calendar.listing_id
                        )
                        AS a 
                    GROUP BY
                        zipcode,
                        DATE,
                        bedrooms
                )
                AS airbnbdata,
                zillow_median_rental_price AS zmrp 
            WHERE
                airbnbdata.zipcode = zmrp.zipcode 
                AND airbnbdata.DATE = zmrp.DATE 
                AND airbnbdata.bedrooms = zmrp.bedrooms
        
)













-----------------------------------------------------------------------------------------------
--CREATE VIEW FOR CITY AUSTIN
--total rows: 627
CREATE view austin_v_revenue_crossover AS
(
    
            SELECT
                airbnbdata.*,
                zmrp.price AS zillow_monthly_price,
                zmrp.city,
                zmrp.price / airbnbdata.median_per_day AS revenue_crossover_point 
            FROM
                (
                    SELECT
                        a.zipcode,
                        a.DATE,
                        a.bedrooms,
                        PERCENTILE_CONT(0.5) WITHIN GROUP ( 
                    ORDER BY
                        a.price ) AS median_per_day 
                    FROM
                        (
                            SELECT
                                filted_listing.neighbourhood_cleansed AS zipcode,
                                date_trunc('month', calendar.DATE) AS DATE,
                                filted_listing.bedrooms,
                                CASE
                                    WHEN
                                        calendar.price IS NULL 
                                    THEN
                                        filted_listing.price 
                                    ELSE
                                        calendar.price 
                                END
                            FROM
                                (
                                    SELECT
                                        * 
                                    FROM
                                        listing 
                                    WHERE
                                        room_type = 'Entire home/apt' 
                                        AND bedrooms > 0 
                                        AND bedrooms IS NOT NULL 
                                        AND last_scraped IS NOT NULL 
                                        AND neighbourhood_cleansed IS NOT NULL 
                                        AND UPPER(city) = 'AUSTIN'
                                )
                                AS filted_listing,
                                calendar 
                            WHERE
                                filted_listing.id = calendar.listing_id
                        )
                        AS a 
                    GROUP BY
                        zipcode,
                        DATE,
                        bedrooms
                )
                AS airbnbdata,
                zillow_median_rental_price AS zmrp 
            WHERE
                airbnbdata.zipcode = zmrp.zipcode 
                AND airbnbdata.DATE = zmrp.DATE 
                AND airbnbdata.bedrooms = zmrp.bedrooms 
)










-----------------------------------------------------------------------------------------------
--CREATE VIEW FOR CITY DENVER
--total rows: 416
CREATE view denver_v_revenue_crossover AS
(
 
            SELECT
                airbnbdata.*,
                zmrp.price AS zillow_monthly_price,
                zmrp.city,
                zmrp.price / airbnbdata.median_per_day AS revenue_crossover_point 
            FROM
                (
                    SELECT
                        a.zipcode,
                        a.DATE,
                        a.bedrooms,
                        PERCENTILE_CONT(0.5) WITHIN GROUP ( 
                    ORDER BY
                        a.price ) AS median_per_day 
                    FROM
                        (
                            SELECT
                                filted_listing.neighbourhood_cleansed AS zipcode,
                                date_trunc('month', calendar.DATE) AS DATE,
                                filted_listing.bedrooms,
                                CASE
                                    WHEN
                                        calendar.price IS NULL 
                                    THEN
                                        filted_listing.price 
                                    ELSE
                                        calendar.price 
                                END
                            FROM
                                (
                                    SELECT
                                        * 
                                    FROM
                                        listing 
                                    WHERE
                                        room_type = 'Entire home/apt' 
                                        AND bedrooms > 0 
                                        AND bedrooms IS NOT NULL 
                                        AND last_scraped IS NOT NULL 
                                        AND neighbourhood_cleansed IS NOT NULL 
                                        AND UPPER(city) = 'DENVER'
                                )
                                AS filted_listing,
                                calendar 
                            WHERE
                                filted_listing.id = calendar.listing_id
                        )
                        AS a 
                    GROUP BY
                        zipcode,
                        DATE,
                        bedrooms
                )
                AS airbnbdata,
                zillow_median_rental_price AS zmrp 
            WHERE
                airbnbdata.zipcode = zmrp.zipcode 
                AND airbnbdata.DATE = zmrp.DATE 
                AND airbnbdata.bedrooms = zmrp.bedrooms 
)








-----------------------------------------------------------------------------------------------
--CREATE VIEW FOR CITY BOSTON
--total rows: 884
CREATE view boston_v_revenue_crossover AS
(
     SELECT
                airbnbdata.*,
                zmrp.price AS zillow_monthly_price,
                zmrp.city,
                zmrp.price / airbnbdata.median_per_day AS revenue_crossover_point 
            FROM
                (
                    SELECT
                        a.zipcode,
                        a.DATE,
                        a.bedrooms,
                        PERCENTILE_CONT(0.5) WITHIN GROUP ( 
                    ORDER BY
                        a.price ) AS median_per_day 
                    FROM
                        (
                            SELECT
                                filted_listing.neighbourhood_cleansed AS zipcode,
                                date_trunc('month', calendar.DATE) AS DATE,
                                filted_listing.bedrooms,
                                CASE
                                    WHEN
                                        calendar.price IS NULL 
                                    THEN
                                        filted_listing.price 
                                    ELSE
                                        calendar.price 
                                END
                            FROM
                                (
                                    SELECT
                                        * 
                                    FROM
                                        listing 
                                    WHERE
                                        room_type = 'Entire home/apt' 
                                        AND bedrooms > 0 
                                        AND bedrooms IS NOT NULL 
                                        AND last_scraped IS NOT NULL 
                                        AND neighbourhood_cleansed IS NOT NULL 
                                        AND UPPER(city) = 'BOSTON'
                                )
                                AS filted_listing,
                                calendar 
                            WHERE
                                filted_listing.id = calendar.listing_id
                        )
                        AS a 
                    GROUP BY
                        zipcode,
                        DATE,
                        bedrooms
                )
                AS airbnbdata,
                zillow_median_rental_price AS zmrp 
            WHERE
                airbnbdata.zipcode = zmrp.zipcode 
                AND airbnbdata.DATE = zmrp.DATE 
                AND airbnbdata.bedrooms = zmrp.bedrooms
)
