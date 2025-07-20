import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuizApp {
    public static void main(String[] args) {
        
        List<Question> questions = loadQuestions("Questions.txt");

        if (questions.isEmpty()) {
            System.out.println("No questions found! Make sure Question.txt is in the correct location.");
            return;
        }

        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("=== WELCOME TO THE QUIZ ===");
            System.out.println("Total Questions: " + questions.size());
            System.out.println("================================\n");
            
            int score = 0;
            int questionNumber = 1;

            for (Question q : questions) {
                q.display(questionNumber);

                char userAnswer = ' ';
                while (true) {
                    System.out.print("Enter your answer (A/B/C/D): ");
                    String input = sc.next().trim();

                    if (!input.isEmpty()) {
                        userAnswer = Character.toUpperCase(input.charAt(0));
                        if (userAnswer >= 'A' && userAnswer <= 'D') {
                            break;
                        } else {
                            System.out.println("Invalid input. Please enter A, B, C, or D.");
                        }
                    } else {
                        System.out.println("Input cannot be empty. Please enter your answer.");
                    }
                }

                if (q.isCorrect(userAnswer)) {
                    System.out.println("Correct!\n");
                    score++;
                } else {
                    System.out.println("Wrong. The correct answer was: " + q.getAnswer() + "\n");
                }

                questionNumber++;
            }

            System.out.println("================================");
            System.out.println("QUIZ COMPLETED!");
            System.out.println("Your score: " + score + " / " + questions.size());
            
            double percentage = (double) score / questions.size() * 100;
            System.out.println("Percentage: " + String.format("%.1f", percentage) + "%");
            
            if (percentage >= 80) {
                System.out.println("Excellent work!");
            } else if (percentage >= 60) {
                System.out.println("Good job!");
            } else {
                System.out.println("Keep practicing!");
            }
            System.out.println("================================");
            
        } catch (Exception e) {
            System.out.println("Error during quiz: " + e.getMessage());
        }
    }

    public static List<Question> loadQuestions(String fileName) {
        List<Question> questions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                // Read question
                String questionText = line;
                
                // Read 4 options
                String[] options = new String[4];
                boolean validQuestion = true;
                
                for (int i = 0; i < 4; i++) {
                    line = br.readLine();
                    if (line == null) {
                        validQuestion = false;
                        break;
                    }
                    options[i] = line.trim();
                }
                
                if (!validQuestion) break;

                // Read answer
                String answerLine = br.readLine();
                if (answerLine == null || answerLine.trim().isEmpty()) {
                    break;
                }

                // Extract answer character (last character of the line)
                char answer = Character.toUpperCase(answerLine.trim().charAt(answerLine.trim().length() - 1));
                
                // Validate answer is A, B, C, or D
                if (answer >= 'A' && answer <= 'D') {
                    questions.add(new Question(questionText, options, answer));
                } else {
                    System.out.println("Warning: Invalid answer '" + answer + "' for question: " + questionText);
                }

                // Skip blank line if it exists
                br.readLine();
            }
        } catch (IOException e) {
            System.out.println("Error reading questions file: " + e.getMessage());
            System.out.println("Make sure 'Question.txt' exists in the same folder as your Java file.");
        }
        
        return questions;
    }
}                         