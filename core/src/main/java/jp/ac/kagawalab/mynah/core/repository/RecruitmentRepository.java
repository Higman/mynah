package jp.ac.kagawalab.mynah.core.repository;

import jp.ac.kagawalab.mynah.core.entity.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruitmentRepository  extends JpaRepository<Recruitment, Integer> {
}
