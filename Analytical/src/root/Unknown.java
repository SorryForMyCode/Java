package root;

class Unknown {
    private static final String[] FUNCTIONS = {
            "sin", "cos", "ln", "ch", "sh", "e"
    };

    private String equation;
    private int number;

    Unknown(String equation, int number) {
        this.equation = equation;
        this.number = number;
    }

    static boolean isCorrectFunction(String function){
        for (String f : FUNCTIONS) {
            if(f.equals(function)){
                return true;
            }
        }
        return false;
    }
}
