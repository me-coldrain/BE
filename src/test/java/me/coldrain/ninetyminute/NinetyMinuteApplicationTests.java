package me.coldrain.ninetyminute;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.data.cassandra.DataCassandraTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

//@SpringBootTest
@Transactional
class NinetyMinuteApplicationTests {

    private static class Response {
        private String uuid;
        private List<String> weekdays;

        public Response(List<String> weekdays) {
            this.uuid = UUID.randomUUID().toString();
            this.weekdays = weekdays;
        }

        @Override
        public String toString() {
            return "Response{" +
                    "uuid='" + uuid + '\'' +
                    ", weekdays=" + weekdays +
                    '}';
        }
    }

    @Test
    void contextLoads() {
        ArrayList<Response> responses = new ArrayList<>();
        Response response1 = new Response(List.of("MON", "TUE"));
        Response response2 = new Response(List.of("MON"));
        responses.add(response1);
        responses.add(response2);

        responses.stream()
                .filter(r -> r.weekdays.containsAll(List.of("MON")))
                .collect(Collectors.toList())
                .forEach(System.out::println);

//        for (Response response : responses) {
//            System.out.println(response);
//        }
    }

}
