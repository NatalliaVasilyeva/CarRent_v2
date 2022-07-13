import com.dmdev.natalliavasilyeva.connection.ConnectionPool;
import com.dmdev.natalliavasilyeva.connection.exception.ConnectionPoolException;
import com.dmdev.natalliavasilyeva.domain.model.Accident;
import com.dmdev.natalliavasilyeva.domain.model.Brand;
import com.dmdev.natalliavasilyeva.domain.model.Category;
import com.dmdev.natalliavasilyeva.domain.model.Model;
import com.dmdev.natalliavasilyeva.persistence.repository.custom.AccidentRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.custom.dao.AccidentCustomRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.custom.dao.BrandCustomRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.custom.dao.CategoryCustomRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.custom.dao.ModelCustomRepository;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.chrono.ChronoLocalDate;
import java.util.List;
import java.util.Optional;


public class Main {

    public static void main(String[] args) throws SQLException, ConnectionPoolException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        AccidentCustomRepository accidentRepository = new AccidentCustomRepository(connectionPool) {
        };


       List<Accident> accidents = accidentRepository.findByDamage(new BigDecimal(100));
        accidents.stream().forEach(System.out::println);

        Optional<Accident> accident = accidentRepository.findById(1l);
        System.out.println(!accident.isPresent()? null : accident.get());
    }
}