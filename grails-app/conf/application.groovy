environments {
  production {
    dataSource {
      dbCreate = "update"
      driverClassName = "org.postgresql.Driver"
      dialect = org.hibernate.dialect.PostgreSQL94Dialect
      uri = new URI(System.env.DATABASE_URL ?: "postgres://test:test@localhost/test")
//      url = "jdbc:postgresql://" + uri.host + ":" + uri.port + uri.path
//      url = "jdbc:postgresql://ec2-54-75-230-140.eu-west-1.compute.amazonaws.com:5432/d3bifn5ooqrvhe?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory"
      username = uri.userInfo.split(":")[0]
      password = uri.userInfo.split(":")[1]
    }
  }
}