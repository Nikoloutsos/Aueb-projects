--Αριθμός ταινιών ανά χρόνο

select extract(year from release_date) as year, count(*) as number_of_movies
from movies_metadata
group by year
order by year;

--Αριθμός ταινιών ανά είδος(genre)

select genre ->>'name' "name", count(*)
from movies_metadata
cross join jsonb_array_elements(genres) genre
GROUP BY genre;

--Αριθμός ταινιών ανά είδος(genre) και ανά χρόνο

select extract(year from release_date) as year, genre ->>'name' "name" , count(genre)
from movies_metadata
cross join jsonb_array_elements(genres) genre
where extract(year from release_date) is not NULL
group by genre, year
order by year desc, count(distinct genre);

--Μέση βαθμολογία (rating) ανά είδος (ταινίας)

select genre ->> 'name' "name", round(avg(vote_average), 2) "average"
from movies_metadata
cross join jsonb_array_elements(genres) genre
group by genre
order by genre;


--Αριθμός από ratings ανά χρήστη

select distinct userid , count(rating)
from ratings
group by userid
order by userid;

--Μέση βαθμολογία (rating) ανά χρήστη

select distinct userid, round(avg(rating),2)
from ratings
group by userid
order by userid;

/*view_table: Σε πρωτη επισκοπηση, το ζητουμενο view μας δινει μια ματια στο βαθμο αυστηροτητας/επιεικειας των χρηστων. 
Αν κοιταξουμε τα δεδομενα απο το διαγραμμα θα παρατηρησουμε μερικα πραγματα: αρχικα,
υπαρχει μεγαλυτερη διασπορα στις βαθμολογιες στους χρηστες που εχουν λιγοτερες καταγεγραμμενες κριτικες.
Επισης, αυτοι οι χρηστες ειναι πιο πιθανο να εχουν ακραιες μεσες βαθμολογιες απο αυτους που εχουν πιο πολλες καταγεγραμενες κριτικες.
Συγκεκριμενα, οσο ο χρηστης καταχωρει περισσοτερες κριτικες, η μεση βαθμολογια του τεινει στο 3-3,5. Αντιθετως, αυτοι με τις λιγοτερες 
κριτικες εχουν μεση βαθμολογια κοντα στο 3,5-4.
view table*/

create view view_table as 
select userid, count(rating), round(avg(rating),2) as average
from ratings
group by userid
order by userid;