package test;

import org.junit.Test;

/**
 * Created by shilin on 2015/3/12.
 */
public class testMy {
    @Test
    public void test1() {
        try {
            new MyExc().tt();
        }catch (Throwable e){
            System.out.println(e.getStackTrace());
        }
        try {
            new MyExc().tt2();
        }catch (Throwable e){
            System.out.println(e.getStackTrace());
        }
    }

    public class MyExc {
       public  void tt() throws  ExceptionB,ExceptionC{
           throw new ExceptionB("BB");
       }
        public  void tt2() throws  ExceptionB,ExceptionC{
            throw new ExceptionB("CC");
        }

    }
    public class ExceptionC extends Exception {
        private Throwable cause;

        public ExceptionC(String msg) {
          super(msg);
            cause=this;
        }
    }
    public class ExceptionB extends Error {
        private Throwable cause;

        public ExceptionB(String msg) {
            super(msg);
            cause=this;
        }
    }

}
