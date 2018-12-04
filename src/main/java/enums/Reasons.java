package enums;

public enum Reasons {
    WRONG_SIZE("Size of answers is wrong"),
    EMPTY_ANSWER("Answer is empty"),
    NO_CORRECT_WORD("Does not contain correct word"),
    CORRECT_WORD("Contain correct word"),
    NOT_EMPTY("Answer is not empty");

        private String text;
        public String text(){return text;}

        Reasons(String text) {
            this.text = text;
        }

}
