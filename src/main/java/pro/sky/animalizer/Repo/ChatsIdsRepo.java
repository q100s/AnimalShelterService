package pro.sky.animalizer.Repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.sky.animalizer.Model.Request;

public interface ChatsIdsRepo extends JpaRepository<Request,Long> {
    @Query(value = "SELECT COUNT(id)  FROM request WHERE chat_id = ?1", nativeQuery = true)
    public int countRequestsByChatId(Long chatId);

}
