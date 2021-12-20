package myspringboot.reactive.r2dbc;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static java.util.stream.Collectors.toList;

public class LambdaTest {

    /*
        stream의 map()과 flatMap의 차이점 이해
     */
    @Test
    public void transformUsingStream(){
        List<Customer> customers = List.of(new Customer(101, "john", "john@gmail.com", Arrays.asList("397937955", "21654725")),
                new Customer(102, "smith", "smith@gmail.com", Arrays.asList("89563865", "2487238947")),
                new Customer(103, "peter", "peter@gmail.com", Arrays.asList("38946328654", "3286487236")),
                new Customer(104, "kely", "kely@gmail.com", Arrays.asList("389246829364", "948609467")));

        //email 주소 목록 List<String>
        List<String> emailList = customers.stream() //Stream<Customer> (리스트를 가지고 스트림 생성)
                . map(cust -> cust.getEmail()) //Stream<String> (스트링 객체를 담고 잇는 스트림이 됨)
                //.collect(Collectors.toList()); List<String> (스트림을 리스트로 바꾸는 작업)
                .collect(toList()); //List<String> (스트림을 리스트로 바꾸는 작업)
        emailList.forEach(System.out::println);

        //위에 있는 것을 축약해서 해보면..
        customers.stream()
                .map(Customer::getEmail)
                .collect(toList())
                .forEach(System.out::println);

        //map() : <R> Stream<R> map(Function<? super T, ? extends R> mapper)
        List<List<String>> phoneList = customers.stream() //Stream<Customer>
                //.map(Customer::getPhoneNumbers) //Stream<List<String>>
                .map(cust->cust.getPhoneNumbers())//Stream<List<String>>
                .collect(toList()); //List<List<String>>
        System.out.println("phoneList = " + phoneList);
        
        //flatMap : <R> Stream<R> flatMap(Function<? super T,? extends Stream<? extends R>> mapper)
        List<String> phoneList2 = customers.stream()
                .flatMap(customer -> customer.getPhoneNumbers().stream()) //Stream<Stream<List<String>>>
                .collect(toList());
        System.out.println("phoneList2 = " + phoneList2);
    }

    @Test
    public void lambdaTest(){
        //Functional Interface가 가진 추상메소드를 재정의할 때 람다식으로 작성하기

        //1. Anonymous Inner Class
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Anonymous Inner Class");
            }
        });
        t1.start();

        //2. Lambda Expression
        Thread t2 = new Thread(()-> System.out.println("Lambda Expression"));
        t2.start();

        /*
            java.util.function에 제공하는 함수형 인터페이스
            Consumer - void accept(T t) (forEach 사용)
            Predicate - boolean test(T t) (조건에 만족하는 것만 필터링 할 때 사용) 
            Supplier - T get()
            Function - R apply(T t) (Function<T,R> T: 입력, R: 리턴)
            (UnaryFunction<T> 인자 하나)
            (BiFunction<T,T,T> 응답(인자) 두 개, 리턴 하나)
            Operator - 
                UnaryOperator : R apply(T t)
                BinaryOperator : R apply(T t, U u)
         */
        List<String> stringList = List.of("abc", "java", "boot");
        stringList.forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println("s = "+s);
            }
        });
        //Lambda Expression
        stringList.forEach(val -> System.out.println(val));
        //Method Reference
        stringList.forEach(System.out::println);
    }
}
