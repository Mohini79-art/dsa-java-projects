
import java.util.*;
class Main {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        String name="Mohini";
        System.out.println("👋 Welcome Mohini!");
System.out.println("📘 Start java practice:\n");
   String[] questions = {
            "1. What is the capital of India?",
            "2. Who invented Java?",
            "3. Which data structure uses FIFO?",
            "4. What is the default value of int in Java?",
            "5. Which keyword is used to inherit a class?"
        };
    String[][] options = {
            {"A. Mumbai", "B. Delhi", "C. Kolkata", "D. Chennai"},
            {"A. James Gosling", "B. Dennis Ritchie", "C. Bjarne Stroustrup", "D. Guido van Rossum"},
            {"A. Stack", "B. Queue", "C. Tree", "D. Graph"},
            {"A. 0", "B. null", "C. undefined", "D. 1"},
            {"A. implement", "B. inherits", "C. extends", "D. interface"}
        };
        char ans[]={'B','A','B','A','C'};
        char user_answer;
        int score=0;
         for (int i = 0; i < questions.length; i++) {
            System.out.println("\n" + questions[i]);
            for (String option : options[i]) {
                System.out.println(option);
            }
            System.out.println("Enter your answer:");
           user_answer = sc.next().charAt(0);
            if(Character.toUpperCase( user_answer)==ans[i]){
                System.out.println("✅ your answer is correct :" + user_answer);
                score++;
            }
            else{
                System.out.println("❌ your answer is wrong :" + user_answer);
System.out.println("📌 right answer is :" + ans[i]);
            }
            
         }
         
         double percentage = (score * 100.0) / questions.length;
         System.out.println("🏁 Your total score is: " + score);
System.out.println("📊 Your total percentage is: " + percentage + "%");

    }
}
