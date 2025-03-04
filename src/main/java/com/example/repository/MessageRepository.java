package com.example.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entity.Message;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer>{
    /**
     * Property Expression method to get Messages by passig posted by user id
     * @param postedBy
     * @return List of Message objects
     */
    List<Message> findByPostedBy(int postedBy);
}
