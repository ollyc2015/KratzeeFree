# Kratzee

Team-based Learning (TBL) is a flipped classroom strategy that is growing in popularity in higher education. 

An implementation of TBL involves students taking an in-class test, first as individuals and then again in their team. 

Teams find out the correct answer by discussion and using a scratch-card. However, the assessment of the answers and their documentation is an onerous activity.  A potential solution to this administrative burden would be the development of an electronic scratch-card system aiding the assessment process. 

I have therefore created an a comprehensive system to help facility the delivery of a TBL session.

This was originally my final year project during my undergraduate Computing degree, however, I have since evolved the system architecture and aimed to add more features.

Fig 3 and 4 demonstrate the different UI for both quiz types. The main detail to note, is that the users will be able to find a star under the correct answer during the team quiz and keep track of their points. 

During an independent quiz, the star and the points are not shown to force the users into a discussion during the team-based quiz. The same set of questions should be taken during both the individual and team-based quizzes. A session is deemed complete once both quizzes have been finished.

![alt text](http://www.oliverbcurtis.co.uk/images/kratzee/kratzeeApp1.png) 

I have created a paid version of the app (yet to be released) that integrates with a web-portal, allowing detailed assessment of the collected data. An administrator can review the collected data, as well as other administrative functions. UX (user experience) research discovered that adding a custom feature where team-members were tagged as the same colour as the team colour, created a better UX. This is displayed in the image below.

![alt text](http://www.oliverbcurtis.co.uk/images/kratzee/kratzeeAdmin.png) 

# Features (Free version)

* Set your own Trivia based quizzes. 
* Scratch-card based submissions.
* Remove All Questions/Participant Data, Edit and Create Question Sets, via the app.
* The ability to take individual and team-based quizzes.
* Access to set questions are accessed through a PIN given by the owner of the question-set.


# Features (Paid version)

* Trivia and Assessment based versions of the application. The main difference between a Trivia Quiz and an Assessment Quiz is that in the web-portal, an administrator can create the student profiles and create teams. The system will then assign a password which can be emailed to students through the web-portal.
* Scratch-card based submissions.
* Data from the application is used to power a website, displaying all collected information.
* The ability to take individual and team-based quizzes.
* Access to set questions are accessed through a PIN given by the owner of the question-set.
* Authorised users can access a website where adminstration activites can be carried out.


# Design

* Adheres to the MVP architecture pattern.
* Retrofit2
* Dagger2
* SQLite

# Use

Feel free to download this lite version here

# Download

The version stored here is a lite version with access to the Trivia based version only.

