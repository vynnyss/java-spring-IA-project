package com.example.sdw24.adapters.out;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.sdw24.domain.model.Champions;
import com.example.sdw24.domain.ports.ChampionsRepository;

@Repository
public class ChampionsJdbcRepository implements ChampionsRepository {
	
	private final JdbcTemplate jdbcTemplate;
	private final RowMapper<Champions> rowMapper;
	
	public ChampionsJdbcRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.rowMapper = (rs, rowNum) -> new Champions(
				rs.getLong("id"),
				rs.getString("name"),
				rs.getString("role"),
				rs.getString("lore"),
				rs.getString("image_url")
				);
	}

	//List<Double>
	@Override
	public List<Champions> findAll() {
		return jdbcTemplate.query("SELECT * FROM CHAMPIONS", rowMapper);
	}

	@Override
	public Optional<Champions> findById(Long id) {
		String sql = "SELECT * FROM CHAMPIONS WHERE ID = ?";
		List<Champions> champions = jdbcTemplate.query(sql, rowMapper, id);
		return champions.stream().findFirst();
	}

}
