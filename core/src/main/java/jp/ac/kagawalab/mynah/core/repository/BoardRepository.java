package jp.ac.kagawalab.mynah.core.repository;

import jp.ac.kagawalab.mynah.core.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Integer> {
}
