package cakecatalog

class Cake {

  long id
  String name
  String description
  long ownerId
  boolean isPublic

  static constraints = {
  }

  static mapping = {
    id generator: "identity"
  }
}
