Set up guide:
https://github.com/Reyes-Tham/Mvizn_DataApp_Setup

Background Information on task 
During my time at mVizn, one my primary responsibilities was data sorting. The task required the interns to download a local file and sort it manually using our file explorers. This process was very tedious as it involved sorting hundreds of images at a time by copying and pasting images from the media folder to the performance folder manually. Additionally, the data sorting required us to accurately sort the images to the correct category else it would affect the final accuracy of the company’s detection system.

<img width="420" height="281" alt="image" src="https://github.com/user-attachments/assets/69c97789-11ce-4978-8352-aa7f2ccecb96" />

The problems faced during the initial stages of doing this task, I encountered several challenges that impacted my efficiency and accuracy of data sorting. Some of the recurring mistakes I made was accidentally missing out some images and sorting same images twice. Additionally, the clarity of the images occasionally posed difficulties, leading to errors in the data categorization. Furthermore, my initial unfamiliarity with the task, also made me frequently consult my supervisor to verify the accuracy of my categorizations. The concept of the project Recognizing the inefficiency and potential for errors in the manual data sorting process, I saw an opportunity to streamline the process of data sorting by conceptualizing the development of a data sorting application. This application was envisioned to simplify the process of data sorting for future interns by enabling them to upload and efficiently categorize images through a user-friendly interface. Among these features, a built-in image editing tool, including a zoom function, to tackle the issue that I faced of image clarity.  Presentation of project idea Following the initial conceptualization of the data sorting application, I proceeded to draft a detailed proposal outlining the application’s features and benefits that it could bring to the company. Subsequently, I arranged a meeting with my supervisor to present this proposal, aiming to gain some valuable feedback and obtain approval to move forward with this project. During the meeting, I gained valuable feedback, including the recommendation to remove the contrast and brightness adjustments tools from the application. It was explained that such features could potentially tamper the original data, thereby impacting the integrity of the data the company lies on. This feedback was helpful in to better align the application with the company’s operational standards. 

User interface design 
With a clear timeline in place, the next step of my design phase was to design the user interface. To design the user interface, I found two potential design tools to use for my prototyping of user interface, Figma and Canva. I eventually chose the Figma design tool since I was experienced with using it throughout my time at Singapore Polytechnic for multiple projects. Before directly designing the prototype, there were a few key steps that I did first, selection of a colour scheme, user-interface layout plan and the user-friendliness of the design. The design process commenced with a selection of a colour scheme. To do this, I utilized coolors.co, an automatic colour palette generator, which was useful in creating aesthetically pleasing colours that worked well together. Using, this tool I chose a colour scheme that was simple and could set the overall theme of the application.

<img width="385" height="243" alt="image" src="https://github.com/user-attachments/assets/ebcfc852-41d8-4bca-870e-d5dc4487932b" />

After selecting the colour scheme, I then proceeded to draft a rough layout plan. This sketch served as a blueprint for the application’s structure, outlining the placement of different elements and the overall flow of the application. The focus of this step was to ensure that the layout of the application was both aesthetically pleasing and logically organized to make it user-friendly for the future users. For example, to create a clean and intuitive design, I positioned the side menu bar for quick access, this design choice was to ensure that user experience will be seamless and that users will be able to access the full range of features easily.

<img width="457" height="262" alt="image" src="https://github.com/user-attachments/assets/5a2dc9f9-cf9e-4bd1-a5a7-7db98edbddd2" />

Code Design 
Subsequently, the next step in the design phase was architecting the code design of the project. This process involved of first selecting a programming language followed by a detailed explanation of how various modules of code would interact together.  For the programming language, I evaluated several options, including Java and Python, both of which are known for their capabilities in developing user interfaces. While Pythion is simple and have vast libraries for data manipulation, Java was ultimately chosen as the programming language for this project. There were several key factors that drove me to select this programming language. Firstly, Java is a platform independent nature, and can be run by any Java Virtual Machine (JVM) regardless of the operating system. This was essential for the data sorting application since it is intended for use in multiple different operating systems. Secondly, is Java’s extensive set of libraries and frameworks, it offers great support for developing sophisticated user interfaces. There are libraries such as JavaFX, a set of graphics and media package that enables developers to easily create, test, debug, and deploy client applications, made Java an attractive choice for creating the data sorting application. Additionally, my previous experience with Java, including Object Oriented Programming and projects undertaken during my time at Singapore Polytechnic, provided me with a solid foundation and confidence in using this programming language effectively for the project.

Database Design 
In parallel with the code design, I also worked on the design of the database architecture. This included defining the data schema, tables, relationships, and data flow within the application to ensure efficient data storage and retrieval. Recognizing the sensitivity of the data we were dealing with, the usage of a secure database was crucial. I chose MySQL to develop a local database, prioritizing the security aspects to safeguard the confidentiality of the images and videos for data sorting. This decision complemented the project’s security which was essential since all data within PSA is confidential.

