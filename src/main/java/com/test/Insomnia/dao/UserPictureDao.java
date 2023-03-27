package com.test.Insomnia.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.Insomnia.model.UserPicture;
import com.test.Insomnia.model.UserPicturePK;

public interface UserPictureDao extends JpaRepository<UserPicture, UserPicturePK> {

}
