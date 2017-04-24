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
    uuid: 'user2-uuid',
    name: 'Jonathan',
    username: 'user2',
    password: '1f540711!4b623a04ebbb8655f0c64b98bb268262fabd477dd876d94f97943661aac80d72',
    roles: 'user',
    targetRatio: 1.0
})

CREATE (p3:Person {
    uuid: 'user3-uuid',
    name: 'Daniela',
    username: 'user3',
    password: '1f540711!4b623a04ebbb8655f0c64b98bb268262fabd477dd876d94f97943661aac80d72',
    roles: 'user',
    targetRatio: 1.0
})

CREATE (p4:Person {
    uuid: 'user4-uuid',
    name: 'David',
    username: 'user4',
    password: '1f540711!4b623a04ebbb8655f0c64b98bb268262fabd477dd876d94f97943661aac80d72',
    roles: 'user',
    targetRatio: 1.0
})

CREATE (p5:Person {
    uuid: 'user5-uuid',
    name: 'Valentina',
    username: 'user5',
    password: '1f540711!4b623a04ebbb8655f0c64b98bb268262fabd477dd876d94f97943661aac80d72',
    roles: 'user',
    targetRatio: 1.0
})

CREATE (p6:Person {
    uuid: 'user6-uuid',
    name: 'Cyril',
    username: 'user6',
    password: '1f540711!4b623a04ebbb8655f0c64b98bb268262fabd477dd876d94f97943661aac80d72',
    roles: 'user',
    targetRatio: 1.0
})

CREATE (p7:Person {
    uuid: 'user7-uuid',
    name: 'Raquel',
    username: 'user7',
    password: '1f540711!4b623a04ebbb8655f0c64b98bb268262fabd477dd876d94f97943661aac80d72',
    roles: 'user',
    targetRatio: 1.0
})

CREATE (p8:Person {
    uuid: 'user8-uuid',
    name: 'Arnaud',
    username: 'user8',
    password: '1f540711!4b623a04ebbb8655f0c64b98bb268262fabd477dd876d94f97943661aac80d72',
    roles: 'user',
    targetRatio: 1.0
})

CREATE (p9:Person {
    uuid: 'user9-uuid',
    name: 'Amandine',
    username: 'user9',
    password: '1f540711!4b623a04ebbb8655f0c64b98bb268262fabd477dd876d94f97943661aac80d72',
    roles: 'user',
    targetRatio: 1.0
})

CREATE (p10:Person {
    uuid: 'user10-uuid',
    name: 'Philippe',
    username: 'user10',
    password: '1f540711!4b623a04ebbb8655f0c64b98bb268262fabd477dd876d94f97943661aac80d72',
    roles: 'user',
    targetRatio: 1.0
})

CREATE (p11:Person {
    uuid: 'user11-uuid',
    name: 'Thomas',
    username: 'user11',
    password: '1f540711!4b623a04ebbb8655f0c64b98bb268262fabd477dd876d94f97943661aac80d72',
    roles: 'user',
    targetRatio: 1.0
})

CREATE (p12:Person {
    uuid: 'user12-uuid',
    name: 'Yves',
    username: 'user12',
    password: '1f540711!4b623a04ebbb8655f0c64b98bb268262fabd477dd876d94f97943661aac80d72',
    roles: 'user',
    targetRatio: 1.0
})

CREATE (p13:Person {
    uuid: 'user13-uuid',
    name: 'Lynn',
    username: 'user13',
    password: '1f540711!4b623a04ebbb8655f0c64b98bb268262fabd477dd876d94f97943661aac80d72',
    roles: 'user',
    targetRatio: 1.0
})

CREATE (p14:Person {
    uuid: 'user14-uuid',
    name: 'Mateo',
    username: 'user14',
    password: '1f540711!4b623a04ebbb8655f0c64b98bb268262fabd477dd876d94f97943661aac80d72',
    roles: 'user',
    targetRatio: 1.0
})

CREATE (e1:Event {
    uuid: 'event1-uuid',
    dateTime: '2017-04-21T19:00:00',
    timestamp: 1492794000,
    name: 'Les Trois Mousquetaires',
    attendeesCount: 4
})

CREATE (e2:Event {
    uuid: 'event2-uuid',
    dateTime: '2017-04-22T14:00:00',
    timestamp: 1492776000,
    name: 'Les Trois Mousquetaires',
    attendeesCount: 4
})

CREATE (e3:Event {
    uuid: 'event3-uuid',
    dateTime: '2017-04-22T19:00:00',
    timestamp: 1492880400,
    name: 'Les Trois Mousquetaires',
    attendeesCount: 4
})

