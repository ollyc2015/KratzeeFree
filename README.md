# Kratzee

Team-based Learning (TBL) is a flipped classroom strategy that is growing in popularity in higher education. 

An implementation of TBL involves students taking an in-class test, first as individuals and then again in their team. 

Teams find out the correct answer by discussion and using a scratch-card. However, the assessment of the answers and their documentation is an onerous activity.  A potential solution to this administrative burden would be the development of an electronic scratch-card system aiding the assessment process. 

I have therefore created an a comprehensive system to help facility the delivery of a TBL session.

This was originally my final year project during my undergraduate Computing degree, however, I have since evolved the system architecture and aimed to add more features.

Fig 3 and 4 demonstrate the different UI for both quiz types. The main detail to note, is that the users will be able to find a star under the correct answer during the team quiz and keep track of their points. 

During an independent quiz, the star and the points are not shown to force the users into a discussion during the team-based quiz. The same set of questions should be taken during both the individual and team-based quizzes. A session is deemed complete once both quizzes have been finished.

![alt text](http://www.oliverbcurtis.co.uk/images/kratzee/kratzeeApp1.png) 

An administrator can then review the collected data, as well as other administrative functions. UX (user experience) research discovered that adding a custom feature where team-members were tagged as the same colour as the team colour, created a better UX. This is displayed in the image below.

![alt text](http://www.oliverbcurtis.co.uk/images/kratzee/kratzeeAdmin.png) 


# Features

* Trivia and Assessment based versions of the application
* Scratch-card based submissions
* Data from the application is used to power a website, displaying all collected information
* The ability to take individual and team-based quizzes
* Access to set questions are accessed through a PIN given by the owner of the question-set
* Authorised users can access a website where adminstration activites can be carried out


# Design

* Adheres to the MVP architecture pattern.
* Retrofit2
* Dagger2
* SQLite

# Use

Feel free to download this lite version, access to the demo questions can be gained by using the PIN: fb761407 
Access to the web-portal can be found at www.oliverbcurtis.co.uk/quizCharts 
Login: demo@kratzee.co.uk
Password: mypassword

# Download

The version stored here is a lite version with access to the Trivia based version only. For access to the web portal, please request access - the app/web portal is not currently available on the app store.

