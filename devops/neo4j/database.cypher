CREATE CONSTRAINT ON (p:Person) ASSERT p.username IS UNIQUE;
CREATE CONSTRAINT ON (p:Person) ASSERT p.uuid IS UNIQUE;
CREATE CONSTRAINT ON (e:Person) ASSERT e.uuid IS UNIQUE;

match ()-[r]->() delete r;
match (n) delete n;

CREATE (p1:Person {
    uuid: '4249085e-3f22-11e7-a919-92ebcb67fe33',
    name: 'Christophe',
    firstName: 'Christophe',
    lastName: 'LastName',
    email: 'email@example.com',
    username: 'cpollet',
    password: '1f540711!4b623a04ebbb8655f0c64b98bb268262fabd477dd876d94f97943661aac80d72',
    roles: 'user,admin',
    targetRatio: 1.0
})

CREATE (p2:Person {
    uuid: 'user2-uuid',
    name: 'Jonathan',
    firstName: 'Jonathan',
    lastName: 'LastName',
    email: 'email@example.com',
    username: 'user2',
    password: '1f540711!4b623a04ebbb8655f0c64b98bb268262fabd477dd876d94f97943661aac80d72',
    roles: 'user',
    targetRatio: 1.0
})

CREATE (p3:Person {
    uuid: 'user3-uuid',
    name: 'Daniela',
    firstName: 'Daniela',
    lastName: 'LastName',
    email: 'email@example.com',
    username: 'user3',
    password: '1f540711!4b623a04ebbb8655f0c64b98bb268262fabd477dd876d94f97943661aac80d72',
    roles: 'user',
    targetRatio: 1.0
})

CREATE (p4:Person {
    uuid: 'user4-uuid',
    name: 'David',
    firstName: 'David',
    lastName: 'LastName',
    email: 'email@example.com',
    username: 'user4',
    password: '1f540711!4b623a04ebbb8655f0c64b98bb268262fabd477dd876d94f97943661aac80d72',
    roles: 'user',
    targetRatio: 1.0
})

CREATE (p5:Person {
    uuid: 'user5-uuid',
    name: 'Valentina',
    firstName: 'Valentina',
    lastName: 'LastName',
    email: 'email@example.com',
    username: 'user5',
    password: '1f540711!4b623a04ebbb8655f0c64b98bb268262fabd477dd876d94f97943661aac80d72',
    roles: 'user',
    targetRatio: 1.0
})

CREATE (p6:Person {
    uuid: 'user6-uuid',
    name: 'Cyril',
    firstName: 'Cyril',
    lastName: 'LastName',
    email: 'email@example.com',
    username: 'user6',
    password: '1f540711!4b623a04ebbb8655f0c64b98bb268262fabd477dd876d94f97943661aac80d72',
    roles: 'user',
    targetRatio: 1.0
})

CREATE (p7:Person {
    uuid: 'user7-uuid',
    name: 'Raquel',
    firstName: 'Raquel',
    lastName: 'LastName',
    email: 'email@example.com',
    username: 'user7',
    password: '1f540711!4b623a04ebbb8655f0c64b98bb268262fabd477dd876d94f97943661aac80d72',
    roles: 'user',
    targetRatio: 1.0
})

CREATE (p8:Person {
    uuid: 'user8-uuid',
    name: 'Arnaud',
    firstName: 'Arnaud',
    lastName: 'LastName',
    email: 'email@example.com',
    username: 'user8',
    password: '1f540711!4b623a04ebbb8655f0c64b98bb268262fabd477dd876d94f97943661aac80d72',
    roles: 'user',
    targetRatio: 1.0
})

CREATE (p9:Person {
    uuid: 'user9-uuid',
    name: 'Amandine',
    firstName: 'Amandine',
    lastName: 'LastName',
    email: 'email@example.com',
    username: 'user9',
    password: '1f540711!4b623a04ebbb8655f0c64b98bb268262fabd477dd876d94f97943661aac80d72',
    roles: 'user',
    targetRatio: 1.0
})

