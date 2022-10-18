package com.hikvision.pbg.sitecodeprj.repository;

import com.hikvision.pbg.sitecodeprj.repository.entity.DeleteTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeleteTaskRepository extends JpaRepository<DeleteTask,Long> {

}
