package com.java.web.estateagency.repository;

import java.util.HashSet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.java.web.estateagency.entity.Chat;
@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    @Query("SELECT c FROM Chat c WHERE c.firstUserName.username = ?1")
    HashSet<Chat> getChatByFirstUserName(String username);

    @Query("SELECT c FROM Chat c WHERE c.secondUserName.username = ?1")
    HashSet<Chat> getChatBySecondUserName(String username);

    @Query("SELECT c FROM Chat c WHERE c.firstUserName.username = ?1 AND c.secondUserName.username = ?2")
    HashSet<Chat> getChatByFirstUserNameAndSecondUserName(String firstUserName, String secondUserName);

    @Query("SELECT c FROM Chat c WHERE c.firstUserName.username = ?1 AND c.secondUserName.username = ?2")
    HashSet<Chat> getChatBySecondUserNameAndFirstUserName(String firstUserName, String secondUserName);
}