<img width="407" height="158" alt="image" src="https://github.com/user-attachments/assets/511bd00a-9c5e-4464-96d5-5f12e610fc46" />

To implement the user interface, I made use of Java’s FXML alongside SceneBuilder to create the overall user interface of the data sorting application. SceneBuilder is a visual layout tool which is a third-party application that allows for the drag-and-drop placement of UI components. This approach enabled me to efficiently replicate my prototype design into a FXML format precisely, from the positioning of buttons to the colour scheme. As seen in the figure below, this FXML screen was created with SceneBuilder using multiple FXML layouts such as panes and boxes. 

1. Choosing a Folder: 
• Whenever a user wants to upload their data folder, they start by pressing a button labelled something like “Select Folder”. This action triggers a function called ‘onFileSelect’. 
2. Selecting with DirectoryChooser: 
• The ‘onFileSelect’ method makes use of a tool from JavaFX called ‘DirectoryChooser’. This tool opens a window where users can navigate through their computer’s file explorer and allows them to pick a folder. 
3. Displaying the Folder’s Contents: 
• Now that the application knows which folder the user wants to work with, it calls another method I created, named ‘displayFoldersInTree’. This function utilizes a complex algorithm that takes the selected folder and shows its contents within the application, organizing the images in a way that is easy to view.

Apart from the image uploading system I created, I also implemented quick access features such as using the keyboard buttons “E” and “Q” to allow users to scroll forwards or backwards respectively, increasing the efficiency of looking through hundreds of different images.

Zoom Tool 
The next feature I implemented was the zoom tool, the zoom tool enhances the application by providing more detailed viewing capabilities for images. This was a highly challenging task for me 
because there were no libraries in Java that directly helps zoom an image. Hence, I came up with a creative approach to create this tool, here is a simple explanation of how it works: 
1. Toggle Magnification On and Off: 
• The ‘toggleMagnifcation’ method checks if the magnification tool is active. If it is, turning off 
magnification removes the zoomed view from the screen, vice versa. 
2. What is being magnified: 
• To magnify part of the image, I used the ‘snapshot’ method, which is part of JavaFX library, a 
snapshot is taken when the toggle tool is active, the snapshot taken is based on the position 
of the user’s cursor. 
3. Display the magnified image: 
• After the snapshot is taken, I coded a Viewport to display the snapshot on the user’s cursor 
with an increased size. Hence, giving the illusion that part of the image is being zoomed at on 
the user’s cursor.

<img width="376" height="258" alt="image" src="https://github.com/user-attachments/assets/b839ff4c-4cde-4878-bf23-0ed71ef6e5f3" />

Checklist system 
Another core function I implemented was the checklist system. This was important for the users to have as they would be able to track their progress and not sort duplicate images. The checklist system makes use of the data entered in the database and updates the folder to become green once sorted. The system works in multiple situations:  
1. Folder Selection: 
• Upon selection, the application performs an immediate comparison between the images in the selected folder and the exiting entries in the database, then updates the interface accordingly.
2. Confirm Image Category:  
• Each time a user categorizes an image and presses the confirm button, the application updates the database with the new categorization. This will save the user’s progress, and the checklist will reflect the most current state of image sorting on the interface.
3. How the checklist interacts with database: 
• The unique identifier of checklist system is the use of the image’s date as the primary key within the database. This approach ensures that each image is uniquely identified based on the date it was taken. This ensures that there is no duplicate data being sorted.

<img width="151" height="192" alt="image" src="https://github.com/user-attachments/assets/c1d441ca-1941-4e23-8f54-d03b1710a3ed" />

Data Persistence and Export 
For the data sorting application, ensuring data integrity, and managing errors effectively were paramount. Here’s a simple explanation on how these objectives were implemented: 
1. Closing the Database Properly: 
• To prevent any chance of data corruption, the application is designed to close the database connection each time the application shut down. This ensures that the database connection is safeguarded from data loss or corruption. 
2. Order of Folder Selection: 
• The application requires users to select the performance folder before the media folder. This sequence is critical because, upon starting, the application first populates the database with data from the performance folder. It then compares this data with data from the media folder. If there are matching images in both folders, the application updates its checklist accordingly. This process allows users to resume their work seamlessly, picking up right where they left off without losing progress. 
3. Validating Folder Selection: 
• To ensure the application operates correctly, it includes checks to verify that the correct folders are selected. If a user selects a folder doesn’t match the expected structure (for example, choosing a non-media folder when the media folder is required), the application will display an error message.

4. Export Tool 
• If the user clicks on the export button, the data will be automatically exported to the performance folder that they have selected. This was done using path handling by taking category of a data record in the database and placing the data into its respective category in the performance folder.

Lastly, I have conducted experiments to test the speed of data sorting using my application compared to the manual process of data sorting. The insights I gained were, the average speed of using the data sorting application on a single data sorting task took only an average of 40 seconds while manual sorting task took an average of 1 minute and 30 seconds, this shows that my application is approximately 55.56% faster than the manual process. Hence, showing the efficiency of using the data sorting application I developed.

