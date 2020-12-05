import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Task1 {
    public static void main(String[] args) {
        Map<String, Stream<String>> map = new HashMap<>(){{
            put("Desktop", Stream.of("iVan", "PeTro", "Ira", null));
            put("Web", Stream.of("STepan", "ira", "Andriy", "an na", " "));
            put("Spring", Stream.of("Ivan", "Anna"));
        }};

        MyUtils myUtils = new MyUtils();
        List<String> listNames = myUtils.nameList(map)
                .collect(Collectors.toList());

        System.out.println(listNames);
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
}

