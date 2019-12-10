
<p align="center">
  <img src="https://i.imgur.com/TJikbiA.jpg">
</p>


# High School Timetable Problem using Hill Climbing
A hill climbing implementation for solving the high school timetable problem. The program generates a random initial state an calculates a rating based on a heuristic function. Each constraint evaluates the state and produces a score. The sum of all equals the rating of the state. Then the program attempts to solve one of these constraints at a time. Each constraint has a specialized way resolving itself if possible to reduce the steps needed for optimizing the solution.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See testing for notes on how to test the project.

## Testing

The program is shipped with a default dataset. The dataset is based on a real high school schedule.

### Altering the Lessons dataset

The total hours for every class must not exceed 35 hours. After removing or adding a lesson, the lesson ids must be adjusted so that the first lesson has an id of 0 and the numbering must be continous. This is important because the id is used for indexing. Also no hours must be negative.

### Altering the Teachers dataset

After removing or adding a teacher, the teacher ids must be adjusted so that the first teacher has an id of 0 and the numbering must be continous. The lesson ids must all exist in the Lessons dataset otherwise the dehaviour of the program is undefined. The weekly hours must be less or equal to the product of the daily hours times five. However the daily hours can be any number greater than 0. This constraint is logically derived by the fact that is impossible to reach the weekly hours even if working every day the maximum hours.


## Collaborators
[Xanthos Xanthopoulos](https://github.com/XanthosXanthopoulos)

[Jenny Bolena](https://github.com/jennybolena)

[Konstantinos Nikoloutsos](https://github.com/Nikoloutsos)
