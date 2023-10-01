package org.niket;

import java.net.URI;
import java.net.URISyntaxException;

public class Test {
    public static void main(String[] args) throws URISyntaxException {
        String databaseUrl = "postgres://mitter:u6JWu83Og3yz1MdAK1qmFP+7ZUrbBatA6sY=@master.rds-chat-bridge-csb.prod.internal:5432/mitter?ssl=true&sslmode=verify-ca&sslrootcert=/var/data/certs/rds-ca-2019-ap-south-1.pem";
        URI jdbcUri = new URI(databaseUrl);
        System.out.println(jdbcUri.getUserInfo());
    }
}
