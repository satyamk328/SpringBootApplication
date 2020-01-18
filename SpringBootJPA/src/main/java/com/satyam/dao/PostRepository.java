package com.satyam.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.satyam.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {


}
