package jp.ac.kagawalab.mynah.core.repository;

import jp.ac.kagawalab.mynah.core.entity.Recruitment;
import jp.ac.kagawalab.mynah.core.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecruitmentRepository  extends JpaRepository<Recruitment, Integer> {
    List<Recruitment> findAllByRecruiter(User user);

    @Query(value = "SELECT (SELECT COUNT(*) + 1 FROM recruitment b WHERE b.created_at < a.created_at) AS number FROM recruitment a WHERE id = ?1.id",
            nativeQuery = true)
    int getRegistrationOrderNumberRaw(Recruitment rec);

    @Query(value = "SELECT * FROM recruitment r, users u WHERE r.user_id = u.id AND u.id = ?1",
    nativeQuery = true)
    List<Recruitment> findAllByUserId(int userId);

    @Query(value = "SELECT * FROM recruitment r, users u, boards b WHERE r.user_id = u.id AND u.id = ?1 AND r.board_id = ?2",
            nativeQuery = true)
    List<Recruitment> findAllByUserIdAndBoardId(int userId, int boardId);
}
