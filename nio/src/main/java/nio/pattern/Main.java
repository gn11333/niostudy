package nio.pattern;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
// comment
public class Main {

    public static void main(String[] args) {
        // testReplace();
        testAppend();
    }

    static void testMatch() {
//        Pattern pattern = Pattern.compile("(d.)");
//        Matcher m = pattern.matcher("abcdedfgd");
//        System.out.println(m.group(0));
//        m.start();
//        while (m.find()) {
//            System.out.println(m.group(0));
//        }
    }
    
    static void changeMain(){
    	
    }

    static void testReplace() {
        Pattern pattern = Pattern.compile("(d.)");
        Matcher m = pattern.matcher("abcdefgd");
        System.out.println(m.replaceFirst("12$1\3"));
        System.out.println("abcdefgd".replaceFirst("(d.)", "12$1\\3"));
        System.out.println("abcdef\\\\gd ");
    }

    static void testAppend() {
        Pattern pattern = Pattern.compile("([Tt])hanks");
        Matcher matcher = pattern.matcher("Thanks,,,,, thanks very much");
        StringBuffer sb = new StringBuffer();
        if (matcher.find()) {
            System.out.println(matcher.start());
            System.out.println(sb);
            if (matcher.group(1).equals("T")) {
                matcher.appendReplacement(sb, "Thank you");
            }
            else {
                matcher.appendReplacement(sb, "thank you");
            }

            System.out.println(matcher.start());
            System.out.println(matcher.start());
        }
        System.out.println(matcher.start());
        System.out.println(sb);
        matcher.appendTail(sb);
        System.out.println(sb);
    }
}
