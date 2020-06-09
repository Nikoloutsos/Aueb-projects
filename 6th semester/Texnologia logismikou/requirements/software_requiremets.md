# <center> **StudyTracker** </center>

<p>
Η διαδικασία εύρεσης/δήλωσης των απαιτήσεων αποτελεί σημαντικό κομμάτι ώστε να μπορεί να συνεχιστεί το υπόλοιπο project και να βγεί το επιθυμητό αποτέλεσμα. <br>
Η εφαρμογή μας έχει τις παρακάτω απαιτήσεις τις οποίες θα φέρουμε εις πέρας.

* Κρυπτογραφημένη αποθήκευση στοιχείωv χρήστη κατά την εγγραφή και έλεγχος στοιχείων του κατά την είσοδο
* Σαφής παρουσίαση των μαθημάτων στους χρήστες
* Το σύστημα θα πρέπει να υπολογίσει μέσο όρο μαθημάτων και συνολικό αριθμό ECTS για κάθε εξάμηνο χωριστά και συνολικά
* Αποστολή ειδοποιήσεων την ώρα που έχει ωρίσει ο χρήστης
* Έγκαιρη αποστολή ειδποιήσεων για σημαντικά συμβάντα της σχολής
* Ανταπόκριση στα clicks του χρήστη στην εφαρμογή(διαχείρηση μαθημάτων/βαθμών/γεγονότων)
* Ανταπόκριση του server σε λιγότερο απο 1sec
* Ευχάριστo, απλό και κατανοητό UI 
</p>

<p>
Από τις άνω απαιτήσεις μπορούμε να βγάλουμε της περιπτώσεις χρήσης, δηλαδή την λειτουργικότητα που παρέχει το σύστημα.<br>
Οι χρήστες της εφαρογής θα έχουν στην διάθεση τους ένα εύρος λειτουργιών που θα μπορέσουν να επιτελέσουν.
</p>

## Διάγραμμα περιπτώσεων χρήσης

![use case diagram][logo]

[logo]: diagrams/use_case_diagram.png "use case diagram"

## Περιπτώσεις χρήσης
* <b>ΠΧ Εγγραφή φοιτητή:</b> η διαδικασία μέσω της οποίας ο φοιτητής μπένει για πρώτη φορά στην εφραμογή.
* <b>ΠΧ Τατυποίηση χρήστη:</b> ο φοιτητής ή ένα μέλος της ομάδας μας κάνει είσοδο στην εφαρομγή.
* <b>ΠΧ Διαχείρηση μαθημάτων:</b> ο φοιτητής θα μπορεί να διαχειρίζεται μαθήματα τα οποία βρίσκονται στον οδηγό σπουδών και τον ενδιαφέρουν.
* <b>ΠΧ Δήλωση μαθημάτων εξαμήνου:</b> ο φοιτητής θα μπορεί να φτιάξει την δήλωση για το τρέχων εξάμηνο και στην πορεία να προσθέσει τους βαθμούς του.
* <b>ΠΧ Επισκόπηση προόδου: </b> ο φοιτητής μπορεί να δεί την πρόοδου του, αναλυτική βαθμολογία, μέσο όρο, ECTS και άλλα σχετικά στατιστικά.
* <b>ΠΧ Υπενθυμίσεις μαθημάτων:</b> ο φοιτητής διαχειρίζεται υπενθυμίσεις που θέλει να λάβει για τα μαθήματα του. 
* <b>ΠΧ Προσθήκη μαζικής ειδοποίησης:</b> μέλος της ομάδας studyTracker προσθέτει μια ενημέρωησ πολύ σημαντική για την σχολή.