CREATE (p10:Person {
    uuid: 'user10-uuid',
    name: 'Philippe',
    firstName: 'Philippe',
    lastName: 'LastName',
    email: 'email@example.com',
    username: 'user10',
    password: '1f540711!4b623a04ebbb8655f0c64b98bb268262fabd477dd876d94f97943661aac80d72',
    roles: 'user',
    targetRatio: 1.0
})

CREATE (p11:Person {
    uuid: 'user11-uuid',
    name: 'Thomas',
    firstName: 'Thomas',
    lastName: 'LastName',
    email: 'email@example.com',
    username: 'user11',
    password: '1f540711!4b623a04ebbb8655f0c64b98bb268262fabd477dd876d94f97943661aac80d72',
    roles: 'user',
    targetRatio: 1.0
})

CREATE (p12:Person {
    uuid: 'user12-uuid',
    name: 'Yves',
    firstName: 'Yves',
    lastName: 'LastName',
    email: 'email@example.com',
    username: 'user12',
    password: '1f540711!4b623a04ebbb8655f0c64b98bb268262fabd477dd876d94f97943661aac80d72',
    roles: 'user',
    targetRatio: 1.0
})

CREATE (p13:Person {
    uuid: 'user13-uuid',
    name: 'Lynn',
    firstName: 'Lynn',
    lastName: 'LastName',
    email: 'email@example.com',
    username: 'user13',
    password: '1f540711!4b623a04ebbb8655f0c64b98bb268262fabd477dd876d94f97943661aac80d72',
    roles: 'user',
    targetRatio: 1.0
})

CREATE (p14:Person {
    uuid: 'user14-uuid',
    name: 'Mateo',
    firstName: 'Mateo',
    lastName: 'LastName',
    email: 'email@example.com',
    username: 'user14',
    password: '1f540711!4b623a04ebbb8655f0c64b98bb268262fabd477dd876d94f97943661aac80d72',
    roles: 'user',
    targetRatio: 1.0
})

CREATE (p15:Person {
    uuid: 'user15-uuid',
    name: 'Sebastian',
    firstName: 'Sebastian',
    lastName: 'LastName',
    email: 'email@example.com',
    username: 'user15',
    password: '1f540711!4b623a04ebbb8655f0c64b98bb268262fabd477dd876d94f97943661aac80d72',
    roles: 'user',
    targetRatio: 1.0
})

CREATE (p16:Person {
    uuid: 'user16-uuid',
    name: 'Claudia',
    firstName: 'Claudia',
    lastName: 'LastName',
    email: 'email@example.com',
    username: 'user16',
    password: '1f540711!4b623a04ebbb8655f0c64b98bb268262fabd477dd876d94f97943661aac80d72',
    roles: 'user',
    targetRatio: 1.0
})

CREATE (e1:Event {
    uuid: 'event1-uuid',
    dateTime: '2018-05-05T19:00:00',
    timestamp: 1494003600,
    name: 'Notre Dame de Paris',
    attendeesCount: 4
})

CREATE (e2:Event {
    uuid: 'event2-uuid',
    dateTime: '2018-05-06T16:30:00',
    timestamp: 1494081000,
    name: 'Notre Dame de Paris',
    attendeesCount: 4
})

CREATE (e3:Event {
    uuid: 'event3-uuid',
    dateTime: '2018-05-06T19:00:00',
    timestamp: 1494093600,
    name: 'Notre Dame de Paris',
    attendeesCount: 4
})

CREATE (e4:Event {
    uuid: 'event4-uuid',
    dateTime: '2018-05-08T19:30:00',
    timestamp: 1494264600,
    name: 'Placebo',
    attendeesCount: 4
})

CREATE (e5:Event {
    uuid: 'event5-uuid',
    dateTime: '2018-05-10T18:30:00',
    timestamp: 1494433800,
    name: 'Harlem Globetrotters',
    attendeesCount: 4
})

