import java.util.ArrayList;
import java.util.List;

public class Generics {

    // Java
    void copyAll(Collection<Object> to, Collection<String> from) {
        to.addAll(from);
        // !!! Would not compile with the naive declaration of addAll:
        // Collection<String> is not a subtype of Collection<Object>
    }


    public static void main(String[] args) {
        // Java
        List<String> strs = new ArrayList<String>();
//        List<Object> objs = strs; // !!! A compile-time error here saves us from a runtime exception later.
//        objs.add(1); // Put an Integer into a list of Strings
        String s = strs.get(0); // !!! ClassCastException: Cannot cast Integer to String

    }
}

// Java
interface Collection1<E> {
    void addAll(Collection<E> items);
}

// Java
interface Collection<E> {
    void addAll(Collection<? extends E> items);
}

