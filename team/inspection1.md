# Team *T29* - Inspection *1*
 
Inspection | Details
----- | -----
Subject | *Review Dynamic Programming of DistanceCalculator.java (Lines 0-276)*
Meeting | *11/01, 10:00 AM, Aylesworth 111*
Checklist | *Java Inspection Checklist (Fox): http://www.cs.toronto.edu/~sme/CSC444F/handouts/java_checklist.pdf*

### Roles
Name | Role | Preparation Time
---- | ---- | ----
 Trey Yu | Developer/Moderator | 1-2 hours
 Matt Erle | Developer/Moderator | 1 hour
 Tim Stroup | Devloper/Moderator | 1-2 hours
 Elliott Roberts | Developer/Moderator | 1 hour
### Log
file:line | defect | h/m/l | github# | who
--- | --- |:---:|:---:| ---
DistanceCalculator.java:104 | Uses a LinkedList to access location variables which is an O(n) search. Use an arraylist | h | #226 | Tim 
DistanceCalculator.java:235-238 | Recalculates distances between nodes with a LinkedList resulting in 8 O(n) searches inside of a O(n^2) loop | h | #226 | Tim
DistanceCalculator.java:260 | Uses a LinkedList to access location variables which is an O(n) search and recomputes the distances. There should be a 2D int array lookup for this task | h | #226 | Tim
DistanceCalculator.java:112 | Unclear why comparing itinerary and locations size | m | #227 | Tim
DistanceCalculator.java:9-10 | Variables named similarly, one is not even used | l | #224 | Tim
DistanceCalculator.java:15 | 2D int array is not used | l | #225 | Tim
DistanceCalculator.java:193 | Accesses a "key" variable with using the get method | l | #228 | Tim
DistanceCalculator.java:228 | Unclear on the purpose of this method, comment is not descriptive enough | l | #229 | Tim
