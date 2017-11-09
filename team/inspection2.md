# Team *T29* - Inspection *2*
 
Inspection | Details
----- | -----
Subject | *Review Location.java (Lines 0-126)*
Meeting | *11/08, 10:00 AM, Aylesworth 111*
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
Location.java:7-8 | Deprecated variables latitude and longitude. | l | #248 | Elliott
Location.java:17-18 | Calls to deprecated methods. | l | #249 | Trey
Location.java:21 | Remove unnecessary setter method. | l | #250 | Elliott
Location.java:22 | Remove unnecessary setter method. | l | #250 | Tim
Location.java:23 | Remove unnecessary setter method. | l | #250 | Matt
Location.java:35 | Remove unnecessary getter method. | l | #250 | Trey
Location.java:36 | Method needs better documentation. | l | #251 | Elliott
Location.java:37 | Rename method to be more readable. | l | #251 | Elliott
