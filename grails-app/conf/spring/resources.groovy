package spring

import com.cakecatalog.srv.auth.client.AuthClient

// Place your Spring DSL code here
beans = {

  authClient(AuthClient, application.config.auth.url, "MONOLITH") {}
}
