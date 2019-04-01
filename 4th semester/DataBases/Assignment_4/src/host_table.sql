CREATE TABLE Host
  AS (SELECT DISTINCT host_id as id, host_url as url, host_name as name, host_since AS since , host_location AS location,
	  host_about as about, host_response_time as time, host_response_rate as response_rate, host_acceptance_rate as acceptance_rate,
	  host_is_superhost as is_superhost,
	  host_thumbnail_url as thumbnail_url , host_picture_url as picture_url , host_neighbourhood as neighbourhood,
	  host_listings_count as listings_count , host_total_listings_count as total_listings_count,
	  host_verifications as verifications , host_has_profile_pic as has_profile_pic , host_identity_verified as identity_verified , 
	  calculated_host_listings_count
	 FROM listings);

  ALTER TABLE Host 
  ADD PRIMARY KEY (id);


  ALTER TABLE Listings 
  ADD FOREIGN  KEY (host_id) REFERENCES Host(id);

 

  ALTER TABLE Listings 
  DROP COLUMN host_url,
  DROP COLUMN host_name,
  DROP COLUMN host_since,
  DROP COLUMN host_location,
  DROP COLUMN host_about,
  DROP COLUMN host_response_time,
  DROP COLUMN host_response_rate,
  DROP COLUMN host_acceptance_rate,
  DROP COLUMN host_is_superhost,
  DROP COLUMN host_thumbnail_url,
  DROP COLUMN host_picture_url,
  DROP COLUMN host_neighbourhood,
  DROP COLUMN host_listings_count,
  DROP COLUMN host_total_listings_count,
  DROP COLUMN host_verifications,
  DROP COLUMN host_has_profile_picm
  DROP COLUMN host_identity_verified,
  DROP COLUMN calculated_host_listings_count;