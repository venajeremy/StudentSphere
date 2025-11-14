# 🎓 StudentSphere
**Course:** CS 151 – Object-Oriented Design  
**Instructor:** Prof. Ahmad Yazdankhah  
**Team 26**

---

##  Overview
**StudentSphere** is a desktop application that serves as a *faculty knowledgebase for student information*.  
It allows university faculty to create, edit, search, and manage detailed student profiles, including academic standing, technical skills, professional roles, and faculty evaluations.  
The goal is to give professors an organized, searchable way to track student growth and provide informed recommendation letters, mentorship, and career guidance.

This project follows the **MVC (Model-View-Controller)** design pattern and demonstrates core OOD principles such as abstraction, encapsulation, modularity, and reusability.  
All data is stored locally using flat-file (CSV) storage as specified in the technical spec.

---

---

## 👥 Team Members
- **Samriddhi Matharu** – Data Science Major
- **Jeremy Greatorex** – Software Engineering Major
- **Ivan Rivera** – Computer Science Major

---
## 🧩 Who Did What 11/15/25

| Member | Role                                               | other |
|---------|----------------------------------------------------|------------------|
| **Jeremy Greatorex** | Implemented add comment feature                    |
| **Samriddhi Matharu** |    Refactored the CreateStudentPage by removing the input initial student comment section, and implemented delete-comment functionality within CommentCatalog. Ensured the backend is fully prepared for integrating comment deletion in the StudentCommentsPage UI.    |
| **Ivan Rivera** | Implemented comment page and view student comments |


---
## 🧩 Who Did What 11/1/25

| Member | Role                                                                                                       | other |
|---------|------------------------------------------------------------------------------------------------------------|------------------|
| **Jeremy Greatorex** | Implemented edit student page. Fixed delete student glitch. Implemented update method for student catalog. |
| **Samriddhi Matharu** |    Implemented an Edit button on the Generate Report page (next to Delete) to let users easily modify student records. Updated the Current Job column to show blank values when the job status is “Unemployed.” Ensured both Edit and Delete buttons stay disabled until a row is selected.                                                                                                           |
| **Ivan Rivera** |                                                                                                            |


---
## 🧩 Who Did What 10/25/25

| Member | Role                                                                                                                                                                       | other |
|---------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------|
| **Jeremy Greatorex** | Modified comment system to support multiple individual comments stored in seperate file. Reviewed student search implementation. Implemented delete student functionality. |
| **Samriddhi Matharu** |    pulled and compiled the code to do some user testing  on features implemented                                                                                                                                                        |
| **Ivan Rivera** |                                                                                                                                                                            |


---
## 🧩 Who Did What 10/18/25

| Member | Role                                                                                                                   | other |
|---------|------------------------------------------------------------------------------------------------------------------------|------------------|
| **Jeremy Greatorex** | Created define student page, created student class, created student catalog, handled student object to csv conversion. |
| **Samriddhi Matharu** |  Implemented the ViewStudentsPage feature, which displays all saved student profiles in a JavaFX TableView. Integrated it into the navigation flow by connecting the Generate Report button to this new page and verified data loading from the CSV file (including entires with null programming langauges and databases); Also ensured it was sorted in alphabetical order of Students name                                                                                                                      |
| **Ivan Rivera** | Implemented auto refresh capability for generate report functionality                                                  |


---
## 🧩 Who Did What 10/11/25

| Member | Role                                                                                                                                                | other |
|---------|-----------------------------------------------------------------------------------------------------------------------------------------------------|------------------|
| **Jeremy Greatorex** | Updated programming language table type, Created catalog system with save file detection & creation, Added define language updates to language file, Added language sorting |
| **Samriddhi Matharu** |   implemented back button to homepage                                                                                                                                                   |
| **Ivan Rivera** |   Implement edit and delete functionality for Define Language page                                                                                                                                                   |

---

##  Functional Highlights

- **Student Profile Management**
  - Create, edit, and delete student records.
  - Store information such as name, year, job status, experience, databases known, and preferred professional role.

- **Programming Language Definition**
  - Faculty can define and save programming languages available for student selection.
  - Duplicate and empty entries are automatically validated.

- **Faculty Evaluation**
  - Add comments or journal entries with auto-stamped dates.
  - Flag students for future services: *Whitelist* (recommended) or *Blacklist* (not eligible).

- **Reporting**
  - Generate lists of blacklisted or whitelisted students.
  - Filter students by skills or profile parameters.
  - View complete individual student reports.

---

##  Technical Design

- **Architecture:** MVC pattern
- **Programming Language:** Java 17+
- **Storage:** Flat files (CSV)
- **Key Files:**
  - `Students.csv`
  - `ProgrammingLanguages.csv`
  - `StudentSkills.csv`
  - `FacultyEvaluations.csv`
- **GUI Framework:** JavaFX / Swing (depending on implementation)
- **Supported OS:** Windows, macOS, Linux

---

## 🚀 Running the Application

1. **Clone the repository**
   ```bash
   git clone https://github.com/<your-username>/StudentSphere.git
   cd StudentSphere
