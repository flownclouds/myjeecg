package test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/*将一个正整数分解质因数
 * 每个合数都可以写成几个质数相乘的形式，这几个质数就都叫做这个合数的质因数
 */
public class IntergerFactorTest {

    public static void main(String[] args) {
        primeFactor();// 调用primeFactor()方法
    }

    /*
     * 程序分析：对n进行分解质因数，应先找到一个最小的质数k，然后按下述步骤完成：
     * (1)如果这个质数恰等于n，则说明分解质因数的过程已经结束，打印出即可。
     * (2)如果n>=k，但n能被k整除，则应打印出k的值，并用n除以k的商,作为新的正整数你n,重复执行第一步。&nbsp;&nbsp;&nbsp;
     * (3)如果n不能被k整除，则用k+1作为k的值,重复执行第一步。&nbsp;&nbsp;
     */
    public static void primeFactor() {// 该方法将一个正整数分解成质因数相乘的形式

        //Scanner scan = new Scanner(System.in);// 接收控制台输入的信息
        //System.out.print("请输入一个正整数：");
        BigDecimal num=PrimeNumberTest.createRadomPrimeNunberProduct(5);
        System.out.println("生成一个正整数："+num);
        try {
            //int num = scan.nextInt();// 取出控制台输入的信息

            if (num.compareTo(BigDecimal.valueOf(2))<0) {// 若输入的数小于2,输出提示信息

                System.out.println("必须输入不小于2的正整数！");
            } else {

                BigDecimal primeNumber =new BigDecimal( 2);// 定义最小的质数
                System.out.print(num + " = ");

                while (primeNumber.compareTo(num)<0) {// 在质数小于输入的数时，进行循环

                    if (num.remainder(primeNumber) ==BigDecimal.ZERO) {// 当输入的数与质数的余数为0时,输出这个质数

                        System.out.print(primeNumber + " * ");
                        num = num .divide( primeNumber);// 把剩下的结果赋给num
                        System.out.println(num);
                        break;

                    } else {// 在余数不为0时,质数递增
                        primeNumber=primeNumber.add(BigDecimal.valueOf(1));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("必须输入正整数！");// 捕捉异常,输出必须输入整数
        }
    }
}
class PrimeNumberTest {
    /** Creates a new instance of PrimeNumberTest */
    public PrimeNumberTest() {
    }
//    public static void main(String[] args){
//        //获得一个4位数的随机大素数
//        long longVar4 = createRadomPrimeNunber(4);
//        System.out.println(longVar4);
//        //获得一个5位数的随机大素数
//        long longVar5 = createRadomPrimeNunber(5);
//        System.out.println(longVar5);
//    }


    public static long createRadomPrimeNunber(int n){
        long recLong = 0;
        List list = listAllPrimeNumber(n);
        Random rd = new Random();
        int randomIndex = Math.abs( rd.nextInt()%list.size());
        recLong = ((Long)list.get(randomIndex)).longValue();
        return recLong;

    }
    public static BigDecimal createRadomPrimeNunberProduct(int n){
        long recLong = 0;
        //List list = listAllPrimeNumber(n);

        List list = new ArrayList();
        long low = (long)Math.pow(10,n-1);
        long high = (long)Math.pow(10,n) - 1;
        for(long i= low;i < low+100;i++){
            if( isSushu(i)) {
                list.add(new Long(i));
            }
        }

        Random rd = new Random();
        int randomIndex = Math.abs( rd.nextInt()%list.size());
        recLong = ((Long)list.get(randomIndex)).longValue();

        randomIndex = Math.abs( rd.nextInt()%list.size());
        long recLong2 = ((Long)list.get(randomIndex)).longValue();
                 System.out.println(recLong+"*"+recLong2);
      BigDecimal val=  BigDecimal.valueOf(recLong).multiply(BigDecimal.valueOf(recLong2));
      return val;
    }
    public static List listAllPrimeNumber(int n){
        List list = new ArrayList();
        long low = (long)Math.pow(10,n-1);
        long high = (long)Math.pow(10,n) - 1;
        for(long i= low;i < high;i++){
            if( isSushu(i)) {
                list.add(new Long(i));
            }
        }
        return list;
    }
    public static boolean isSushu(long x){//定义一个判断一个数是否是素数的函数
        if(x<2) return false;
        if( x==2)return true;
        for(long i=2;i<= (long)Math.sqrt(x);i++)
            if(x%i==0) return false;
        return true;
    }
}