CREATE (e6:Event {
    uuid: 'event6-uuid',
    dateTime: '2018-05-20T19:30:00',
    timestamp: 1495301400,
    name: 'Deep Purple',
    attendeesCount: 4
})

CREATE (e7:Event {
    uuid: 'event7-uuid',
    dateTime: '2018-05-24T19:30:00',
    timestamp: 1495647000,
    name: 'M Pokora',
    attendeesCount: 4
})

CREATE (p7)-[:IS_AVAILABLE]->(e1)
CREATE (p7)-[:IS_AVAILABLE]->(e2)
CREATE (p7)-[:IS_AVAILABLE]->(e3)
CREATE (p7)-[:IS_AVAILABLE]->(e4)
CREATE (p7)-[:IS_AVAILABLE]->(e5)
CREATE (p7)-[:IS_AVAILABLE]->(e6)
CREATE (p7)-[:IS_AVAILABLE]->(e7)

CREATE (p3)-[:IS_AVAILABLE]->(e1)
CREATE (p3)-[:IS_AVAILABLE]->(e2)

CREATE (p3)-[:IS_AVAILABLE]->(e4)
CREATE (p3)-[:IS_AVAILABLE]->(e5)
CREATE (p3)-[:IS_AVAILABLE]->(e6)
CREATE (p3)-[:IS_AVAILABLE]->(e7)

CREATE (p10)-[:IS_AVAILABLE]->(e1)
CREATE (p10)-[:IS_AVAILABLE]->(e2)
CREATE (p10)-[:IS_AVAILABLE]->(e3)
CREATE (p10)-[:IS_AVAILABLE]->(e4)
CREATE (p10)-[:IS_AVAILABLE]->(e5)
CREATE (p10)-[:IS_AVAILABLE]->(e6)
CREATE (p10)-[:IS_AVAILABLE]->(e7)

CREATE (p5)-[:IS_AVAILABLE]->(e2)
CREATE (p5)-[:IS_AVAILABLE]->(e3)
CREATE (p5)-[:IS_AVAILABLE]->(e4)
CREATE (p5)-[:IS_AVAILABLE]->(e7)

CREATE (p9)-[:IS_AVAILABLE]->(e1)
CREATE (p9)-[:IS_AVAILABLE]->(e2)
CREATE (p9)-[:IS_AVAILABLE]->(e3)

CREATE (p8)-[:IS_AVAILABLE]->(e1)
CREATE (p8)-[:IS_AVAILABLE]->(e2)
CREATE (p8)-[:IS_AVAILABLE]->(e3)
CREATE (p8)-[:IS_AVAILABLE]->(e7)

CREATE (p15)-[:IS_AVAILABLE]->(e7)

CREATE (p2)-[:IS_AVAILABLE]->(e1)
CREATE (p2)-[:IS_AVAILABLE]->(e7)

CREATE (p4)-[:IS_AVAILABLE]->(e2)
CREATE (p4)-[:IS_AVAILABLE]->(e3)
CREATE (p4)-[:IS_AVAILABLE]->(e6)

CREATE (p16)-[:IS_AVAILABLE]->(e2)
CREATE (p16)-[:IS_AVAILABLE]->(e3)
CREATE (p16)-[:IS_AVAILABLE]->(e6)

CREATE (p12)-[:IS_AVAILABLE]->(e4)
CREATE (p12)-[:IS_AVAILABLE]->(e5)
CREATE (p12)-[:IS_AVAILABLE]->(e6)
CREATE (p12)-[:IS_AVAILABLE]->(e7)

CREATE (p14)-[:IS_AVAILABLE]->(e1)
CREATE (p14)-[:IS_AVAILABLE]->(e3)
CREATE (p14)-[:IS_AVAILABLE]->(e4)
CREATE (p14)-[:IS_AVAILABLE]->(e6)
;
