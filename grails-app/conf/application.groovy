environments {
  production {
    dataSource {
      driverClassName = "org.postgresql.Driver"
      dialect = org.hibernate.dialect.PostgreSQL94Dialect
//      uri = new URI("postgres://kgmevmhljblkht:LEUEvRKfyeiNuOmUZDPshGGAqs@ec2-54-75-230-140.eu-we?
      url = 'jdbc:postgresql://ec2-54-75-230-140.eu-west-1.compute.amazonaws.com:5432/d3bifn5ooqrvhe?user=kgmevmhljblkht&password=LEUEvRKfyeiNuOmUZDPshGGAqs&sslmode=require'
//      username = uri.userInfo.split(":")[0]
//      password = uri.userInfo.split(":")[1]
    }
  }
}