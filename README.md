# Hogwarts Admin REST API

Welcome to the Hogwarts Admin REST API! This API allows you to manage various aspects of Hogwarts School of Witchcraft and Wizardry, including students, courses, houses, and teachers.

## Deployment

The API is deployed at [https://hogwarts-rest.azurewebsites.net/](https://hogwarts-rest.azurewebsites.net/)

## Resources

### Students

The `/students` resource allows you to perform CRUD operations on students.

#### Endpoints

- `GET /students`: Get a list of all students.
- `GET /students/{id}`: Get details of a specific student by ID.
- `POST /students`: Create a new student.
- `PUT /students/{id}`: Update information of a specific student by ID.
- `DELETE /students/{id}`: Delete a student by ID.
- `PATCH /students/{id}`: Partially update information of a specific student by ID.

### Teachers

The `/teachers` resource allows you to manage teachers, including assigning them as heads of houses and updating employment status.

#### Endpoints

- `GET /teachers`: Get a list of all teachers.
- `GET /teachers/{id}`: Get details of a specific teacher by ID.
- `POST /teachers`: Create a new teacher.
- `PUT /teachers/{id}`: Update information of a specific teacher by ID.
- `DELETE /teachers/{id}`: Delete a teacher by ID.
- `PATCH /teachers/{id}/patchTeacherHeadOfHouse`: Assign a teacher as the head of a house.
- `PATCH /teachers/{id}/patchTeacherEmploymentEnd`: Mark the end of employment for a teacher.
- `PATCH /teachers/{id}/patchTeacherEmployment`: Update employment details of a teacher.

### Courses

The `/courses` resource allows you to manage courses, including assigning teachers and enrolling students.

#### Endpoints

- `GET /courses`: Get a list of all courses.
- `GET /courses/{id}`: Get details of a specific course by ID.
- `GET /courses/{id}/teacher`: Get the teacher assigned to a specific course.
- `GET /courses/{id}/students`: Get a list of students enrolled in a specific course.
- `POST /courses`: Create a new course.
- `POST /courses/{id}/students`: Enroll a student in a specific course.
- `PUT /courses/{id}`: Update information of a specific course by ID.
- `PUT /courses/{courseId}/students/{studentId}`: Update enrollment details of a student in a specific course.
- `DELETE /courses/{id}`: Delete a course by ID.
- `DELETE /courses/{id}/teacher`: Remove the assigned teacher from a specific course.
- `DELETE /courses/{courseId}/students/{studentId}`: Remove a student from a specific course.
- `PUT /courses/{id}/teacher`: Assign a teacher to a specific course.

### House

The `/houses` resource allows you to retrieve information about Hogwarts houses.

#### Endpoints

- `GET /houses`: Get information about all Hogwarts houses.
- `GET /houses/{name}`: Get details of a specific Hogwarts house by name.
