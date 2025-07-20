
public class Question {
    private final String question;
    private final String[] options;
    private final char answer;

    public Question(String question, String[] options, char answer) {
        this.question = question.trim();
        this.options = options;
        this.answer = Character.toUpperCase(answer);
    }

    public void display(int number) {
        System.out.println("\n" + number + ") " + question);
        char opt = 'A';
        for (String option : options) {
            System.out.println("(" + opt + ") " + option.trim());
            opt++;
        }
    }

    public boolean isCorrect(char userAnswer) {
        return Character.toUpperCase(userAnswer) == answer;
    }

    public char getAnswer() {
        return answer;
    }
}