CREATE (e4:Event {
    uuid: 'event4-uuid',
    dateTime: '2017-04-26T18:00:00',
    timestamp: 1493222400,
    name: 'Julien DORÉ',
    attendeesCount: 4
})

CREATE (e5:Event {
    uuid: 'event5-uuid',
    dateTime: '2017-04-28T18:30:00',
    timestamp: 1493397000,
    name: 'Véronic DICAIRE',
    attendeesCount: 4
})

CREATE (e6:Event {
    uuid: 'event6-uuid',
    dateTime: '2017-04-29T18:30:00',
    timestamp: 1493483400,
    name: 'GOSPEL pour 100 Voix - World tour 2017',
    attendeesCount: 4
})

CREATE (e7:Event {
    uuid: 'event7-uuid',
    dateTime: '2017-04-30T19:00:00',
    timestamp: 1493571600,
    name: 'MESSMER',
    attendeesCount: 4
})

CREATE (p2)-[:IS_AVAILABLE]->(e1)
CREATE (p2)-[:IS_AVAILABLE]->(e5)

CREATE (p3)-[:IS_AVAILABLE]->(e1)
CREATE (p3)-[:IS_AVAILABLE]->(e2)
CREATE (p3)-[:IS_AVAILABLE]->(e3)
CREATE (p3)-[:IS_AVAILABLE]->(e4)
CREATE (p3)-[:IS_AVAILABLE]->(e5)
CREATE (p3)-[:IS_AVAILABLE]->(e6)
CREATE (p3)-[:IS_AVAILABLE]->(e7)

CREATE (p4)-[:IS_AVAILABLE]->(e1)
CREATE (p4)-[:IS_AVAILABLE]->(e2)
CREATE (p4)-[:IS_AVAILABLE]->(e3)
CREATE (p4)-[:IS_AVAILABLE]->(e7)

CREATE (p5)-[:IS_AVAILABLE]->(e1)
CREATE (p5)-[:IS_AVAILABLE]->(e2)
CREATE (p5)-[:IS_AVAILABLE]->(e3)
CREATE (p5)-[:IS_AVAILABLE]->(e6)
CREATE (p5)-[:IS_AVAILABLE]->(e7)

CREATE (p6)-[:IS_AVAILABLE]->(e5)
CREATE (p6)-[:IS_AVAILABLE]->(e6)

CREATE (p7)-[:IS_AVAILABLE]->(e1)
CREATE (p7)-[:IS_AVAILABLE]->(e2)
CREATE (p7)-[:IS_AVAILABLE]->(e3)
CREATE (p7)-[:IS_AVAILABLE]->(e4)
CREATE (p7)-[:IS_AVAILABLE]->(e5)
CREATE (p7)-[:IS_AVAILABLE]->(e6)
CREATE (p7)-[:IS_AVAILABLE]->(e7)

CREATE (p8)-[:IS_AVAILABLE]->(e2)
CREATE (p8)-[:IS_AVAILABLE]->(e3)

CREATE (p9)-[:IS_AVAILABLE]->(e1)
CREATE (p9)-[:IS_AVAILABLE]->(e2)
CREATE (p9)-[:IS_AVAILABLE]->(e3)
CREATE (p9)-[:IS_AVAILABLE]->(e4)
CREATE (p9)-[:IS_AVAILABLE]->(e6)
CREATE (p9)-[:IS_AVAILABLE]->(e7)

CREATE (p10)-[:IS_AVAILABLE]->(e1)
CREATE (p10)-[:IS_AVAILABLE]->(e2)
CREATE (p10)-[:IS_AVAILABLE]->(e3)
CREATE (p10)-[:IS_AVAILABLE]->(e4)
CREATE (p10)-[:IS_AVAILABLE]->(e5)
CREATE (p10)-[:IS_AVAILABLE]->(e6)
CREATE (p10)-[:IS_AVAILABLE]->(e7)

CREATE (p11)-[:IS_AVAILABLE]->(e4)

CREATE (p12)-[:IS_AVAILABLE]->(e1)
CREATE (p12)-[:IS_AVAILABLE]->(e2)
CREATE (p12)-[:IS_AVAILABLE]->(e3)
CREATE (p12)-[:IS_AVAILABLE]->(e4)
CREATE (p12)-[:IS_AVAILABLE]->(e5)
CREATE (p12)-[:IS_AVAILABLE]->(e7)

CREATE (p14)-[:IS_AVAILABLE]->(e1)
CREATE (p14)-[:IS_AVAILABLE]->(e2)
CREATE (p14)-[:IS_AVAILABLE]->(e3)
CREATE (p14)-[:IS_AVAILABLE]->(e7);
