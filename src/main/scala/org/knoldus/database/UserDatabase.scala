package org.knoldus.database

import org.knoldus.model.{User, UserType}
import org.knoldus.repository.dao.Dao

import java.util.UUID
import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.util.{Failure, Success, Try}

class UserDatabase extends Dao[User]{

  var user_index: mutable.Map[UUID, User] = mutable.Map[UUID,User]()

  var user_database: ListBuffer[User] = ListBuffer[User]()

  override def createUser(user: User): Boolean = {
    if(!user_database.contains(user)){
      Try{
        user_database += user
        user_index += (user.id -> user)
      } match {
        case Failure(_) => false
        case Success(_) => true
      }
    }
    else false
  }

  override def listAllUser(): List[User] = {
    user_database.toList
  }

  override def updateUser(oldUser : User, newUser : User): Boolean = {
    if(user_database.contains(oldUser)){
      val pos = user_database.indexOf(oldUser)
      user_database.update(pos , newUser)
      user_index -= oldUser.id
      user_index += newUser.id -> newUser
      true
    }
    else false
  }

  override def updateUserName(user: User, newName: String): Boolean = {
    if(user_index.contains(user.id)){
      Try{
        val newUserObject = User(user_index(user.id).id,newName, user_index(user.id).category)
        user_database.update(user_database.indexOf(user_index(user.id)),newUserObject)
        user_index -= user.id
        user_index += newUserObject.id -> newUserObject
      } match {
        case Success(_) => true
        case Failure(exception) =>
          false
      }
    }
    else {
      false
    }
  }

  override def updateUserCategory(user: User, newCategory: UserType.Value): Boolean = {
    if(user_index.contains(user.id)){
      Try{
        val newUserObject = User(user_index(user.id).id,user_index(user.id).name,newCategory)
        user_database.update(user_database.indexOf(user_index(user.id)),newUserObject)
        user_index -= user.id
        user_index += newUserObject.id -> newUserObject
      } match {
        case Success(_) => true
        case Failure(_) => false
      }
    }
    else false
  }

  override def deleteUser(user: User): Boolean = {
    Try{
      user_database.remove(user_database.indexOf(user))
      user_index -= user.id
    } match {
      case Success(_) => true
      case Failure(_) =>false
    }
  }

  override def getUserById(id: UUID): User = {
    if(user_index.contains(id)) user_index(id)
    else null
  }
}
