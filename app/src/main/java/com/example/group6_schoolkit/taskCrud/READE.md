1 Create TaskModel.java - this is a model for the tasks
2 Created HomeTaskCrud activity and layout - this will be the starting point of the task function
3 Updated the manifest file to point the launcher to HomeTaskCrud activity (temporariliy- will bring this back to MainActivity later)
4 Created AllTAsksACtivity and layout - this will view all the tasks
5 Created list_task_item.xml - this will be used for the recycler view in the AllTaskActivity
6 Created CustomAdapater to be used for the Recylcer view
7 Updated all task activity to use the custom adapter and placed some dummy data using array list for now

8 Created TaskActivity where user  will be routed to a task from the activity_all_tasks.xml
9 Created a UTILS class , making this class singleton, it will be 1 instance of this class for the entire application
10 Updated activity_home_task_crud.xml to add a button for adding a task
11 Created AddTaskActivity and Layout to add task
12 Updated AddTaskActivity to get inputs from EditText when button is clicked
