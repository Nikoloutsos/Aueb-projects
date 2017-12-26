import random
class Question_Game:
    quest = {
1:["Ποιά είναι η μεγαλύτερη χώρα;",["Ελλάδα","Κίνα","Βουλγαρία","Ισπανία"],["Κίνα"],["Βουλγαρία","Κίνα"]],
2:["Ποια νότα είναι μετά την λα;",["ντο","φα","σι","μι"],["σι"],["ντο","σι"]],
3:["Ποιο σύμβολο ονομάζεται παραγοντικό;",["+","!","<","S"],["!"],["!","S"]],
4:["Ποιος καλλιτέχνης έχει ερμηνεύσει περισσότερα τραγούδια;",["Μαρτάκης","Ρέμος","Ασλανίδου","Παντελίδης"],["Ρέμος"],["Παντελίδης","Ρέμος"]],
5:["Ποιος ποιητής έγραψε τον Εθνικό Ύμνο¨",["Σεφέρης","Καζαντζάκης","Σολωμός","Παλαμάς"],["Σολωμός"],["Παλαμάς","Σολωμός"]],
6:["Πότε έγινε η άλωση της Κων/λης;",["1450","1451","1483","1453"],["1453"],["1453","1483"]],
7:["Πότε μπήκε η Ελλάδα στην Ε.Ε;",["1990","2001","2002","2000"],["2001"],["2001","2000"]],
8:["Ποιος είναι ο μεγαλύτερος ποταμός της Γης;",["Τίγρης","Αμαζόνιος","Ευφράτης","Δούναβης"],["Αμαζόνιος"],["Δούναβης","Αμαζόνιος"]],
9:["Ποιο είναι το αποτέλεσμα της παράστασης Α = 2/4*3 σε έναν υπολογιστή;",["0","3/2","6","1/6"],["3/2"],["1/6","3/2"]],
10:["Τι σημαίνει η λέξη famous;",["Χοντός","Ψηλός","Όμορφος","Γνωστός"],["Γνωστός"],["Όμορφος","Γνωστός"]],
11:["Ποιο είναι το συνώνυμο της λέξης αδαής;",["Έξυπνος","Άσχετος","Απρεπής","Άγνωστος"],["Άσχετος"],["Απρεπής","'Ασχετος"]],
12:["Πώς συμβολίζεται η νότα φα;",["G","F","E","Fm"],["F"],["F","Fm"]],
13:["Ποια ομάδα έχει πιο σταθερά στοιχεία;",["Αλκάλια","Αλκαλικές Γαίες","Αμέταλλα","Ευγενή Αέρια"],["Ευγενή Αέρια"],["Αμέταλλα","Ευγενή Αέρια"]],
14:["Ποιο είναι το αντίθετο του μπροστά;",["Δίπλα","Πίσω","Απέναντι","Κάτω"],["Πίσω"],["Πίσω","Κάτω"]],
15:["Πότε έγιναν οι Ολυμπιακοί αγώνες στην Ελλάδα;",["1990","2000","2004","2010"],["2004"],["2000","2004"]]
    }
    players_self = []
 
    def __init__(self, name): #Initialize the object's attributes.
        self.name = name
        self.skip = 1
        self.fifty = 1
        self.points = 0
        self.questions_already_shown = []
        self.questions_to_display=10
 
    def ask_question(self):
        print("Player ",self.name,"is playing! \n ------------------------------")
        while(self.questions_to_display > 0): # It will run 10 times.
            print("QUESTION NUMBER ",11-self.questions_to_display)
            random_question = random.randint(1,15)
            while (random_question in self.questions_already_shown):
                random_question = random.randint(1,15)
            self.questions_already_shown.append(random_question)
            self.questions_to_display -= 1
            self.show_question(random_question)
            self.give_solution(random_question)
            print("--------------------------------------") #Seperates the questions. //ADD POINTS IF HELPS HAVE NOT BEEN USED.
        self.points += self.skip*5 + self.fifty*5
        print("********************************************") #Seperates the player's questions.
 
    def show_question(self,random_question):
        print(Question_Game.quest[random_question][0], "\n")  # It prints the question
        print("A. ", Question_Game.quest[random_question][1][0])
        print("B. ", Question_Game.quest[random_question][1][1])
        print("C. ", Question_Game.quest[random_question][1][2])
        print("D. ", Question_Game.quest[random_question][1][3])
        self.print_helps()
    def give_solution(self,random_question):
        ans = input("Η απάντηση σου ειναι --> ").upper()
        while ans not in ["A", "B", "C", "D", "F", "S"]:
            print('Κατι πηγε στραβα.. \n Οι διαθεσιμες επιλογες σας ειναι "A","B","C","D"')
            ans = input("Η απάντηση σου ειναι --> ").upper()
        self.actions_of_users_input(random_question,ans)
    def actions_of_users_input(self,random_question,ans):
        if ans == "A":
            if Question_Game.quest[random_question][2][0] == Question_Game.quest[random_question][1][0]:
                self.points += 10
                print("Σωσταα!")
            else:
                print("Λαθος απάντηση")
        elif ans == "B":
            if Question_Game.quest[random_question][2][0] == Question_Game.quest[random_question][1][1]:
                self.points += 10
                print("Σωσταα!")
            else:
                print("Λαθος απάντηση")
        elif ans == "C":
            if Question_Game.quest[random_question][2][0] == Question_Game.quest[random_question][1][2]:
                self.points += 10
                print("Σωσταα!")
            else:
                print("Λαθος απάντηση")
        elif ans == "D":
            if Question_Game.quest[random_question][2][0] == Question_Game.quest[random_question][1][3]:
                self.points += 10
                print("Σωσταα!")
            else:
                print("Λαθος απάντηση")
        elif ans == "F":
            if self.fifty > 0:
                self.fifty -= 1
                print("Επιλέξατε 50-50")
                self.show_question_50_and_give_ans(random_question)

            else:
                print("*Εξαντλήσατε αυτην την βοήθεια.*")
                self.show_question(random_question)
                self.give_solution(random_question)
        elif ans == "S":
            if self.skip > 0:
                self.skip -= 1
                print("Επιλέξατε πάσο.")
                self.questions_to_display += 1
            else:
                print("*Εξαντλήθηκαν ολα τα πασο.*")
                self.show_question(random_question)
                self.give_solution(random_question)
 
    def print_helps(self):
        if self.fifty>0:
            print("||Βοηθεια 50-50 πατα F||")
        if self.skip>0:
            print("||Πάσο πάτα S||")
    @staticmethod
    def better_player():
        for i in range(0,len(Question_Game.players_self)-1): #BUBBLE SORT
            for j in range(len(Question_Game.players_self)-1,i,-1):
                if(Question_Game.players_self[j].points >= Question_Game.players_self[j-1].points):
                    temp = Question_Game.players_self[j]
                    Question_Game.players_self[j] = Question_Game.players_self[j - 1]
                    Question_Game.players_self[j - 1] = temp
        print("RESULTS:")
        for i in Question_Game.players_self:
            print(i.name,"With: ",i.points, "points")

    def show_question_50_and_give_ans(self,random_question):
        l = ["A. ","B. ","C. ","D. "]
        question_list = Question_Game.quest[random_question][1]
        index_of_question = question_list.index(Question_Game.quest[random_question][2][0])
        index_of_random = random.randint(0,3)
        while (index_of_random==index_of_question):
            index_of_random = random.randint(0, 3)
        print("SELECT FROM THIS TWO")
        if(index_of_question<index_of_random):
            print(l[index_of_question],Question_Game.quest[random_question][2][0])
            print(l[index_of_random],Question_Game.quest[random_question][1][index_of_random])
        else:
            print(l[index_of_random], Question_Game.quest[random_question][1][index_of_random])
            print(l[index_of_question], Question_Game.quest[random_question][2][0])
        ans = input("Your answer is: ").upper()
        while(ans!=l[index_of_random][0] and ans!=l[index_of_question][0]):
            if(index_of_question>index_of_random):
                print("Something went wrong! \n Please choose either",l[index_of_random][0],"or ",l[index_of_question][0])
            else:
                print("Something went wrong! \n Please choose either", l[index_of_question][0], "or ", l[index_of_random][0] )
            ans = input("Your answer is: ").upper()
        self.actions_of_users_input(random_question,ans)


 
 
# Create 3 players and store them in players_self list.
for i in range(1, 4):
    player1 = Question_Game(input("Enter name for Player" + str(i)+"-->"))
    Question_Game.players_self.append(player1)
for i in Question_Game.players_self:
    i.ask_question()
Question_Game.better_player()
