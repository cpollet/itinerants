CREATE CONSTRAINT ON (p:Person) ASSERT p.username IS UNIQUE;
CREATE CONSTRAINT ON (p:Person) ASSERT p.uuid IS UNIQUE;
CREATE CONSTRAINT ON (e:Person) ASSERT e.uuid IS UNIQUE;

CREATE (p:Person {
    uuid: 'cpollet-uuid',
    name: 'Christophe',
    username: 'cpollet',
    password: '1f540711!4b623a04ebbb8655f0c64b98bb268262fabd477dd876d94f97943661aac80d72',
    roles: 'user,admin'
});

CREATE (p:Person {
    uuid: 'user-uuid',
    name: 'User',
    username: 'user',
    password: '1f540711!4b623a04ebbb8655f0c64b98bb268262fabd477dd876d94f97943661aac80d72',
    roles: 'user'
});

CREATE (e:Event {
    uuid: 'event-uuid',
    dateTime: '2018-12-15T19:00:00',
    timestamp: 1544900400,
    name: 'Event'
});

MATCH (p:Person)
MATCH (e:Event)
CREATE (p)-[r:IS_AVAILABLE]->(e);
