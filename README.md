# Artemis

Artemis is a free and open source Reddit app made for Android. It was heavily inspired by the iOS
app "Apollo" and focuses on a simple design with the main dark theme being optimized for AMOLED
screens.

Artemis is currently only a few days old at the time of writing this README, it currently can only
display the `/hot` timeline from Reddit and can only display comments for posts you click on. You
cannot login yet or do anything related to a logged in account.

The goals for the next few days are:

 - Implement the ability to log in
 - Implement a custom navigation system based on gestures to move back and forth through fragments
   (or through the history of fragments that the user has opened) the goal is to create navigation
   as similar to iOS as possible
 - Implement auto-playing videos/GIFs
 - Add the ability to upvote, downvote, and comment
 - Implement a markdown renderer (we could use the `_html` versions of all fields or convert the
   markdown to HTML ourselves, I'm not sure what I will do at this moment)

## Screenshots

As I said, it is only a few days old but here is the current state of the application:

Timeline:

![Timeline Screenshot](https://raw.githubusercontent.com/brandonhuddle/Artemis/master/screenshots/Screenshot1_Timeline.png)

Comments:

![Comments Screenshot](https://raw.githubusercontent.com/brandonhuddle/Artemis/master/screenshots/Screenshot2_Comments.png)
