# Edith User Guide

Edith is a Command-line chatbot to help users manage their task efficiently.
It helps keeping track of Todo, Deadline and Event tasks and supports persistent storage,
where the tasks are automatically saved and loaded when the user starts the program.

## Quick Start
1. Ensure Java is installed (JDK 17).
2. Download the latest release [here](https://github.com/yueyang1111/ip/releases).
3. Open a terminal and navigate to the jar location.
4. Run the program with:
   ```
   java -jar ip.jar
   ```
5. Start interacting with Edith.
6. Look at [Feature](#features) for the commands supported.

## Features

### Add Todo

Adds a simple todo task.

Command:
```
todo DESCRIPTION
```

Example:
```
todo read book
```

Expected Output:
```
___________________________________
Got it. I've added this task:
[T][ ] read book
Now you have 1 task in the list
___________________________________
```

### Add Deadline

Adds a deadline task that must be completed before a specific date/time.
Supported date formats are described in [Date Format](#date-format).

Command:
```
deadline DESCRIPTION /by DATE
```

Example:
```
deadline return book /by 6/3/2026 1800
```

Expected Output:
```
___________________________________
Got it. I've added this task:
[D][ ] return book (by: Mar 6 2026, 6:00pm)
Now you have 2 tasks in the list
___________________________________
```


### Add Event

Adds a deadline task that happens during a specific time period.
Supported date formats are described in [Date Format](#date-format).

Command:
```
event DESCRIPTION /from DATE /to DATE
```

Example:
```
event join meeting /from 6/3/2026 1800 /to 6/3/2026 2000
```

Expected Output:
```
___________________________________
Got it. I've added this task:
[E][ ] join meeting (from: Mar 6 2026, 6:00pm to: Mar 6 2026, 8:00pm)
Now you have 3 tasks in the list
___________________________________
```

### List Task

List all the task in the current list.

Command:
```
list
```

Expected Output:
```
___________________________________
Here are the tasks in your list:
1.[T][ ] read book
2.[D][ ] return book (by: Mar 6 2026, 6:00pm)
3.[E][ ] join meeting (from: Mar 6 2026, 6:00pm to: Mar 6 2026, 8:00pm)
___________________________________
```

### Mark Task

Marks a task as completed.

Command:
```
mark INDEX
```

Example:
```
mark 1
```

Expected Output:
```
___________________________________
Nice! I've marked this task as done:
[T][X] read book
___________________________________
```

### Unmark Task

Marks a task as not completed.

Command:
```
unmark INDEX
```

Example:
```
unmark 1
```

Expected Output:
```
___________________________________
OK, I've marked this task as not done yet:
[T][ ] read book
___________________________________
```

### Delete Task

Deletes a task from task list.

Command:
```
delete INDEX
```

Example:
```
delete 1
```

Expected Output:
```
___________________________________
Noted. I've removed this task:
[T][ ] read book
Now you have 2 tasks in the list
___________________________________
```

### Find Task

Find tasks that matches a keyword.

Command:
```
find KEYWORD
```

Example:
```
find book
```

Expected Output:
```
___________________________________
Here are the matching tasks in your list:
1.[D][ ] return book (by: Mar 6 2026, 6:00pm)
___________________________________
```

### Exit Program

Exits the program.

Command:
```
bye
```

Expected Output:
```
___________________________________
Bye. Hope to see you again soon!
___________________________________
```

## Date Format

Edith supports multiple input date formats.

### Supported Input Formats

Dates and times can be entered in the following formats:

Date and Time:
- `d/M/yyyy HHmm`  (e.g., `6/3/2026 1800`)
- `d-M-yyyy HHmm`  (e.g., `6-3-2026 1800`)
- `yyyy-M-d HHmm`  (e.g., `2026-3-6 1800`)

Date Only:
- `d/M/yyyy`  (e.g., `6/3/2026`)
- `d-M-yyyy`  (e.g., `6-3-2026`)
- `yyyy-M-d`  (e.g., `2026-3-6`)

Time Only:
- `HHmm`  (e.g., `1800`)

### Output Format

All dates and times are displayed in the following format:
- `MMM d yyyy, h:mma` (e.g., Mar 6 2026, 6:00pm)

## Command Summary

| Command      | Format                                  | Example                                               |
|--------------|-----------------------------------------|-------------------------------------------------------|
| Add Todo     | `todo DESCRIPTION`                      | `todo read book`                                      |
| Add Deadline | `deadline DESCRIPTION /by DATE`         | `deadline return book /by 6/3/2026 1800`              |
| Add Event    | `event DESCRIPTION /from DATE /to DATE` | `event meeting /from 6/3/2026 1800 /to 6/3/2026 2000` |
| List Tasks   | `list`                                  | `list`                                                |
| Mark Task    | `mark INDEX`                            | `mark 1`                                              |
| Unmark Task  | `unmark INDEX`                          | `unmark 1`                                            |
| Delete Task  | `delete INDEX`                          | `delete 1`                                            |
| Find Task    | `find KEYWORD`                          | `find book`                                           |
| Exit Program | `bye`                                   | `bye`                                                 |