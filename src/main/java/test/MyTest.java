package test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by shilin on 2015/7/17.
 */
public class MyTest {
    public static void main(String[] args) {
       ClassLoader cl= Thread.currentThread().getContextClassLoader();

       Type tp= new TypeReference<UserInfo>(){}.getType();
       Class cls= tp.getClass();

        TypeReference2<UserInfo> tp2= new TypeReference2<UserInfo>();
      Class c2= tp2.getClass();

       // cls.newInstance();
    }
}
class UserInfo{
    private String Name;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
class TypeReference2<T>{

}
 abstract class TypeReference<T>
        implements Comparable<TypeReference<T>>
{
    final Type _type;

    protected TypeReference()
    {
        Type superClass = getClass().getGenericSuperclass();
        if (superClass instanceof Class<?>) { // sanity check, should never happen
            throw new IllegalArgumentException("Internal error: TypeReference constructed without actual type information");
        }
        /* 22-Dec-2008, tatu: Not sure if this case is safe -- I suspect
         *   it is possible to make it fail?
         *   But let's deal with specifc
         *   case when we know an actual use case, and thereby suitable
         *   work arounds for valid case(s) and/or error to throw
         *   on invalid one(s).
         */
        _type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
    }

    public Type getType() { return _type; }

    /**
     * The only reason we define this method (and require implementation
     * of <code>Comparable</code>) is to prevent constructing a
     * reference without type information.
     */
    public int compareTo(TypeReference<T> o) {
        // just need an implementation, not a good one... hence:
        return 0;
    }
}