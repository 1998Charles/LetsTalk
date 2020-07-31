# LetsTalk
## Description
LetsTalk is an Android application that implements a simple chat utilizing the OCSF framework.

## Requirements
### User Stories
#### Login Page
* Click ⌨️ to hide the keyboard.
* Enter the login ID at the Login ID line.
* Enter the hostname at the Host Name line.
* Enter the port number at the Port Number line.
* Click LOGIN to initiate the chat and build the connection with the server.
* Get a thanking message after the login.
* Get a welcome message after going to the chat page.
* Get an error message if failed to login. 

#### Chat Page
* Each time, one must tap the Message window(the first one from the top) to fetch the message from the Server or sometimes need to update after 🚀SEND.
* The Command feedback will be displayed on the Edit window; One ought to Clear the feedback before sending anything else.
* Click 🛰HIDE to hide the keyboard.
* Click 🚀SEND to send the Command or the message.️
* Scroll the Message window to see more text.
* Scroll the Edit Text window to see more text.
* Scroll the Main window to see more.
1. #quit Causes the client to terminate gracefully. The connection to the server will be terminated before exiting the program.
2. #logoff Causes the client to disconnect from the server, but not quit.
3. #sethost [host] Calls the setHost method in the client, where '[host]' is your new host. Only allowed if the client is logged off; displays an error message otherwise.
4. #setport [port] Calls the setPort method in the client, with the same constraints as #sethost. '[port]' is your new port.
5. #login Causes the client to connect to the server. Only allowed if the client is not already connected; displays an error message otherwise.
6. #gethost Displays the current host name.
7. #getport Displays the current port number.

#### Screenshots
[LetsTalk Screenshots](https://github.com/1998Charles/LetsTalk/tree/master/LetsTalk-ScreenshotsWithIntro)

## Instruction
* OCSF java code
* SimpleChat java code
* Android Studio
* Java 8

## Architecture
* Started at a Google project template called Login Activity for Phone and Tablet.
* Extended the SimpleChat code.
* Used the OCSF framework.
### Frameworks
#### The OCSF
[The OCSF & The SimpleChat](https://github.com/TimLethbridge/Lloseng/tree/master/code)
### Design Diagram
### Hints
* Learn the OCSF framework
* Improve the SimpleChat
* Learn the content of Socket
* Add new features 
## Tests
#### Test Video 
[Xiaoxi Jia - LetsTalk [PROJECT DEMONSTRATION VIDEO]](https://www.youtube.com/watch?v=QfKm7iDxsDs)
#### Reference the Test Cases from the SimpleChat
[Test cases Phase 1 & 2](https://github.com/1998Charles/LetsTalk/tree/master/TestCasesCouldReference)
## License
LetsTalk is licensed under the MIT license. See [LetsTalk MIT License](https://github.com/1998Charles/LetsTalk/blob/master/LICENSE.md)
## Credits
* [Timothy C. Lethbridge](https://github.com/TimLethbridge)
* [Android Studio](https://developer.android.com/studio/projects/templates)
