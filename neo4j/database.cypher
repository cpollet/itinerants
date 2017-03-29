CREATE CONSTRAINT ON (p:Person) ASSERT p.username IS UNIQUE
CREATE (p:Person { name: 'Christophe', username: 'cpollet', password: '1f540711!4b623a04ebbb8655f0c64b98bb268262fabd477dd876d94f97943661aac80d72' })
