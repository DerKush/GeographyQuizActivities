# Geography Quiz with Activities

In this application, we improve the design of our Geography Quiz.

The application now presents three activities:
- HomeActivity: this is the launcher activity
- PlayActivity: this is the game interface
- AddQuestionActivity: this is where a user can add a new pair (country, capital)

In order to have a playable game at first launch, the PlayActivity actually reads data from a CSV file
and stores it into a SharedPreferences object. The game is then played following the same flow as the previous application.
The only important change is that we now retrieve the data from the SharedPreferences.

The AddQuestionActivity simply consists in two EditTexts and a Button. If the inputs are not empty,
we write a new pair (country, capital) in the SharedPreferences. This allows the user to create a sort of 
'database' of questions that can grow over time.
