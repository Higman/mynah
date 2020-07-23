package jp.ac.kagawalab.mynah.core.repository;

import jp.ac.kagawalab.mynah.core.entity.Recruitment;
import jp.ac.kagawalab.mynah.core.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecruitmentRepository  extends JpaRepository<Recruitment, Integer> {
    List<Recruitment> findAllByRecruiter(User user);
}
