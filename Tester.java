public class Tester {
    private int nTestCases;
    private int nPassed;
    private int nFailed;
    private int nErrors;
    private StringBuilder expectedActualBuilder;
    private StringBuilder expectedPassed;
    private StringBuilder expectedAll;
    private StringBuilder expectedAllTestsBuilder;
    private TesterQuestionEnum currentQuestion;

    private int maxPoints;
    private int currQuestionpoints;
    private int points;

    public Tester() {
        this.nTestCases = 0;
        this.nPassed = 0;
        this.nFailed = 0;
        this.nErrors = 0;
        this.expectedActualBuilder = new StringBuilder();
        this.expectedPassed = new StringBuilder();
        this.expectedAll = new StringBuilder();
        this.expectedAllTestsBuilder = new StringBuilder();
        this.currentQuestion = null;
        this.maxPoints = 0;
        this.currQuestionpoints = 0;
        this.points = 0;
    }
    
    public void reset() {
        this.nTestCases = 0;
        this.nPassed = 0;
        this.nFailed = 0;
        this.nErrors = 0;
        this.expectedActualBuilder = new StringBuilder();
        this.expectedPassed = new StringBuilder();
        this.expectedAll = new StringBuilder();
        this.currentQuestion = null;
        this.currQuestionpoints = 0;
    }

    public void reset(TesterQuestionEnum currentQuestion) {
        this.nTestCases = 0;
        this.nPassed = 0;
        this.nFailed = 0;
        this.nErrors = 0;
        this.expectedActualBuilder = new StringBuilder();
        this.expectedPassed = new StringBuilder();
        this.expectedAll = new StringBuilder();
        this.currentQuestion = currentQuestion;
        this.initQuestionTest(currentQuestion);
    }

    private void expectedPassed(String input, String expected, String actual) {
        this.appendInputExpectedActual(this.expectedPassed, input, expected, actual);
        this.expectedPassed.append("\n\t").append(TesterMessagesEnum.RESULT.getMessage()).append(TesterMessagesEnum.PASSED.getMessage()).append("\n");
    }

    private void expectedActualBuilder(String input, String expected, String actual) {
        this.appendInputExpectedActual(this.expectedActualBuilder, input, expected, actual);
        this.expectedActualBuilder.append("\n\t").append(TesterMessagesEnum.RESULT.getMessage()).append(TesterMessagesEnum.FAILED.getMessage()).append("\n");
    }

    private void expectedAll(String input, String expected, String actual, boolean hasPassed) {
        this.appendInputExpectedActual(this.expectedAll, input, expected, actual);
        this.expectedAll.append("\n\t").append(TesterMessagesEnum.RESULT.getMessage()).append(this.getCurrentPassedMessage(hasPassed)).append("\n");
    }

    private void expectedAllTest(String input, String expected, String actual, boolean hasPassed) {
        this.appendInputExpectedActual(this.expectedAllTestsBuilder, input, expected, actual);
        this.expectedAllTestsBuilder.append("\n\t").append(TesterMessagesEnum.RESULT.getMessage()).append(this.getCurrentPassedMessage(hasPassed)).append("\n");
    }

    private void appendInputExpectedActual(StringBuilder sb, String input, String expected, String actual) {
        sb.append("\n").append(this.buildCaseNumber()).append("\n");
        sb.append("\t").append(TesterMessagesEnum.INPUT.getMessage() + "\"" + input + "\"").append("\n");
        sb.append("\t").append(TesterMessagesEnum.EXPECTED.getMessage() + "\"" + expected + "\"").append("\n");
        sb.append("\t").append(TesterMessagesEnum.ACTUAL.getMessage() + "\"" + actual + "\"").append("\n");
    }

    public boolean test(String input, int expected, int actual) {
        this.nTestCases++;
        boolean hasPassed = expected == actual;
        if (hasPassed) {
            this.nPassed++;
            this.expectedPassed(input, expected + "", actual + "");
        } else {
            this.nFailed++;
            this.expectedActualBuilder(input, expected + "", actual + "");
        }
        this.expectedAll(input, expected + "", actual + "", hasPassed);
        this.expectedAllTest(input, expected + "", actual + "", hasPassed);
        return hasPassed;
    }

    public boolean test(String input, boolean expected, boolean actual) {
        this.nTestCases++;
        boolean hasPassed = expected == actual;
        if (hasPassed) {
            this.nPassed++;
            this.expectedPassed(input, expected + "", actual + "");
        } else {
            this.nFailed++;
            this.expectedActualBuilder(input, expected + "", actual + "");
        }
        this.expectedAll(input, expected + "", actual + "", hasPassed);
        this.expectedAllTest(input, expected + "", actual + "", hasPassed);
        return hasPassed;
    }

    public boolean test(String input, String expected, String actual) {
        this.nTestCases++;
        boolean hasPassed = expected.equals(actual);
        if (hasPassed) {
            this.nPassed++;
            this.expectedPassed(input, expected, actual);
        } else {
            this.nFailed++;
            this.expectedActualBuilder(input, expected, actual);
        }
        this.expectedAll(input, expected, actual, hasPassed);
        this.expectedAllTest(input, expected, actual, hasPassed);
        return hasPassed;
    }
    
    public void initQuestionTest(TesterQuestionEnum testQuestionEnum) {
        this.buildQuestionTitleName(testQuestionEnum);
        this.maxPoints += testQuestionEnum.getMaxPoints();
        this.currQuestionpoints = testQuestionEnum.getMaxPoints();
    }
    public void updatePoints() {
        this.points += this.currQuestionpoints;
    }

    private void buildQuestionTitleName(TesterQuestionEnum testQuestionEnum) {
        this.expectedAll.append("\n").append(testQuestionEnum.getQuestion()).append("\n");
        this.expectedActualBuilder.append("\n").append(testQuestionEnum.getQuestion()).append("\n");
        this.expectedPassed.append("\n").append(testQuestionEnum.getQuestion()).append("\n");
        this.expectedAllTestsBuilder.append("\n").append(testQuestionEnum.getQuestion()).append("\n");
    }

    private String buildCaseNumber() {
        return TesterMessagesEnum.CASE_NUMBER.getMessage() + this.nTestCases;
    }

    public StringBuilder finishTest() {
        StringBuilder sb = new StringBuilder();
        sb.append(TesterMetadataMessagesEnum.QUESTION.getMessage()).append(this.getQuestion()).append(" ").append(TesterMetadataMessagesEnum.TEST.getMessage()).append("\n");
        sb.append(TesterMetadataMessagesEnum.TEST_CASE_NUMBER.getMessage()).append(this.nTestCases).append("\n");
        sb.append(TesterMetadataMessagesEnum.PASSED.getMessage()).append(this.nPassed).append("\n");
        sb.append(TesterMetadataMessagesEnum.FAILED.getMessage()).append(this.nFailed).append("\n");
        sb.append(TesterMetadataMessagesEnum.ERRORS.getMessage()).append(this.nErrors).append("\n");
        this.appendPassedOrFailedCases(sb);   
        return sb;
    }

    private String getQuestion() {
        return this.currentQuestion != null ? this.currentQuestion.getQuestion() : "";
    }

    private void appendPassedOrFailedCases(StringBuilder sb) {
        if (this.hasPassed()){
            sb.append(TesterMetadataMessagesEnum.ALL_TESTS_ARE_PASSED.getMessage());
        } else {
            sb.append(this.expectedActualBuilder.toString());
        }
    }

    public boolean hasPassed() {
        return this.nFailed == 0 && this.nErrors == 0 && this.nPassed == this.nTestCases;
    }

    public String getCurrentPassedMessage(boolean hasPassed) {
        return hasPassed ? TesterMessagesEnum.PASSED.getMessage() : TesterMessagesEnum.FAILED.getMessage();
    }

    @Override
    public String toString(){
        String finished = this.finishTest().toString();
        this.reset();
        return finished;
    }

    public void showAllTests() {
        System.out.println(this.expectedAllTestsBuilder.toString());
    }
 
    public void showAll() {
        System.out.println(this.expectedAll.toString());
    }

    public void showPassedOnly() {
        System.out.println(this.expectedPassed.toString());
    }

    public void showFailedOnly() {
        System.out.println(this.expectedActualBuilder.toString());
    }

    public void gotExceptions() {
        this.nErrors++; 
    }

    public void conclusion(){
        
        System.out.println("Your score is: " + this.points + "/" + this.maxPoints + " available points");
    }
    
}
