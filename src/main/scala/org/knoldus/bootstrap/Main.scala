package org.knoldus.bootstrap

import org.knoldus.database.UserDatabase
import org.knoldus.model.{User, UserType}
import org.knoldus.repository.dao.Dao
import org.knoldus.service.UserService

object Main {
  def main(args: Array[String]): Unit = {
    val data_access_object: Dao[User] = new UserDatabase
    val userService = new UserService(data_access_object)
    val user1 = User(java.util.UUID.randomUUID(), "Yash", UserType.Admin)
    val user2 = User(java.util.UUID.randomUUID(), "Yas", UserType.Admin)
    val user3 = User(java.util.UUID.randomUUID(), "Yasdh", UserType.Admin)
    val user4 = User(java.util.UUID.randomUUID(), "Yas", UserType.Admin)
    userService.createNewUser(user1)
    userService.createNewUser(user2)
    userService.createNewUser(user3)
    userService.createNewUser(user4)
    println("==============================================================Print All users:-")
    for(users <- userService.getAllUser()) {
      println(users)
    }

    println("===============================================================Update a USer:-")
    val new_user = User(java.util.UUID.randomUUID(), "Honey Singh", UserType.Customer)
    println(userService.updateUser(user4,new_user))
    for(users <- userService.getAllUser()) {
      println(users)
    }
    println("===============================================================Update the category of an existed user")
    println(userService.updateUserCategory(new_user,UserType.Admin))
    for(users <- userService.getAllUser()) {
      println(users)
    }
    println("===============================================================Update the name of an Existing User")
    println(userService.updateUserName(new_user,"Sidhu Moosewala"))
    for(users <- userService.getAllUser()) {
      println(users)
    }

    println("===============================================================Delete an USer")
    if(userService.deleteUser(user2)) println("User deleted")
    else println("User can't delete may be it does not exist")
    for(users <- userService.getAllUser()) {
      println(users)
    }

    println("===============================================================")

  }

}
