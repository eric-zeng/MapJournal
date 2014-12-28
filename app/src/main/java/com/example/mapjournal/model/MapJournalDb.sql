--  Copyright 2014 Eric Zeng
--  
--  Licensed under the Apache License, Version 2.0 (the "License");
--  you may not use this file except in compliance with the License.
--  You may obtain a copy of the License at
-- 
--      http://www.apache.org/licenses/LICENSE-2.0
-- 
--  Unless required by applicable law or agreed to in writing, software
--  distributed under the License is distributed on an "AS IS" BASIS,
--  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
--  See the License for the specific language governing permissions and 
--  limitations under the License.
 

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
