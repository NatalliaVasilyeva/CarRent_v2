import com.dmdev.natalliavasilyeva.connection.ConnectionPool;
import com.dmdev.natalliavasilyeva.connection.exception.ConnectionPoolException;
import com.dmdev.natalliavasilyeva.domain.jpa.AccidentJpa;
import com.dmdev.natalliavasilyeva.domain.jpa.ModelJpa;
import com.dmdev.natalliavasilyeva.domain.model.Brand;
import com.dmdev.natalliavasilyeva.domain.model.Category;
import com.dmdev.natalliavasilyeva.domain.model.EngineType;
import com.dmdev.natalliavasilyeva.domain.model.Model;
import com.dmdev.natalliavasilyeva.domain.model.Transmission;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao.AccidentJpaRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao.ModelRepository;
import com.dmdev.natalliavasilyeva.service.ModelService;

import java.sql.SQLException;
import java.util.Optional;


public class Main {

    public static void main(String[] args) throws SQLException, ConnectionPoolException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
//        OrderCustomRepository orderRepository = new OrderCustomRepository(connectionPool);
        ModelService modelService = new ModelService();
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

        Model model = new Model.Builder()
                .brand(new Brand.Builder().id(1l).build())
                .category(new Category.Builder().id(1l).build())
                .name("test_model_2")
                .transmission(Transmission.AUTOMATIC)
                .engine(EngineType.DIESEL)
                .build();

        Model saved = modelService.createModel(model);
        System.out.println(saved);
    }
}