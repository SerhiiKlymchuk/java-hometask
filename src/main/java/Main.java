import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        MyUtils myUtils = new MyUtils();

        //Task 1
//        Map<String, Stream<String>> mapNames = new HashMap<>(){{
//            put("Desktop", Stream.of("iVan", "PeTro", "Ira", null));
//            put("Web", Stream.of("STepan", "ira", "Andriy", "an na", " "));
//            put("Spring", Stream.of("Ivan", "Anna"));
//        }};
//
//        List<String> listNames = myUtils.nameList(mapNames)
//                .collect(Collectors.toList());
//
//        System.out.println(listNames);
//
       //Task 2
//        List<Stream<String>> listPhoneNumbers = new ArrayList<>(Arrays.asList(
//                Stream.of("093 987 65 43", "(050)1234567", "12-345"),
//                Stream.of("067-21-436-57", "050-2345678",  "0939182736", "224-19-28", "22521"),
//                Stream.of("(093)-11-22-334", "044 435-62-18", "721-73-45")
//        ));
//
//        Map<String, Stream<String>>mapPhone = myUtils.phoneNumbers(listPhoneNumbers);
//
//        mapPhone.forEach((e, stream) ->{
//            System.out.print("\n" + e + "=");
//            stream.forEach(v-> System.out.print("["+v+"] "));
//        });

        //Task 3
//        Stream<IntStream> sumEvenStream = new ArrayList<>(Arrays.asList(
//                IntStream.of(-2, -4, 1, 8, 3, 10),
//                IntStream.of(2, -4, 4, 0, 3, 1),
//                IntStream.of(1, -4, 3, 5, 3, 1)
//        )).stream();
//
//        System.out.println(myUtils.sumEven(sumEvenStream));
//
        //Task 4
//        IntStream countNumberInt = IntStream.of(3, 2, 1, 13, 21, 15);
//        Stream<String>countNumberStr = Stream.of("9", "4", "23", "0", "32", "5");
//
//        System.out.println(myUtils.countNumbers(countNumberInt, countNumberStr));
    }
}

class MyUtils{
    Stream<String> nameList(Map<String, Stream<String>> map) throws NullPointerException{
        if(map == null) throw new NullPointerException();

        List <String>fullList = new ArrayList<>();
        map.values()
                .forEach(e->fullList.addAll(e.collect(Collectors.toList())));

        return fullList.stream()
                .filter(e->e!=null && !e.isBlank())
                .map(e->{
                    e = e.replaceAll(" ", "").toLowerCase();
                    e = e.substring(0,1).toUpperCase() + e.substring(1);
                    return e;
                })
                .distinct().sorted();
    }

    Map<String, Stream<String>> phoneNumbers(List<Stream<String>> list){
        Map<String, Stream<String>> phoneMap = new HashMap<>();
        List<String>fullList = new ArrayList<>();
        list.forEach(e->fullList.addAll(e.collect(Collectors.toList())));

        System.out.println(fullList);
        Map<String, Stream<String>> map= new HashMap<>();

        fullList.stream()
                .map(e->e.replaceAll("[-()., ]", ""))
                .forEach(e->{
                    if(e.length() == 10){
                        map.merge(e.substring(0,3), Stream.of(e.substring(3)), Stream::concat);
                    }
                    else if(e.length()==7){
                        map.merge("loc", Stream.of(e), Stream::concat);
                    }
                    else{
                        map.merge("err", Stream.of(e), Stream::concat);
                    }
                });
        return map;
    }
    int sumEven(Stream<IntStream> stream){
        return stream.map(
                    e -> e.filter(v -> v>0 && v%2==0).min()
                )
                .map(e -> e.isEmpty() ? 0 : e.getAsInt())
                .reduce(0, Integer::sum);
    }
    int countNumbers(IntStream intNum, Stream<String> strNum){
        Stream<String> intToStr = intNum.mapToObj(String::valueOf);
        Stream<String> mainStream = Stream.concat(strNum, intToStr);

        return Integer.parseInt(mainStream
                .filter(e->e!="0")
                .reduce("0", (acc,e)->{
                    if(Integer.parseInt(e)%3==0 || e.contains("3")){
                        return String.valueOf(Integer.parseInt(acc)+1);
                    }
                    else return acc;
        }));
    }
}

