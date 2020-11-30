--renaming all tables from plural to singular form:
ALTER TABLE Listings RENAME TO Listing;
ALTER TABLE Reviews RENAME TO Review;
ALTER TABLE Summary_Listings RENAME TO Summary_Listing;
ALTER TABLE Summary_Reviews RENAME TO Summary_Review;
ALTER TABLE Neighbourhoods RENAME TO Neighbourhood;

--kalo tha itan prin antigrafete tin ekfonisi tis ergasias, na grafete kai mia simeiosi apo kato
--stous foitites na kanoun drop sto teliko stadio ta columns pou antigrafikan apo ton pinaka listings,
--giati autoi pou douleuoun tin psql proti fora to na tous peis apla kane copy ena pinaka den arkei