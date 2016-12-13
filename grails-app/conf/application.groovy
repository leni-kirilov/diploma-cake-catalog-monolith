environments {
  production {
    dataSource {
      driverClassName = "org.postgresql.Driver"
      dialect = org.hibernate.dialect.PostgreSQL94Dialect
//      uri = new URI("postgres://kgmevmhljblkht:LEUEvRKfyeiNuOmUZDPshGGAqs@ec2-54-75-230-140.eu-we?
      url = 'jdbc:postgresql://ec2-54-228-255-234.eu-west-1.compute.amazonaws.com:5432/dbq2rvsu94nh6f?user=fpkxyggrntzlax&password=e7307ff88a46bf6731e1a7c8f6a3588cf00dcce8bfd143b32a2bc170a6c516e5&sslmode=require'
//      username = uri.userInfo.split(":")[0]
//      password = uri.userInfo.split(":")[1]
    }
  }
}