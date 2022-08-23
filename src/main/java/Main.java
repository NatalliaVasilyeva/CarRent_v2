import com.dmdev.natalliavasilyeva.connection.ConnectionPool;
import com.dmdev.natalliavasilyeva.connection.exception.ConnectionPoolException;
import com.dmdev.natalliavasilyeva.domain.model.Brand;
import com.dmdev.natalliavasilyeva.domain.model.Category;
import com.dmdev.natalliavasilyeva.domain.model.EngineType;
import com.dmdev.natalliavasilyeva.domain.model.Model;
import com.dmdev.natalliavasilyeva.domain.model.Transmission;
import com.dmdev.natalliavasilyeva.service.ModelService;
import com.dmdev.natalliavasilyeva.utils.PasswordUtils;

import java.sql.SQLException;


public class Main {

    public static void main(String[] args) throws SQLException, ConnectionPoolException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        PasswordUtils.generateHash("RabbitAdm", "abcdabcd");
        System.out.println(PasswordUtils.generateHash("RabbitAdm", "aA80171674880"));
//        OrderCustomRepository orderRepository = new OrderCustomRepository(connectionPool);

//
////        System.out.println(userRepository.findByLoginAndPassword("RabbitAdm", "abcd"));
//
//       List<Order> orders = orderRepository.findAll();
//        orders.stream().forEach(System.out::println);
//
////        Optional<User> user = userCustomRepository.findByRole("client");
////        System.out.println(user.get());
//
////                boolean car = carRepository.isCarAvailable(23l, LocalDateTime.parse( "2022-07-05T10:15:39").toInstant(ZoneOffset.UTC), LocalDateTime.parse( "2022-07-10T10:15:39").toInstant(ZoneOffset.UTC));
////        System.out.println(car);
//
//    }
//        Optional<Accident> accident = accidentRepository.findById(1l);
//        System.out.println(accident.get());

//        List<Accident> accidents = accidentRepository.findAll();
//        accidents.stream().forEach(System.out::println);

//        Accident accident = new Accident.Builder()
//                .order(1l)
//                .date(LocalDateTime.now().toInstant(ZoneOffset.UTC))
//                .description("saved description")
//                .damage(BigDecimal.valueOf(15))
//                .build();
    }
}