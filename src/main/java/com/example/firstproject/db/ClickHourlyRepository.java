package com.example.firstproject.db;

import com.example.firstproject.service.ClickHourly;
import com.example.firstproject.service.UrlClick;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ClickHourlyRepository extends MongoRepository<ClickHourly,String>{
}

