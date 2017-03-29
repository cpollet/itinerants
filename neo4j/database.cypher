CREATE CONSTRAINT ON (p:Person) ASSERT p.username IS UNIQUE
CREATE (p:Person { name: 'Christophe', username: 'cpollet', password: 'password' })
