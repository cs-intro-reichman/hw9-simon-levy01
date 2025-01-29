public class Test {

    public static final TestHandler testHandler = new TestHandler();
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java Test <Question Name/number>");
        } else {
            TesterQuestionEnum question = TesterQuestionEnum.valueOf(args[0]);
            testHandler.questionDecider(question, args);
        }
    }
}