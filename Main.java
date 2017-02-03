package homework6;
import java.io.PrintStream;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

class Main {
    static PrintStream original;

    public static String getStackTrace(Throwable t) {
        StringBuilder sb = new StringBuilder();
        for(StackTraceElement e : t.getStackTrace()) {
            String err = e.toString();
            if(!err.startsWith("Test") && !err.startsWith("Main") && !err.contains(".reflect.")) {
                sb.append("    " + e.toString() + "\n");
            }
        }
        return sb.toString();
    }

    public static int test(Class<?> test, String testDesc, int testScore) {
        String name = test.getName();
        try {
            System.err.println(name + " output:");
            Class[] argTypes = new Class[] { String[].class };
            Method m = test.getMethod("main", argTypes);
            m.invoke(null, (Object)new String[0]);
            System.err.println("OK");
            original.println(name + " " + testDesc +
                             ": PASSED " + testScore + " points");
            return testScore;
        } catch(Throwable ex) {
            if(ex instanceof InvocationTargetException) {
                ex = ((InvocationTargetException)ex).getTargetException();
            }
            System.err.println(ex);
            String st = getStackTrace(ex);
            if(st.length() > 0) {
                System.err.println("Stacktrace:");
                System.err.println(st);
            }
            original.println(name + " " + testDesc + ": FAILED 0 points");
            return 0;
        }
    }

    public static void main(String[] args) {
        original = System.out;
        try {
            System.setOut(new PrintStream(new FileOutputStream(".program_output")));
        } catch(Exception e) {
            System.err.println("Could not execute test.");
            System.exit(1);
        }

        String test1Desc = "Check constructor 2-colors bipartite graphs   ";
        int test1Score = 25;

        String test2Desc = "Check constructor fast enough on large graphs ";
        int test2Score = 10;

        String test3Desc = "Check constructor colors as well as greedy    ";
        int test3Score = 10;

        String test4Desc = "Check tryImprove competes with randomGreedy   ";
        int test4Score = 35;

        int score = 0;
        score += test(Test1.class, test1Desc, test1Score);
        score += test(Test2.class, test2Desc, test2Score);
        score += test(Test3.class, test3Desc, test3Score);
        score += test(Test4.class, test4Desc, test4Score);

        original.println("Overall: " + score + " points");
        original.println("OK " + score);
    }
}

