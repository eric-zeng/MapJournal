-- MapJournal Database Schemas v1.0

CREATE TABLE MapJournalTrips (
	_ID INTEGER PRIMARY KEY,
	Name TEXT,
	Description TEXT
)


CREATE TABLE MapJournalPoints (
	_ID INTEGER PRIMARY KEY,
	Title TEXT,
	TripId INTEGER,
	Latitude REAL,
	Longitude REAL,
	Altitude REAL,
	Time INTEGER,
	Address TEXT,
	Journal TEXT,
	FOREIGN KEY (Trip) REFERENCES MapJournalTrips(_ID)
)

CREATE TABLE MapJournalMedia (
	_ID INTEGER PRIMARY KEY,
	PointId INTEGER,
	Caption TEXT,
	Filepath TEXT,
	FOREIGN KEY (PointId) REFERENCES MapJournalPoints(_ID)
)
