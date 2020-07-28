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
}
