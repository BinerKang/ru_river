package com.biner.ru.mapper;

import java.util.Vector;

import org.apache.ibatis.annotations.Param;

import com.biner.ru.model.Score;

public interface ScoreMapper extends BaseMapper<Score> {

	Vector<Score> getAllRank(@Param("item")Score obj);
}
