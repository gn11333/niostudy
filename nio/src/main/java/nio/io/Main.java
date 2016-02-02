package nio.io;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws IOException {
        Set<String> set = new HashSet<String>();
        set.add("a");
        set.add("b");
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            String s = it.next();
            it.remove();
            System.out.println(s);
            System.out.println(set);
        }
    }

}
