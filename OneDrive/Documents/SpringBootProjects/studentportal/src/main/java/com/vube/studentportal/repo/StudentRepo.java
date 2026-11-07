
package com.vube.studentportal.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vube.studentportal.dbconnect.FirstConnectJdbcToMysql;

@Repository
public interface StudentRepo
        extends JpaRepository<FirstConnectJdbcToMysql, Integer> {
}