CREATE CONSTRAINT ON (p:Person) ASSERT p.username IS UNIQUE;
CREATE CONSTRAINT ON (p:Person) ASSERT p.uuid IS UNIQUE;
CREATE CONSTRAINT ON (e:Person) ASSERT e.uuid IS UNIQUE;

match ()-[r]->() delete r;
match (n) delete n;

CREATE (p1:Person {
    uuid: 'cpollet-uuid',
    name: 'Christophe',
    username: 'cpollet',
    password: '1f540711!4b623a04ebbb8655f0c64b98bb268262fabd477dd876d94f97943661aac80d72',
    roles: 'user,admin',
    targetRatio: 1.0
})

CREATE (p2:Person {
    uuid: 'user-uuid',
    name: 'User',
    username: 'user',
    password: '1f540711!4b623a04ebbb8655f0c64b98bb268262fabd477dd876d94f97943661aac80d72',
    roles: 'user',
    targetRatio: 0.5
})

CREATE (e1:Event {
    uuid: 'event1-uuid',
    dateTime: '2018-12-15T19:00:00',
    timestamp: 1544900400,
    name: 'Event 1',
    attendeesCount: 1
})

CREATE (e2:Event {
    uuid: 'event2-uuid',
    dateTime: '2018-12-15T19:00:00',
    timestamp: 1544900400,
    name: 'Event 2',
    attendeesCount: 1
});

MATCH (p:Person)
MATCH (e:Event)
CREATE (p)-[r:IS_AVAILABLE]->(e);
